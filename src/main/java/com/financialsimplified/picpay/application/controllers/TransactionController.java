package com.financialsimplified.picpay.application.controllers;

import com.financialsimplified.picpay.application.dtos.RequestTransactionDto;
import com.financialsimplified.picpay.domain.entities.Transaction;
import com.financialsimplified.picpay.domain.services.CreateTransactionService;
import com.financialsimplified.picpay.domain.services.GetAllTransactionsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final CreateTransactionService createTransactionService;
    private final GetAllTransactionsService getAllTransactionsService;
    public TransactionController(CreateTransactionService createTransactionService, GetAllTransactionsService getAllTransactionsService) {
        this.createTransactionService = createTransactionService;
        this.getAllTransactionsService = getAllTransactionsService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody @Valid RequestTransactionDto requestTransactionDto) throws Exception {
        return ResponseEntity.ok(this.createTransactionService.execute(requestTransactionDto));
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(this.getAllTransactionsService.execute());
    }
}
