package com.migueljuncalp.trazasan.identity.controller.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Info to assign the new user in Trazasan")
public record CreateUserRequest(
    @NotBlank
    String hospitalSlug,

    @NotBlank
    @Email
    String email,

    @NotBlank
    String password,

    @NotNull
    Role role // not null in ENUMS
){}