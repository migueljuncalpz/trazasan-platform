package com.migueljuncalp.trazasan.identity.service.auth.session;

import com.migueljuncalp.trazasan.identity.service.auth.result.CreatedSession;
import com.migueljuncalp.trazasan.identity.domain.AppUser;
import com.migueljuncalp.trazasan.identity.domain.UserSession;
import com.migueljuncalp.trazasan.identity.repository.UserSessionRepository;
import com.migueljuncalp.trazasan.identity.security.RefreshTokenGenerator;
import com.migueljuncalp.trazasan.identity.security.RefreshTokenHasher;
import com.migueljuncalp.trazasan.identity.service.exceptions.InvalidRefreshTokenException;
import com.migueljuncalp.trazasan.identity.service.auth.result.RefreshedSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Service
public class SessionService {

    private final UserSessionRepository sessionRepository;
    private final RefreshTokenGenerator tokenGenerator;
    private final RefreshTokenHasher tokenHasher;

    public SessionService(UserSessionRepository sessionRepository, RefreshTokenGenerator tokenGenerator, RefreshTokenHasher tokenHasher) {
        this.sessionRepository = sessionRepository;
        this.tokenGenerator = tokenGenerator;
        this.tokenHasher = tokenHasher;
    }

    public CreatedSession create(AppUser user) {
        String refreshToken = tokenGenerator.generate();
        String tokenHash = tokenHasher.hash(refreshToken);
        Instant expiresAt = Instant.now().plus(Duration.ofDays(7));

        UserSession session = new UserSession(
                user,
                tokenHash,
                expiresAt,
                null,
                null
        );

        UserSession savedSession =
                sessionRepository.save(session);

        return new CreatedSession(
                savedSession.getId(),
                refreshToken,
                savedSession.getExpiresAt()
        );
    }

    @Transactional
    public RefreshedSession refresh(String refreshToken){
        UserSession userSession = sessionRepository.findUserSessionByRefreshTokenHash(
                tokenHasher.hash(refreshToken)).orElseThrow(
                        InvalidRefreshTokenException::new
        );

        Instant now = Instant.now();

        if(userSession.isActive(now)){
            throw new InvalidRefreshTokenException();
        }

        String newRefreshToken = tokenGenerator.generate();
        String newTokenHash = tokenHasher.hash(newRefreshToken);
        Instant newExpiration = now.plus(Duration.ofDays(7));

        userSession.rotateToken(newTokenHash, newExpiration, now);

        RefreshedSession createdSession = new RefreshedSession(
                userSession.getId(),
                userSession.getUser(),
                newRefreshToken,
                newExpiration
                );

        return createdSession;
    }

    @Transactional
    public void logout(String refreshToken) {
        String tokenHash = tokenHasher.hash(refreshToken);

        sessionRepository
                .findUserSessionByRefreshTokenHash(tokenHash)
                .ifPresent(session ->
                        session.revoke(Instant.now())
                );
    }
}