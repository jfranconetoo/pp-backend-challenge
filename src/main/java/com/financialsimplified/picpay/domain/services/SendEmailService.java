package com.financialsimplified.picpay.domain.services;

import com.financialsimplified.picpay.domain.interfaces.EmailNotifier;
import com.financialsimplified.picpay.providers.notifications.dtos.EmailMessageDto;
import com.financialsimplified.picpay.providers.notifications.dtos.ResponseEmailProviderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SendEmailService {

    private final EmailNotifier<Mono<ResponseEmailProviderDto>, EmailMessageDto> emailProvider;

    @Autowired
    public SendEmailService(EmailNotifier<Mono<ResponseEmailProviderDto>, EmailMessageDto> emailProvider) {
        this.emailProvider = emailProvider;
    }

    void execute(String from, String to, String message) {
        var emailMessageDto = new EmailMessageDto(from, to, message);
        this.emailProvider.sendEmail(emailMessageDto)
                .subscribe(body -> {
                    if(body.message()) {
                        System.out.printf("Email sent: %s", message);
                    } else {
                        System.out.println("Email was not sent");
                    };
                });
    }
}
