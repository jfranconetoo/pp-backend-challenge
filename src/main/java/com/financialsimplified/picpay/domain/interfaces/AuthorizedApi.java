package com.financialsimplified.picpay.domain.interfaces;

import com.financialsimplified.picpay.providers.request.dtos.ResponseAuthorizedDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
public interface AuthorizedApi<T> {
    public T get(String url);
}
