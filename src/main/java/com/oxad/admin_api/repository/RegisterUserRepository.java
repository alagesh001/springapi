package com.oxad.admin_api.repository;

import com.oxad.admin_api.entity.RegisterUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterUserRepository
        extends JpaRepository<RegisterUser, Long> {

    boolean existsByGymId(Integer gymId);

    boolean existsByGymnameAndLocation(
            String gymname,
            String location
    );

    boolean existsByMobile(Long mobile);
}