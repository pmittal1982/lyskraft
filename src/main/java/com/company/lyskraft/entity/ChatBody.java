package com.company.lyskraft.entity;

import com.company.lyskraft.constant.ChatMessageType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChatBody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private ChatMessageType chatMessageType;
    @ManyToOne
    @JsonIgnoreProperties({ "enquiryCompany",
            "transportationTerms",
            "paymentTerms",
            "quoteCount",
            "matchingEnquiries",
            "otherTerms",
            "otherAttachmentsUrl",
            "lastModifiedDate" })
    private Enquiry enquiry;
    @ManyToOne
    @JsonIgnoreProperties({ "quoteCompany",
            "enquiry",
            "transportationTerms",
            "paymentTerms",
            "otherTerms",
            "otherAttachmentsUrl",
            "lastModifiedDate" })
    private Quote quote;
    @ManyToOne
    private ChatBody replyTo;
    @Size(max= 500, message="text should not be more than 500 characters")
    private String text;
    private String attachmentUrl;
}