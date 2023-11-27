package com.company.lyskraft.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CallbackStatus {
    Unattended("Unattended"),
    Interested("Interested"),
    Notinterested("Notinterested");
    public final String value;
}