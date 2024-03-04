package com.financialsimplified.picpay.domain.services;
import com.financialsimplified.picpay.application.dtos.CreateUserDto;
import com.financialsimplified.picpay.domain.entities.User;
import com.financialsimplified.picpay.providers.database.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    private final UserRepository userRepository;

    @Autowired
    public CreateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(CreateUserDto createUserDto) {
        var user = new User();
        BeanUtils.copyProperties(createUserDto, user);
        return userRepository.save(user);
    }
}
