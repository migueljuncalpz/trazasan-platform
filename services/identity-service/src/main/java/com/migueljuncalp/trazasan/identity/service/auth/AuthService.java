package com.migueljuncalp.trazasan.identity.service.auth;

import com.migueljuncalp.trazasan.identity.controller.dto.auth.response.RefreshResponse;
import com.migueljuncalp.trazasan.identity.service.auth.session.SessionService;
import com.migueljuncalp.trazasan.identity.service.exceptions.InvalidCredentialsException;
import com.migueljuncalp.trazasan.identity.service.auth.result.CreatedSession;
import com.migueljuncalp.trazasan.identity.controller.dto.auth.request.LoginRequest;
import com.migueljuncalp.trazasan.identity.controller.dto.auth.response.LoginResponse;
import com.migueljuncalp.trazasan.identity.domain.AppUser;
import com.migueljuncalp.trazasan.identity.repository.AppUserRepository;
import com.migueljuncalp.trazasan.identity.service.auth.result.RefreshedSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;

    public AuthService (AppUserRepository appUserRepository,
                        JwtService jwtService,
                        PasswordEncoder passwordEncoder,
                        SessionService sessionService){
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.sessionService = sessionService;
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        AppUser user = appUserRepository
                .findAppUsersByHospital_SlugAndEmailIgnoreCase(
                        request.hospitalSlug(),
                        email
                )
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.isEnabled()) {
            throw new InvalidCredentialsException();
        }

        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash()
        )) {
            throw new InvalidCredentialsException();
        }

        CreatedSession session = sessionService.create(user);

        String accessToken = jwtService.createAccessToken(
                user,
                session.getId()
        );

        return new LoginResponse(
                accessToken,
                session.refreshToken(),
                jwtService.getAccessTokenDurationSeconds()
        );
    }

    @Transactional
    public RefreshResponse refresh(String refreshToken){

        RefreshedSession refreshedSession = sessionService.refresh(refreshToken);

        String accessToken = jwtService.createAccessToken(
                refreshedSession.user(),
                refreshedSession.sessionId()
        );

        return new RefreshResponse(
                accessToken,
                refreshedSession.refreshToken(),
                jwtService.getAccessTokenDurationSeconds()
        );
    }

    @Transactional
    public void logout(String refreshToken){
        sessionService.logout(refreshToken);
    }

}
