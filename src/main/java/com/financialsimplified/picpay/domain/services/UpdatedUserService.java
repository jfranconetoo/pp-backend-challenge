package com.financialsimplified.picpay.domain.services;

import com.financialsimplified.picpay.application.dtos.UpdateUserDto;
import com.financialsimplified.picpay.domain.entities.User;
import com.financialsimplified.picpay.providers.database.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdatedUserService {
    private final UserRepository userRepository;
    @Autowired
    public UpdatedUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    User execute(UUID userId, UpdateUserDto updateUserDto) {
        return this.userRepository.findById(userId).map(user -> {
            BeanUtils.copyProperties(updateUserDto, user);
            return this.userRepository.save(user);
        }).orElseThrow();
    }
}
