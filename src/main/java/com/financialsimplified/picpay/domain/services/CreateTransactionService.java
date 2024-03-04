package com.financialsimplified.picpay.domain.services;

import com.financialsimplified.picpay.application.dtos.RequestTransactionDto;
import com.financialsimplified.picpay.application.dtos.UpdateUserRecordDto;
import com.financialsimplified.picpay.domain.entities.Transaction;
import com.financialsimplified.picpay.domain.entities.enums.TransactionStatus;
import com.financialsimplified.picpay.providers.database.repositories.TransactionRepository;
import com.financialsimplified.picpay.providers.request.AuthorizedApiProviderImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateTransactionService {
    private final GetUserService getUserService;
    private final UpdatedUserService updatedUserService;
    private final AuthorizedApiProviderImpl authorizedApiProviderImpl;
    private final TransactionRepository transactionRepository;
    @Autowired
    public CreateTransactionService(GetUserService getUserService, UpdatedUserService updatedUserService, AuthorizedApiProviderImpl authorizedApiProviderImpl, TransactionRepository transactionRepository){
        this.getUserService = getUserService;
        this.updatedUserService = updatedUserService;
        this.authorizedApiProviderImpl = authorizedApiProviderImpl;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction execute(RequestTransactionDto requestTransactionDto) throws Exception {
        var sender = this.getUserService.execute(requestTransactionDto.sender());
        var receiver = this.getUserService.execute(requestTransactionDto.receiver());
        var transaction = new Transaction();
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(requestTransactionDto.amount());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.valid();

        this.authorizedApiProviderImpl.get("/v3/5794d450-d2e2-4412-8131-73d0293ac1cc").subscribe((body) -> {
            String message = body.message();
            if(message.equalsIgnoreCase("Autorizado")) {
                this.performTransfer(transaction);
                transaction.setStatus(TransactionStatus.COMPLETED);
            } else {
                transaction.setStatus(TransactionStatus.REFUSED);
            }
        });

        return this.transactionRepository.save(transaction);
    }

    @Transactional
    private void performTransfer(Transaction transaction) {
        this.updatedUserService.execute(transaction.getSender().getUserId(), new UpdateUserRecordDto());
        this.updatedUserService.execute(transaction.getReceiver().getUserId(), new UpdateUserRecordDto());
    }
}
