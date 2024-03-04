package com.financialsimplified.picpay.domain.services;

import com.financialsimplified.picpay.domain.entities.Transaction;
import com.financialsimplified.picpay.providers.database.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetAllTransactionsService {
    private final TransactionRepository transactionRepository;

    public GetAllTransactionsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> execute() {
        return this.transactionRepository.findAll();
    }
}

