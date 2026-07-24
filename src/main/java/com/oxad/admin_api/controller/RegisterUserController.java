package com.oxad.admin_api.controller;

import com.oxad.admin_api.dto.RegisterRequest;
import com.oxad.admin_api.entity.RegisterUser;
import com.oxad.admin_api.repository.RegisterUserRepository;
import com.oxad.admin_api.service.EmailService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registerusers")
@CrossOrigin
public class RegisterUserController {

    private final RegisterUserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
        private final EmailService emailService;

 @Value("${api.token}")
    private String apiToken;
    // Constructor
  public RegisterUserController(
        RegisterUserRepository repository,
        BCryptPasswordEncoder passwordEncoder,
        EmailService emailService) {

    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
    this.emailService = emailService;
}





   // Get paginated institutes
    @GetMapping
    public ResponseEntity<?> getAllUsers(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestHeader(
                    value = "X-API-TOKEN",
                    required = false
            )
            String token) {

        // Check token
        if (token == null ||
                !token.equals(apiToken)) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid API token");
        }


        Pageable pageable =
                PageRequest.of(page, 5);

        Page<RegisterUser> users =
                repository.findAll(pageable);

        return ResponseEntity.ok(users);
    
    }
    // Generate unique Gym ID
    private String generateUniqueGymId() {

        String gymId;

        do {

            long timestamp =
                    System.currentTimeMillis();

            int randomNumber =
                    ThreadLocalRandom.current()
                            .nextInt(1, 10);

            gymId = String.valueOf(
                    timestamp * randomNumber
            ).substring(0, 8);

        } while (
                repository.existsByGymId(
                        Integer.valueOf(gymId)
                )
        );

        return gymId;
    }


    // Generate random password
    private String generateRandomPassword(int length) {

        String characters =
                "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder password =
                new StringBuilder();

        for (int i = 0; i < length; i++) {

            int index =
                    ThreadLocalRandom.current()
                            .nextInt(characters.length());

            password.append(
                    characters.charAt(index)
            );
        }

        return password.toString();
    }

    // Update login status
  @PutMapping("/{id}/login-status")
public ResponseEntity<?> updateLoginStatus(

        @PathVariable Long id,

        @RequestParam Integer isLoggedIn,

        @RequestHeader(
                value = "X-API-TOKEN",
                required = false
        )
        String token) {

    // Check API token
    if (token == null ||
            !token.equals(apiToken)) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid API token");
    }


    // Validate login status
    if (isLoggedIn != 0 &&
            isLoggedIn != 1) {

        return ResponseEntity
                .badRequest()
                .body(
                        "isLoggedIn must be either 0 or 1"
                );
    }


    // Find institute
    RegisterUser user =
            repository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Institute not found"
                            )
                    );


    // Update login status
    user.setIsLoggedIn(isLoggedIn);


    // Save
    RegisterUser updatedUser =
            repository.save(user);


    return ResponseEntity.ok(updatedUser);
}


    // Update renewal date
  @PutMapping("/{id}/renewupdate")
public ResponseEntity<?> updateRenewalDate(

        @PathVariable Long id,

        @RequestParam String renewalDate,

        @RequestHeader(
                value = "X-API-TOKEN",
                required = false
        )
        String token) {

    // Check API token
    if (token == null ||
            !token.equals(apiToken)) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid API token");
    }


    // Find institute
    RegisterUser user =
            repository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Institute not found"
                            )
                    );


    try {

        // Convert String to LocalDate
        LocalDate date =
                LocalDate.parse(renewalDate);

        // Update renewal date
        user.setRenewalDate(date);

        // Save to database
        RegisterUser updatedUser =
                repository.save(user);

        return ResponseEntity.ok(updatedUser);

    } catch (DateTimeParseException e) {

        return ResponseEntity
                .badRequest()
                .body(
                        "Invalid date format. Use YYYY-MM-DD"
                );
    }
}


    // Register new gym
    @PostMapping("/register")
    public ResponseEntity<?> registerGym(
            @RequestBody RegisterRequest request,
         @RequestHeader(
                value = "X-API-TOKEN",
                required = false
        )
        String token)
         {
 // Check API token
    if (token == null ||
            !token.equals(apiToken)) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid API token");
    }

        try {

            // Validate input
            if (request.getOwner() == null ||
                    request.getGymname() == null ||
                    request.getLocation() == null ||
                    request.getMob() == null) {

                return ResponseEntity
                        .badRequest()
                        .body(
                                Map.of(
                                        "success", false,
                                        "message",
                                        "Missing data",
                                        "isAdmin", 0
                                )
                        );
            }


            // Check if gym already exists
            boolean gymExists =
                    repository.existsByGymnameAndLocation(
                            request.getGymname(),
                            request.getLocation()
                    );

            if (gymExists) {

                return ResponseEntity.ok(
                        Map.of(
                                "success", true,
                                "message",
                                "Gym already registered"
                        )
                );
            }


            // Check mobile number
            boolean mobileExists =
                    repository.existsByMobile(
                            request.getMob()
                    );

            if (mobileExists) {

                return ResponseEntity.ok(
                        Map.of(
                                "success", false,
                                "message",
                                "Mobile number already registered"
                        )
                );
            }


            // Generate Gym ID
            String gymId =
                    generateUniqueGymId();


            // Generate random password
            String randomPassword =
                    generateRandomPassword(6);


            // Hash password
            String hashedPassword =
                    passwordEncoder.encode(
                            randomPassword
                    );


            // Create user
            RegisterUser user =
                    new RegisterUser();


            user.setGymname(
                    request.getGymname()
            );

            user.setLocation(
                    request.getLocation()
            );

            user.setOwner(
                    request.getOwner()
            );

            user.setEmail(
                    request.getEmail()
            );

            user.setMob(
                    request.getMob()
            );

            user.setAdminCont(
                    request.getMob()
            );

            user.setGymId(
                    Integer.valueOf(gymId)
            );

            user.setPassword(
                    hashedPassword
            );
user.setRenewalDate(
        LocalDate.now().plusMonths(
                request.getPlantype()
        )
);

user.setPlanType(
        request.getPlantype()
);

            user.setIsActive(2);

            user.setIsLoggedIn(0);


            // Save gym
            repository.save(user);

emailService.sendGymPassword(
        request.getGymname(),
        gymId,
        randomPassword
);
            // Return response
            return ResponseEntity.ok(
                    Map.of(
                            "success", true,
                            "message",
                            "Gym registered successfully.",
                            "gymId",
                            gymId,
                            "password",
                            randomPassword,
                            "isAdmin",
                            0
                    )
            );


        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body(
                            Map.of(
                                    "success", false,
                                    "message",
                                    "Registration failed",
                                    "isAdmin", 0
                            )
                    );
        }
    }
}