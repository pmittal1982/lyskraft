package com.company.lyskraft.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum KycDocumentType {
    VAT_REGISTRATION_NUMBER("VAT Registration"),
    ACRA("ACRA");
    public final String value;
}