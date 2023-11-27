package com.company.lyskraft.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChatMessageType {
    Text("Text"),
    Attachment("Attachment"),
    Enquiry("Enquiry"),
    Quote("Quote"),
    Reply("Reply");
    public final String value;
}