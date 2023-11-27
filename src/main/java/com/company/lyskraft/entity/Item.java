package com.company.lyskraft.entity;

import com.company.lyskraft.entity.helper.Auditable;
import com.company.lyskraft.constant.QuantityUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Audited
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Item extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @NonNull
    @NotAudited
    private Sku sku;
    @ManyToOne
    @JsonIgnore
    @NotAudited
    private Product product;
    private long quantity;
    @NonNull
    @Enumerated(EnumType.STRING)
    private QuantityUnit quantityUnit;
    private double price;
    @Size(max= 500, message="Remarks should not be more than 500 characters")
    private String remarks;
}