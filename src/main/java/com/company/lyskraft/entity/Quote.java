package com.company.lyskraft.entity;

import com.company.lyskraft.entity.helper.Auditable;
import com.company.lyskraft.constant.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Audited
@Table(indexes = {
        @Index(columnList = "quote_company_id, status"),
        @Index(columnList = "status"),
        @Index(columnList = "enquiry_id, status")
})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Quote extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JsonIgnoreProperties({ "name",
            "address",
            "pinCode",
            "email",
            "phone",
            "bankAccountNumber",
            "bankName",
            "legalRepresentativeName",
            "companyNumber",
            "swiftCode",
            "sellInterest",
            "buyInterest",
            "status",
            "locale",
            "kycDocument",
            "profileCompletion",
            "lastModifiedDate" })
    private Company quoteCompany;
    @ManyToOne
    @JsonIgnoreProperties({ "enquiryCompany",
            "quoteCount",
            "matchingEnquiries" })
    private Enquiry enquiry;
    @ManyToMany(fetch=FetchType.EAGER)
    @JsonIgnoreProperties({ "lastModifiedDate" })
    private Set<Item> item;
    @Enumerated(EnumType.STRING)
    @JsonUnwrapped
    private TransportationTerms transportationTerms;
    @Enumerated(EnumType.STRING)
    @JsonUnwrapped
    private PaymentTerms paymentTerms;
    @Enumerated(EnumType.STRING)
    private EnquiryStatus status;
    @Size(max= 500, message="Other terms should not be more than 500 characters")
    private String otherTerms;
    private String otherAttachmentsUrl;

    @Transient
    private String uuid;

    public String getUuid() {
        if (this.enquiry.getEnquiryType() == EnquiryType.Buy) {
            // It will be opposite of Enquiry type.
            return String.format("#QUOTE-SELL-%05d", this.id);
        }
        return String.format("#QUOTE-BUY-%05d", this.id);
    }
}