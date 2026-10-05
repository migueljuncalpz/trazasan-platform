package com.migueljuncalp.trazasan.identity.controller.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String hospitalSlug,
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) {

}
