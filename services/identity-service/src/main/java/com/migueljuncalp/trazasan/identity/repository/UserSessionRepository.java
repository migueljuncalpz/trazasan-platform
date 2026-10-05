package com.migueljuncalp.trazasan.identity.repository;

import com.migueljuncalp.trazasan.identity.domain.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {
    Optional<UserSession> findUserSessionByRefreshTokenHash(String refreshTokenHash);
}
