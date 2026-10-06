package com.migueljuncalp.trazasan.identity.service.auth.result;

import com.migueljuncalp.trazasan.identity.domain.AppUser;

import java.time.Instant;
import java.util.UUID;

public record RefreshedSession(
    UUID sessionId,
    AppUser user,
    String refreshToken,
    Instant expiresAt
) {
}