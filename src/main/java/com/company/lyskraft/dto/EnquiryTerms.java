package com.company.lyskraft.dto;

import com.company.lyskraft.constant.PaymentTerms;
import com.company.lyskraft.constant.TransportationTerms;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EnquiryTerms {
    private Map<String, String> paymentTerms;
    private Map<String, String> transportationTerms;

    public Map<String, String> getPaymentTerms() {
        this.paymentTerms = new HashMap<>();
        for (PaymentTerms paymentTerm : PaymentTerms.values()) {
            this.paymentTerms.put(paymentTerm.name(), paymentTerm.paymentTerms);
        }
        return this.paymentTerms;
    }

    public Map<String, String> getTransportationTerms() {
        this.transportationTerms = new HashMap<>();
        for (TransportationTerms transportationTerm : TransportationTerms.values()) {
            this.transportationTerms.put(transportationTerm.name(), transportationTerm.transportationTerms);
        }
        return this.transportationTerms;
    }
}