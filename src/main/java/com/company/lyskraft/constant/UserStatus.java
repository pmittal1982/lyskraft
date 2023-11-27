package com.company.lyskraft.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus {
    Active("Active"),
    Inactive("Inactive");
    public final String value;
}