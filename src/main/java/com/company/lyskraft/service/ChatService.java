package com.company.lyskraft.service;

import com.company.lyskraft.constant.ChatMessageType;
import com.company.lyskraft.entity.*;
import com.company.lyskraft.constant.ChatMessageStatus;
import com.company.lyskraft.dto.ChatRoomCard;
import com.company.lyskraft.constant.UserRole;
import com.company.lyskraft.repository.*;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@EnableAsync
@RequiredArgsConstructor
public class ChatService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ChatMessageRepository chatMessageRepository;
    private final ChatBodyRepository chatBodyRepository;
    private final EnquiryRepository enquiryRepository;
    private final QuoteRepository quoteRepository;

    public void saveChatMessage(ChatMessage chatMessage) throws Exception {
        chatMessage.getBody().setChatMessageType(ChatMessageType.Text);
        if (chatMessage.getBody().getAttachmentUrl() != null && !chatMessage.getBody().getAttachmentUrl().isBlank()) {
            chatMessage.getBody().setChatMessageType(ChatMessageType.Attachment);
        }
        if (chatMessage.getBody().getReplyTo() != null) {
            chatMessage.getBody().setChatMessageType(ChatMessageType.Reply);
        }
        if (chatMessage.getEnquiryId() != 0) {
            Optional<Enquiry> enquiry = enquiryRepository.findById(chatMessage.getEnquiryId());
            if (enquiry.isEmpty()) {
                throw new Exception("Invalid enquiry");
            }
            if (chatMessage.getBody().getEnquiry() != null) {
                chatMessage.getBody().setChatMessageType(ChatMessageType.Enquiry);
                chatMessage.getBody().setEnquiry(enquiry.get());
            }
        }
        if (chatMessage.getQuoteId() != 0) {
            Optional<Quote> quote = quoteRepository.findById(chatMessage.getQuoteId());
            if (quote.isEmpty()) {
                throw new Exception("Invalid quote");
            }
            if (chatMessage.getBody().getQuote() != null) {
                chatMessage.getBody().setChatMessageType(ChatMessageType.Quote);
                chatMessage.getBody().setQuote(quote.get());
            }
        }
        chatBodyRepository.save(chatMessage.getBody());
        chatMessage.setStatus(ChatMessageStatus.Unseen);
        chatMessage.setChatRoomId(generateChatRoomId(chatMessage.getSenderCompanyId(),
                chatMessage.getRecipientCompanyId()));

        logger.info("The chat Message is : {}", new Gson().toJson(chatMessage));
        chatMessageRepository.save(chatMessage);
    }

    @Async
    public void createChatForNewQuote(Quote quoteDetails) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderCompanyId(quoteDetails.getQuoteCompany().getId());
        // TODO: 20/07/23  for now sending all the quote chats to Root user Only.
        chatMessage.setRecipientCompanyId(1);
        //chatMessage.setRecipientCompanyId(quoteDetails.getEnquiry().getEnquiryCompany().getId());
        chatMessage.setEnquiryId(quoteDetails.getEnquiry().getId());
        chatMessage.setQuoteId(quoteDetails.getId());
        ChatBody chatBody = new ChatBody();
        chatBody.setQuote(quoteDetails);
        chatBody.setChatMessageType(ChatMessageType.Quote);
        chatMessage.setBody(chatBody);
        chatBodyRepository.save(chatMessage.getBody());
        chatMessage.setStatus(ChatMessageStatus.Unseen);
        chatMessage.setChatRoomId(generateChatRoomId(chatMessage.getSenderCompanyId(),
                chatMessage.getRecipientCompanyId()));
        chatMessageRepository.save(chatMessage);
    }

    @Async
    public void createChatForAcceptQuote(Quote quoteDetails) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderCompanyId(quoteDetails.getEnquiry().getEnquiryCompany().getId());
        // TODO: 20/07/23  for now sending all the quote chats to Root user Only.
        chatMessage.setRecipientCompanyId(1);
        //chatMessage.setRecipientCompanyId(quoteDetails.getQuoteCompany().getId());
        chatMessage.setEnquiryId(quoteDetails.getEnquiry().getId());
        chatMessage.setQuoteId(quoteDetails.getId());
        ChatBody chatBody = new ChatBody();
        chatBody.setText(String.format("Your quote %s has been accepted for Enquiry %s",
                quoteDetails.getUuid(),
                quoteDetails.getEnquiry().getUuid()));
        chatBody.setChatMessageType(ChatMessageType.Text);
        chatMessage.setBody(chatBody);
        chatBodyRepository.save(chatMessage.getBody());
        chatMessage.setStatus(ChatMessageStatus.Unseen);
        chatMessage.setChatRoomId(generateChatRoomId(chatMessage.getSenderCompanyId(),
                chatMessage.getRecipientCompanyId()));
        chatMessageRepository.save(chatMessage);
    }

    @Async
    public void createChatForCloseQuote(Quote quoteDetails) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderCompanyId(quoteDetails.getEnquiry().getEnquiryCompany().getId());
        // TODO: 20/07/23  for now sending all the quote chats to Root user Only.
        chatMessage.setRecipientCompanyId(1);
        //chatMessage.setRecipientCompanyId(quoteDetails.getQuoteCompany().getId());
        chatMessage.setEnquiryId(quoteDetails.getEnquiry().getId());
        chatMessage.setQuoteId(quoteDetails.getId());
        ChatBody chatBody = new ChatBody();
        chatBody.setText(String.format("Your quote %s has been closed/Expired for Enquiry %s",
                quoteDetails.getUuid(),
                quoteDetails.getEnquiry().getUuid()));
        chatMessage.setBody(chatBody);
        chatBody.setChatMessageType(ChatMessageType.Text);
        chatBodyRepository.save(chatMessage.getBody());
        chatMessage.setStatus(ChatMessageStatus.Unseen);
        chatMessage.setChatRoomId(generateChatRoomId(chatMessage.getSenderCompanyId(),
                chatMessage.getRecipientCompanyId()));
        chatMessageRepository.save(chatMessage);
    }

    public void markMessagesRead(User currentUser, List<Long> messageIds) {
        for (Long messageId : messageIds) {
            Optional<ChatMessage> chatMessage = chatMessageRepository.findById(messageId);
            if (chatMessage.isPresent()) {
                if (currentUser.getCompany().getId() == chatMessage.get().getRecipientCompanyId()) {
                    chatMessage.get().setStatus(ChatMessageStatus.Seen);
                    chatMessageRepository.save(chatMessage.get());
                }
            }
        }
    }

    public Page<ChatMessage> getAllChatsBetweenTwoCompanies(User currentUser,
                                                            long receiverId,
                                                            Pageable page) {
        return chatMessageRepository.findAllByChatRoomId(generateChatRoomId(currentUser.getCompany().getId(), receiverId),
                page);
    }

    public Page<ChatMessage> getAllChatsOfCurrentUser(User currentUser, Pageable page) {
        return chatMessageRepository.findAllBySenderCompanyIdOrRecipientCompanyId(currentUser.getCompany().getId(),
                currentUser.getCompany().getId(),
                page);
    }

    public Page<ChatRoomCard> getChatRoomCardsOfCurrentUser(User currentUser, Pageable page) {
        Page<ChatRoomCard> chatRoomCards = chatMessageRepository.findUniqueChatRoomCards(page, currentUser.getId());
        // Fill the chat room display details
        for (ChatRoomCard chatRoomCard : chatRoomCards) {
            if (chatRoomCard.getEnquiryId() != 0) {
                Optional<Enquiry> enquiry = enquiryRepository.findById(chatRoomCard.getEnquiryId());
                if (enquiry.isPresent()) {
                    chatRoomCard.setHeading(enquiry.get().getUuid());
                    chatRoomCard.setDescription(prepareDescription(enquiry.get()));
                    // TODO: 10/08/23 uncomment when you plan to show the unseen count.
                    //chatRoomCard.setUnseenChatCount(getUnseenChatCount(currentUser, enquiry.get().getId()));
                }
            } else {
                chatRoomCard.setHeading("MetalTrade support");
                chatRoomCard.setDescription("Chat with us...");
                // TODO: 10/08/23 uncomment when you plan to show the unseen count.
                //chatRoomCard.setUnseenChatCount(getUnseenChatCount(currentUser, 0L));
            }
            chatRoomCard.setInitial(numToLetterByAsciiCode(chatRoomCard.getEnquiryId()));
        }
        return chatRoomCards;
    }

    public int getUnseenChatCount(User currentUser, Long enquiryId) {
        if (enquiryId == null) {
            return chatMessageRepository.countByRecipientCompanyIdAndStatus(currentUser.getCompany().getId(),
                    ChatMessageStatus.Unseen);
        } else {
            return chatMessageRepository.countByRecipientCompanyIdAndEnquiryIdAndStatus(currentUser.getCompany().getId(),
                    enquiryId,
                    ChatMessageStatus.Unseen);
        }
    }

    public Page<ChatMessage> getAllChatsBetweenTwoCompaniesByEnquiry(User currentUser,
                                                                     long receiverId,
                                                                     long enquiryId,
                                                                     Pageable page) throws Exception {
        return chatMessageRepository.findAllByChatRoomIdAndEnquiryId(generateChatRoomId(currentUser.getCompany().getId(),
                receiverId), enquiryId, page);
    }

    public Page<ChatMessage> getAllChatsByQuote(User currentUser, long quoteId, Pageable page) throws Exception {
        Optional<Quote> quote = quoteRepository.findById(quoteId);
        if (quote.isEmpty()) {
            throw new Exception("Invalid quote");
        }
        if (!currentUser.getRole().equals(UserRole.ROOT) &&
                quote.get().getEnquiry().getEnquiryCompany().getId() != currentUser.getCompany().getId() &&
                        quote.get().getQuoteCompany().getId() != currentUser.getCompany().getId()) {
            throw new Exception("This Enquiry/Quote is not owned by you");
        }
        return chatMessageRepository.findAllByQuoteId(quoteId, page);
    }

    private String generateChatRoomId(long senderCompanyId, long recipientCompanyId) {
        if (senderCompanyId > recipientCompanyId) {
            return String.format("%s-%s", recipientCompanyId, senderCompanyId);
        }
        return String.format("%s-%s", senderCompanyId, recipientCompanyId);
    }

    private String prepareDescription(Enquiry enquiry) {
        StringBuilder description = new StringBuilder();
        for (Item item : enquiry.getItem()) {
            description.append(item.getSku().getTitle());
            description.append(", ");
        }
        // Break the text at 30 characters
        if (description.length() > 32) {
            return description.substring(0, 30).trim() + "...";
        }
        description.delete(description.length() - 2, description.length());
        return description.toString().trim();
    }

    private char numToLetterByAsciiCode(long enquiryId) {
        if (enquiryId != 0) {
            long i = enquiryId % 24;
            return (char) ('A' + i);
        } else {
            return 'M';
        }
    }
}