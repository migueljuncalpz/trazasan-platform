package com.migueljuncalp.trazasan.identity.repository;

import com.migueljuncalp.trazasan.identity.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface HospitalRepository extends JpaRepository<Hospital, UUID> {
    Optional<Hospital> findBySlug(String slug);

    @Query("SELECT h.domain FROM Hospital h WHERE h.slug = :username")
    Optional<String> findHospitalDomainByHospitalSlug(@Param("hospitalSlug") String hospitalSlug);}
