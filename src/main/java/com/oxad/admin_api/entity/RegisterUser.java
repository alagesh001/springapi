package com.oxad.admin_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "gymdata")
public class RegisterUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, length = 100)
    private String 	gymname;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    // Getters and Setters

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getName() {
        return 	gymname;
    }

    public void setName(String name) {
        this.gymname = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}