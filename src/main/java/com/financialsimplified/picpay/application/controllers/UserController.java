package com.financialsimplified.picpay.application.controllers;

import com.financialsimplified.picpay.application.dtos.CreateUserRecordDto;
import com.financialsimplified.picpay.domain.entities.User;
import com.financialsimplified.picpay.domain.services.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/users")
public class UserController {
    private final CreateUserService createUserService;

    @Autowired
    public UserController(CreateUserService createUserService){
        this.createUserService = createUserService;
    };

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRecordDto requestUserRecordDto) {
        var user = createUserService.execute(requestUserRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
