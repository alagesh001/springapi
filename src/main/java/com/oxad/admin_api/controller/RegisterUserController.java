package com.oxad.admin_api.controller;


import com.oxad.admin_api.entity.RegisterUser;
import com.oxad.admin_api.repository.RegisterUserRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registerusers")
@CrossOrigin
public class RegisterUserController {

    private final RegisterUserRepository repository;

    public RegisterUserController(RegisterUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<RegisterUser> getAllUsers() {

        return repository.findAll();
    }
}
