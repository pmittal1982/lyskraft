package com.company.lyskraft.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CompanyStatus {
    Unverified("Unverified"),
    Verified("Verified"),
    Deleted("Deleted");
    public final String value;
}