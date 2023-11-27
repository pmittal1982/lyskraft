package com.company.lyskraft.entity;

import com.company.lyskraft.entity.helper.Auditable;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(indexes = {
        @Index(columnList = "createdDate, name")
})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ShanghaiStockExchangeData extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private long price;
    private int change;
    private int volume;
}