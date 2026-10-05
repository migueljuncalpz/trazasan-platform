package com.migueljuncalp.trazasan.identity.controller.dto.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Login response")
public record LoginResponse(

    String accessToken,
    String refreshToken,
    Long expiresInSeconds
) { }
