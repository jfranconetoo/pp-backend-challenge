package com.financialsimplified.picpay.domain.services;

import com.financialsimplified.picpay.providers.notifications.EmailProvider;
import com.financialsimplified.picpay.providers.notifications.dtos.EmailMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    private final EmailProvider emailProvider;

    @Autowired
    public SendEmailService(EmailProvider emailProvider) {
        this.emailProvider = emailProvider;
    }

    void execute(String from, String to, String message) {
        var emailMessageDto = new EmailMessageDto(from, to, message);
        this.emailProvider.sendEmail(emailMessageDto)
                .subscribe(body -> {
                    if(body.message()) {
                        System.out.println("Email sent");
                    } else {
                        System.out.println("Email was not sent");
                    };
                });
    }
}
