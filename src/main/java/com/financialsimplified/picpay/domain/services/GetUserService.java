package com.financialsimplified.picpay.domain.services;

import com.financialsimplified.picpay.domain.entities.User;
import com.financialsimplified.picpay.providers.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserService {
    private final UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID userId) throws Exception {
        return this.userRepository.findById(userId).orElseThrow(()-> new Exception("User Not Found"));
    }
}
