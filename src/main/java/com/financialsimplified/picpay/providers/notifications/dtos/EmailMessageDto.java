package com.financialsimplified.picpay.providers.notifications.dtos;

public record EmailMessageDto(
        String from,
        String to,
        String message
) {
}
