package com.company.lyskraft.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
    Accepted("Accepted"),
    Inprogress("Inprogress"),
    Delivered("Delivered");
    public final String value;
}
