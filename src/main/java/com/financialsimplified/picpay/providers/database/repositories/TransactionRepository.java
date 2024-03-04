package com.financialsimplified.picpay.providers.database.repositories;

import com.financialsimplified.picpay.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {}
