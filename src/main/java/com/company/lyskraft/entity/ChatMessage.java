package com.company.lyskraft.entity;

import com.company.lyskraft.entity.helper.Auditable;
import com.company.lyskraft.constant.ChatMessageStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(indexes = {
        @Index(columnList = "chatRoomId"),
        @Index(columnList = "chatRoomId, enquiryId"),
        @Index(columnList = "quoteId")
})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChatMessage extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonIgnore
    private String chatRoomId;
    private long senderCompanyId;
    private long recipientCompanyId;
    private long enquiryId;
    private long quoteId;
    @OneToOne
    @NonNull
    private ChatBody body;
    @Enumerated(EnumType.STRING)
    private ChatMessageStatus status;
}