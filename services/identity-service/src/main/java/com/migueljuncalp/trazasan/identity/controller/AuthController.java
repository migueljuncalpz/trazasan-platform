package com.migueljuncalp.trazasan.identity.controller;

import com.migueljuncalp.trazasan.identity.controller.dto.auth.request.LoginRequest;
import com.migueljuncalp.trazasan.identity.controller.dto.auth.response.LoginResponse;
import com.migueljuncalp.trazasan.identity.controller.dto.auth.response.RefreshResponse;
import com.migueljuncalp.trazasan.identity.service.AuthService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/status")
    ResponseEntity getStatus(){
        return ResponseEntity.ok("Login OK");
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login( @Valid @RequestBody LoginRequest request){
        LoginResponse result =
                authService.login(request);
        ResponseCookie responseCookie = createRefreshCookie(result.refreshToken());
        return ResponseEntity.ok().header(
                HttpHeaders.SET_COOKIE,
                responseCookie.toString()
        ).body(result);
    }

    private ResponseCookie createRefreshCookie(
            String refreshToken
    ) {
        return ResponseCookie
                .from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(false) // true en producción con HTTPS
                .sameSite("Lax")
                .path("/api/v1/auth")
                .maxAge(Duration.ofDays(7))
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(
            @Parameter(hidden = true)
            @CookieValue(required = true, name = "refresh_token")
            String refreshToken
    ) {
        RefreshResponse result =
                authService.refresh(refreshToken);

        ResponseCookie cookie = createRefreshCookie(
                result.refreshToken()
        );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        cookie.toString()
                )
                .body(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @Parameter(hidden = true)
            @CookieValue(
                    name = "refresh_token",
                    required = false
            )
            String refreshToken
    ) {
        if (refreshToken != null) {
            authService.logout(refreshToken);
        }

        ResponseCookie deletedCookie = ResponseCookie
                .from("refresh_token", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/api/v1/auth")
                .maxAge(Duration.ZERO)
                .build();

        return ResponseEntity.noContent()
                .header(
                        HttpHeaders.SET_COOKIE,
                        deletedCookie.toString()
                )
                .build();
    }

}
