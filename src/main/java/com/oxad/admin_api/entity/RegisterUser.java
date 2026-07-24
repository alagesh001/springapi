package com.oxad.admin_api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "gymdata")
public class RegisterUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String gymname;

    @Column(length = 255)
    private String location;

      @Column(name = "mob")
    private long mobile;

    @Column(name = "plantype")
    private Integer planType;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "renewaldate")
    private LocalDate renewalDate;

    @Column(name = "gymid")
    private Integer gymId;

    @Column(name = "is_logged_in")
    private Integer isLoggedIn;

@Column(name = "owner")
private String owner;

@Column(name = "email")
private String email;

@Column(name = "password")
private String password;

@Column(name = "admin_cont")
private Long adminCont;
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGymname() {
        return gymname;
    }

    public void setGymname(String gymname) {
        this.gymname = gymname;
    }
public String getOwner() {
    return owner;
}

public void setOwner(String owner) {
    this.owner = owner;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}

public Long getAdminCont() {
    return adminCont;
}

public void setAdminCont(Long adminCont) {
    this.adminCont = adminCont;
}
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

      public long getMob() {
        return mobile;
    }
   public void setMob(long mobile) {
        this.mobile = mobile;
    }
    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public LocalDate getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(LocalDate renewalDate) {
        this.renewalDate = renewalDate;
    }

    public Integer getGymId() {
        return gymId;
    }

    public void setGymId(Integer gymId) {
        this.gymId = gymId;
    }

    public Integer getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(Integer isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}