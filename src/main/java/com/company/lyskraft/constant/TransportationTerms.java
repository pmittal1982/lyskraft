package com.company.lyskraft.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TransportationTerms {
    FOB("FOB (Freight on Board)"),
    CFR("CFR (Cost and Freight)"),
    CIF("CIF (Cost, Freight and Insurance)"),
    OTHERS("Others");
    public final String transportationTerms;

    public String getTransportationTermsDisplay() {
        return this.transportationTerms;
    }

    public String getTransportationTerms() {
        return this.toString();
    }
}