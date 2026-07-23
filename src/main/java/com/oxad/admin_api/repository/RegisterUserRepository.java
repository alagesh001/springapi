package com.oxad.admin_api.repository;

import com.oxad.admin_api.entity.RegisterUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterUserRepository
        extends JpaRepository<RegisterUser, Long> {

}
