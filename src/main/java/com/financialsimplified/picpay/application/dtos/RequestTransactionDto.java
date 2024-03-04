package com.financialsimplified.picpay.application.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record RequestTransactionDto(
        @NotNull BigDecimal amount,
        @NotNull UUID sender,
        @NotNull UUID receiver
        ) {}
