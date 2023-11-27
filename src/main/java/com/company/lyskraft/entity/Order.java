package com.company.lyskraft.entity;

import com.company.lyskraft.entity.helper.Auditable;
import com.company.lyskraft.constant.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "mtp_order",
        indexes = {
                @Index(columnList = "status")
        })
@Audited
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Order extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @NonNull
    @JsonIgnoreProperties({ "lastModifiedDate",
            "enquiry",
            "item",
            "transportationTerms",
            "paymentTerms",
            "status",
            "otherTerms",
            "otherAttachmentsUrl" })
    private Quote quote;
    @OneToOne
    @NonNull
    @JsonIgnoreProperties({ "lastModifiedDate",
            "item",
            "country",
            "transportationTerms",
            "paymentTerms",
            "status",
            "otherTerms",
            "otherAttachmentsUrl",
            "quoteCount",
            "matchingEnquiries" })
    private Enquiry enquiry;
    @ManyToMany
    @NonNull
    @JsonIgnoreProperties({ "lastModifiedDate" })
    private Set<Item> item;
    @Enumerated(EnumType.STRING)
    @JsonUnwrapped
    private TransportationTerms transportationTerms;
    @Enumerated(EnumType.STRING)
    @JsonUnwrapped
    private PaymentTerms paymentTerms;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private double totalValue;

    @Transient
    private String uuid;

    public String getUuid() {
        return String.format("#MTP-%05d", this.id);
    }
}