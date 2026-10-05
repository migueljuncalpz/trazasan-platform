package com.migueljuncalp.trazasan.identity.repository;

import com.migueljuncalp.trazasan.identity.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    boolean existsByHospitalIdAndEmailIgnoreCase(UUID hospitalId, String email);
    Optional<AppUser> findAppUsersByHospital_SlugAndEmailIgnoreCase(String hospitalName, String email);
}
