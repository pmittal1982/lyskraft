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
@Table(name = "sku",
        uniqueConstraints={
                @UniqueConstraint(columnNames = "title")
        }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Sku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @NotBlank
    @Size(max= 100, message="Title should not be more than 100 characters")
    private String title;
    @NonNull
    @NotBlank
    @JsonIgnore
    @Size(max= 5000, message="Description should not be more than 5000 characters")
    private String description;
    @NonNull
    @JsonIgnore
    private String imageUrl;
    @ManyToOne
    @NonNull
    @JsonIgnore
    private LeafCategory leafCategory;
    @Transient
    @JsonIgnore
    private Iterable<Product> products;
}