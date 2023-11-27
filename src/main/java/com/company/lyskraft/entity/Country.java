package com.company.lyskraft.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "country",
        uniqueConstraints={
                @UniqueConstraint(columnNames = "name")
        }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String name;
}