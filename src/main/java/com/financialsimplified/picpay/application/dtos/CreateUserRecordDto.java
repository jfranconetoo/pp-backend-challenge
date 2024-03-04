package com.financialsimplified.picpay.application.dtos;

import com.financialsimplified.picpay.domain.entities.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateUserRecordDto(
        @NotBlank String name,
        @NotBlank String document,
        @NotNull BigDecimal balance,
        @NotBlank String email,
        @NotBlank UserType type
        ){
}
