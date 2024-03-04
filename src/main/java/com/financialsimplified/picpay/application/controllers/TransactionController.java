package com.financialsimplified.picpay.application.controllers;

import com.financialsimplified.picpay.application.dtos.RequestTransactionDto;
import com.financialsimplified.picpay.domain.entities.Transaction;
import com.financialsimplified.picpay.domain.services.CreateTransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final CreateTransactionService createTransactionService;

    @Autowired
    public TransactionController(CreateTransactionService createTransactionService) {
        this.createTransactionService = createTransactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody @Valid RequestTransactionDto requestTransactionDto) throws Exception {
        return ResponseEntity.ok(this.createTransactionService.execute(requestTransactionDto));
    }
}
