package com.oxad.admin_api.controller;

import com.oxad.admin_api.entity.RegisterUser;
import com.oxad.admin_api.repository.RegisterUserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registerusers")
@CrossOrigin
public class RegisterUserController {

    private final RegisterUserRepository repository;

    public RegisterUserController(
            RegisterUserRepository repository) {
        this.repository = repository;
    }

    // Get paginated institutes
    @GetMapping
    public Page<RegisterUser> getAllUsers(
            @RequestParam(defaultValue = "0") int page) {

        Pageable pageable =
                PageRequest.of(page, 5);

        return repository.findAll(pageable);
    }

    // Update login status
    @PutMapping("/{id}/login-status")
    public RegisterUser updateLoginStatus(
            @PathVariable Long id,
            @RequestParam Integer isLoggedIn) {

        if (isLoggedIn != 0 && isLoggedIn != 1) {
            throw new IllegalArgumentException(
                    "isLoggedIn must be either 0 or 1"
            );
        }

        RegisterUser user = repository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Institute not found"
                        ));

        user.setIsLoggedIn(isLoggedIn);

        return repository.save(user);
    }
   @PutMapping("/{id}/renewupdate")
public RegisterUser updateRenewalDate(
        @PathVariable Long id,
        @RequestParam String renewalDate) {

    RegisterUser user = repository
            .findById(id)
            .orElseThrow(() ->
                    new RuntimeException("Institute not found")
            );

    try {
        LocalDate date = LocalDate.parse(renewalDate);
        user.setRenewalDate(date);

        return repository.save(user);

    } catch (DateTimeParseException e) {
        throw new IllegalArgumentException(
                "Invalid date format. Use YYYY-MM-DD"
        );
    }
}
}