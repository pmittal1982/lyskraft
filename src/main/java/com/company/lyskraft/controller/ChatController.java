package com.company.lyskraft.controller;

import com.company.lyskraft.constant.UserRole;
import com.company.lyskraft.service.ChatService;
import com.company.lyskraft.entity.ChatMessage;
import com.company.lyskraft.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    @MessageMapping("/chat")
    public void processMessage(Authentication authentication, @Payload ChatMessage chatMessage) throws Exception {
        User user = (User)authentication.getPrincipal();
        // IMP: This is set explicitly so that we can get the value for createdBy and modifiedBy
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // TODO: 08/07/23 we are hardcoding the recipient to Admin
        //  and overriding the senderCompanyId to currentUserCompanyId
        //  As all the chats needs to be sent to Admin.
        if (!user.getRole().equals(UserRole.ROOT)) {
            logger.info("Overwriting the original recipient Id {} to Admin", chatMessage.getRecipientCompanyId());
            chatMessage.setRecipientCompanyId(1);
            chatMessage.setSenderCompanyId(user.getCompany().getId());
        }
        chatService.saveChatMessage(chatMessage);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessage.getRecipientCompanyId()),"/queue/messages",
                chatMessage);
    }

    @MessageMapping("/chat/read")
    public void markMessagesRead(Authentication authentication, @Payload List<Long> chatMessageIds) {
        User user = (User)authentication.getPrincipal();
        logger.info("Ids to be marked read : {}", chatMessageIds);
        // IMP: This is set explicitly so that we can get the value for createdBy and modifiedBy
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chatService.markMessagesRead(user, chatMessageIds);
    }

    @GetMapping("api/v{apiVersion}/user/chat/count")
    @ResponseBody
    private Object getUnseenChatCount(@PathVariable int apiVersion,
                                      Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        return ResponseEntity.ok(chatService.getUnseenChatCount(user, null));
    }

    @GetMapping("api/v{apiVersion}/user/chat")
    @ResponseBody
    private Object getAllChatsByCurrentUser(@PathVariable int apiVersion,
                                            Authentication authentication,
                                            @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                            @RequestParam(name="size", defaultValue="10") Integer size) {
        User user = (User)authentication.getPrincipal();
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("createdDate").descending());
        return ResponseEntity.ok(chatService.getAllChatsOfCurrentUser(user, page));
    }

    @GetMapping("api/v{apiVersion}/user/chat/room")
    @ResponseBody
    private Object getChatRoomCardsByCurrentUser(@PathVariable int apiVersion,
                                                 Authentication authentication,
                                                 @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                                 @RequestParam(name="size", defaultValue="10") Integer size) {
        User user = (User)authentication.getPrincipal();
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("latestChatDate").descending());
        return ResponseEntity.ok(chatService.getChatRoomCardsOfCurrentUser(user, page));
    }

    @GetMapping("api/v{apiVersion}/user/enquiry/{enquiryId}/chat")
    @ResponseBody
    private Object getAllChatsByCurrentUserAndEnquiryId(@PathVariable int apiVersion,
                                                        Authentication authentication,
                                                        @PathVariable("enquiryId") Long enquiryId,
                                                        @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                                        @RequestParam(name="size", defaultValue="10") Integer size) throws Exception {
        User user = (User)authentication.getPrincipal();
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("createdDate").descending());
        // Here the recipientId has been hardcoded to 1 as all the chats will always be with
        // Support team.
        return ResponseEntity.ok(chatService.getAllChatsBetweenTwoCompaniesByEnquiry(user,
                1, enquiryId, page));
    }

    @GetMapping("api/v{apiVersion}/user/quote/{quoteId}/chat")
    @ResponseBody
    private Object getAllChatsByCurrentUserAndQuoteId(@PathVariable int apiVersion,
                                                      Authentication authentication,
                                                      @PathVariable("quoteId") Long quoteId,
                                                      @RequestParam(name="page", defaultValue="0") Integer pageNumber,
                                                      @RequestParam(name="size", defaultValue="10") Integer size) throws Exception {
        User user = (User)authentication.getPrincipal();
        Pageable page = PageRequest.of(pageNumber, size, Sort.by("createdDate").descending());
        return ResponseEntity.ok(chatService.getAllChatsByQuote(user, quoteId, page));
    }
}