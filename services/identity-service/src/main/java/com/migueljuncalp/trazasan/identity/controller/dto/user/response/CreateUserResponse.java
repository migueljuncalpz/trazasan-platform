package com.migueljuncalp.trazasan.identity.controller.dto.user.response;


import com.migueljuncalp.trazasan.identity.controller.dto.user.request.Role;

import java.util.List;

public record CreateUserResponse(
        String userEmail,
        Role roles
) {
}