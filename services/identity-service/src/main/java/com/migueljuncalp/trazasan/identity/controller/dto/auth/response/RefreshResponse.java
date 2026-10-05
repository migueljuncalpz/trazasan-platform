package com.migueljuncalp.trazasan.identity.controller.dto.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Refresh response")
public record RefreshResponse (
    String accessToken,
    String refreshToken,
    Long expiresInSeconds
) { }