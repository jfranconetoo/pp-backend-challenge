package com.financialsimplified.picpay.application.controllers;

import com.financialsimplified.picpay.application.dtos.CreateUserDto;
import com.financialsimplified.picpay.domain.entities.User;
import com.financialsimplified.picpay.domain.services.CreateUserService;
import com.financialsimplified.picpay.domain.services.GetAllUsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {
    private final CreateUserService createUserService;
    private final GetAllUsersService getAllUsersService;

    public UserController(CreateUserService createUserService, GetAllUsersService getAllUsersService){
        this.createUserService = createUserService;
        this.getAllUsersService = getAllUsersService;
    };

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto requestUserRecordDto) {
        var user = createUserService.execute(requestUserRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(this.getAllUsersService.execute());
    }
}
