package com.company.lyskraft.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "product",
        uniqueConstraints={
                @UniqueConstraint(columnNames = "title")
        }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @NotBlank
    @Size(max= 100, message="Title should not be more than 100 characters")
    private String title;
    @NonNull
    @JsonIgnore
    private String imageUrl;
    @ManyToOne
    @NonNull
    @JsonIgnore
    private Sku sku;
}