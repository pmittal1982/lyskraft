package com.company.lyskraft.entity;

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
@Table(name = "root_category",
        uniqueConstraints={
                @UniqueConstraint(columnNames = "name")
        }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RootCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @NotBlank
    @Size(max= 100, message="Category name should not be more than 100 characters")
    private String name;
}