package com.company.lyskraft.entity;

import com.company.lyskraft.entity.helper.Auditable;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "one_time_password",
        uniqueConstraints={@UniqueConstraint(columnNames ={"mobileNumber","oneTimePasswordCode"})})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OneTimePassword extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String mobileNumber;
    @NonNull
    private String oneTimePasswordCode;
    @NonNull
    private Date expires;
}