package com.company.lyskraft.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EnquiryStatus {
    Inreview("In-review"),
    Active("Active"),
    Expired("Expired"),
    Closed("Closed"),
    Complete("Complete");

    public final String value;
}