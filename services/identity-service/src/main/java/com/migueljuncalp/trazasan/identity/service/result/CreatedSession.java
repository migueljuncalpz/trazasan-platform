package com.migueljuncalp.trazasan.identity.service.result;

import java.time.Instant;
import java.util.UUID;

public record CreatedSession(
    UUID sessionId,
    String refreshToken,
    Instant expiresAt
) {
    public UUID getId() {
        return this.sessionId;
    }
}