package com.oxad.admin_api.controller;

import com.oxad.admin_api.entity.RegisterUser;
import com.oxad.admin_api.repository.RegisterUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registerusers")
@CrossOrigin
public class RegisterUserController {

    private final RegisterUserRepository repository;

    public RegisterUserController(RegisterUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Page<RegisterUser> getAllUsers(
            @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 5);

        return repository.findAll(pageable);
    }
}