package com.financialsimplified.picpay.domain.services;

import com.financialsimplified.picpay.application.dtos.RequestTransactionDto;
import com.financialsimplified.picpay.application.dtos.UpdateUserDto;
import com.financialsimplified.picpay.domain.entities.Transaction;
import com.financialsimplified.picpay.domain.entities.enums.TransactionStatus;
import com.financialsimplified.picpay.domain.interfaces.AuthorizedApi;
import com.financialsimplified.picpay.providers.database.repositories.TransactionRepository;
import com.financialsimplified.picpay.providers.request.dtos.ResponseAuthorizedDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Service
public class CreateTransactionService {
    private static final String emailCompany = "company@finance.com";
    private static final String messageFormatTemplate = "Você recebeu um transferência de %s de %s";

    private final GetUserService getUserService;
    private final UpdatedUserService updatedUserService;
    private final AuthorizedApi<Mono<ResponseAuthorizedDto>> authorizedApiProviderImpl;
    private final TransactionRepository transactionRepository;

    private final SendEmailService sendEmailService;

    public CreateTransactionService(GetUserService getUserService, UpdatedUserService updatedUserService, AuthorizedApi<Mono<ResponseAuthorizedDto>> authorizedApiProviderImpl, TransactionRepository transactionRepository, SendEmailService sendEmailService){
        this.getUserService = getUserService;
        this.updatedUserService = updatedUserService;
        this.authorizedApiProviderImpl = authorizedApiProviderImpl;
        this.transactionRepository = transactionRepository;
        this.sendEmailService = sendEmailService;
    }

    public Transaction execute(RequestTransactionDto requestTransactionDto) throws Exception {
        var sender = this.getUserService.execute(requestTransactionDto.sender());
        var receiver = this.getUserService.execute(requestTransactionDto.receiver());

        var transaction = new Transaction();
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(requestTransactionDto.amount());
        transaction.setTimestamp(LocalDateTime.now());
        //Perform validation
        transaction.valid();

        Mono<String> apiResponse = this.authorizedApiProviderImpl.get("/v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
                .map(ResponseAuthorizedDto::message)
                .onErrorResume(ex -> {
                    return Mono.just("Error");
                })
                .subscribeOn(Schedulers.boundedElastic());


        apiResponse.subscribe(message -> {
            if ("Autorizado".equalsIgnoreCase(message)) {
                try {
                    performTransfer(transaction);
                    transaction.setStatus(TransactionStatus.COMPLETED);
                    sendEmailService.execute(emailCompany, receiver.getEmail(), String.format(messageFormatTemplate, transaction.getAmount(), sender.getEmail()));
                } catch (Exception ex) {
                    transaction.setStatus(TransactionStatus.FAILED);
                }
            } else {
                transaction.setStatus(TransactionStatus.REFUSED);
            }
            // Save the transaction to the repository
            transactionRepository.save(transaction);
        });

        return transaction;
    }

    @Transactional
    private void performTransfer(Transaction transaction) {
        this.updatedUserService.execute(transaction.getSender().getUserId(), new UpdateUserDto(
               transaction.getSender().getBalance().subtract(transaction.getAmount())
        ));
        this.updatedUserService.execute(transaction.getReceiver().getUserId(), new UpdateUserDto(
                transaction.getReceiver().getBalance().add(transaction.getAmount())
        ));
    }
}
