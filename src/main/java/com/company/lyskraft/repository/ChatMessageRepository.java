package com.company.lyskraft.repository;

import com.company.lyskraft.dto.ChatRoomCard;
import com.company.lyskraft.entity.ChatMessage;
import com.company.lyskraft.constant.ChatMessageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Page<ChatMessage> findAllByQuoteId(long quoteId, Pageable page);

    int countByRecipientCompanyIdAndStatus(long currentCompanyId, ChatMessageStatus status);

    int countByRecipientCompanyIdAndEnquiryIdAndStatus(long currentCompanyId, long enquiryId, ChatMessageStatus status);

    Page<ChatMessage> findAllByChatRoomId(String chatRoomId, Pageable page);

    Page<ChatMessage> findAllBySenderCompanyIdOrRecipientCompanyId(long senderCompanyId,
                                                                   long recipientCompanyId,
                                                                   Pageable page);

    Page<ChatMessage> findAllByChatRoomIdAndEnquiryId(String chatRoomId, long enquiryId, Pageable page);

    @Query(value = "select new com.company.mtp.dto.ChatRoomCard(cm.enquiryId,  " +
            "max(cm.createdDate) as latestChatDate) from ChatMessage cm " +
            "where cm.recipientCompanyId = :companyId or cm.senderCompanyId = :companyId group by cm.enquiryId",
            countQuery = "select cm.enquiryId from ChatMessage cm where " +
                    "cm.recipientCompanyId = :companyId or cm.senderCompanyId = :companyId group by cm.enquiryId")
    Page<ChatRoomCard> findUniqueChatRoomCards(Pageable pageable, @Param("companyId") Long companyId);
}