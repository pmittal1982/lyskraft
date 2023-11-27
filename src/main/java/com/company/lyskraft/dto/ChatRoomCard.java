package com.company.lyskraft.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChatRoomCard {
    private Long enquiryId;
    private char initial;
    @JsonIgnore
    private int unseenChatCount;
    private Date lastChatDate;
    private String heading;
    private String description;

    public ChatRoomCard(Long enquiryId, Date lastChatDate) {
        this.enquiryId = enquiryId;
        this.lastChatDate = lastChatDate;
    }
}