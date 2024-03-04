package com.financialsimplified.picpay.domain.services;

import com.financialsimplified.picpay.domain.entities.User;
import com.financialsimplified.picpay.providers.database.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetAllUsersService {

    public final UserRepository userRepository;

    public GetAllUsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute() {
       return this.userRepository.findAll();
    }
}
