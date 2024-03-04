package com.financialsimplified.picpay.providers.database.repositories;

import com.financialsimplified.picpay.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {}
