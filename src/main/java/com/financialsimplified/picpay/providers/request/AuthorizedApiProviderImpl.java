package com.financialsimplified.picpay.providers.request;

import com.financialsimplified.picpay.domain.interfaces.AuthorizedApi;
import com.financialsimplified.picpay.providers.request.dtos.ResponseAuthorizedDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthorizedApiProviderImpl implements AuthorizedApi<Mono<ResponseAuthorizedDto>> {
    private static final String authorizedApiUrl =  "https://run.mocky.io";

    private final WebClient client;

    public AuthorizedApiProviderImpl() {
        this.client = WebClient.builder()
                .baseUrl(authorizedApiUrl)
                .build();
    }

    @Override
    public Mono<ResponseAuthorizedDto> get(String url) {
        return this.client.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ResponseAuthorizedDto.class);
    }
}
