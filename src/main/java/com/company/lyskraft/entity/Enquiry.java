package com.company.lyskraft.entity;

import com.company.lyskraft.entity.helper.Auditable;
import com.company.lyskraft.constant.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Audited
@Table(indexes = {
        @Index(columnList = "enquiry_company_id, status"),
        @Index(columnList = "status, enquiryType, enquiry_company_id"),
        @Index(columnList = "enquiryType, status"),
        @Index(columnList = "status")
})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Enquiry extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany(fetch=FetchType.EAGER)
    @JsonIgnoreProperties({ "lastModifiedDate",
            "price" })
    private Set<Item> item;
    @ManyToMany(fetch=FetchType.EAGER)
    @NotAudited
    private Set<Country> country;
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
    private Company enquiryCompany;
    private EnquiryType enquiryType;
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
    private int quoteCount;
    @Transient
    private String uuid;
    @Transient
    @JsonIgnore
    private int matchingEnquiries;
    @Transient
    private String otherAttachmentsName;

    public String getUuid() {
        if (this.enquiryType == EnquiryType.Buy) {
            return String.format("#ENQ-BUY-%05d", this.id);
        }
        return String.format("#ENQ-SELL-%05d", this.id);
    }

    public String getOtherAttachmentsName() {
        if (otherAttachmentsUrl != null) {
            otherAttachmentsName = otherAttachmentsUrl.substring(otherAttachmentsUrl.lastIndexOf('/') + 1);
        }
        return otherAttachmentsName;
    }
}