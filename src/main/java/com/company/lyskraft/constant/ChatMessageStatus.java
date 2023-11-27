package com.company.lyskraft.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChatMessageStatus {
    Unseen("Unseen"),
    Seen("Seen");

    public final String value;
}