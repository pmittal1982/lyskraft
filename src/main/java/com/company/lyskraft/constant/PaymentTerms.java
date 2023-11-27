package com.company.lyskraft.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PaymentTerms {
    ADVANCE("Advance"),
    LC_AT_SITE("LC at site"),
    OTHERS("Others");
    public final String paymentTerms;
    public String getPaymentTermsDisplay() {
        return this.paymentTerms;
    }

    public String getPaymentTerms() {
        return this.toString();
    }
}