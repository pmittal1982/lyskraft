package com.company.lyskraft.entity;

import com.company.lyskraft.constant.CallbackStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CallbackRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String mobileNumber;
    @Size(max= 500, message="message should not be more than 500 characters")
    private String message;
    @Enumerated(EnumType.STRING)
    private CallbackStatus status;
}