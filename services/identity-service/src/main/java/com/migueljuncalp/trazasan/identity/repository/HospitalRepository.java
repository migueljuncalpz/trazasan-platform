package com.migueljuncalp.trazasan.identity.repository;

import com.migueljuncalp.trazasan.identity.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HospitalRepository extends JpaRepository<Hospital, UUID> {
    Optional<Hospital> findBySlug(String slug);
}
