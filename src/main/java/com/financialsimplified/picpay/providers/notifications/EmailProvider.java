package com.financialsimplified.picpay.providers.notifications;

import com.financialsimplified.picpay.domain.interfaces.EmailNotifier;
import com.financialsimplified.picpay.providers.notifications.dtos.EmailMessageDto;
import com.financialsimplified.picpay.providers.notifications.dtos.ResponseEmailProviderDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class EmailProvider implements EmailNotifier<Mono<ResponseEmailProviderDto>, EmailMessageDto> {
    private static final String emailProviderUrl =  "https://run.mocky.io";

    private final WebClient client;

    public EmailProvider() {
            this.client = WebClient.builder()
                .baseUrl(emailProviderUrl)
                .build();
    }

    @Override
    public Mono<ResponseEmailProviderDto> sendEmail(EmailMessageDto emailMessageDto) {
        return this.client.post()
                .uri("/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(emailMessageDto))
                .retrieve()
                .bodyToMono(ResponseEmailProviderDto.class);
    }
}
