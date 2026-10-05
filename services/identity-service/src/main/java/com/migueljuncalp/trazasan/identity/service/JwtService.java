package com.migueljuncalp.trazasan.identity.service;

import com.migueljuncalp.trazasan.identity.config.JwtProperties;
import com.migueljuncalp.trazasan.identity.domain.AppUser;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties properties;

    public JwtService(
        JwtEncoder jwtEncoder,
        JwtProperties properties
    ) {
        this.jwtEncoder = jwtEncoder;
        this.properties = properties;
    }

    public String createAccessToken(
        AppUser user,
        UUID sessionId
    ) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(
            properties.accessTokenDuration()
        );

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer(properties.issuer())
            .issuedAt(issuedAt)
            .expiresAt(expiresAt)
            .subject(user.getId().toString())
            .claim(
                "hospital_id",
                user.getHospital().getId().toString()
            )
            .claim(
                "roles",
                List.of(user.getRole().name())
            )
            .claim("session_id", sessionId.toString())
            .build();

        JwsHeader header = JwsHeader
            .with(SignatureAlgorithm.RS256)
            .type("JWT")
            .build();

        return jwtEncoder.encode(
            JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }

    public long getAccessTokenDurationSeconds() {
        return properties.accessTokenDuration().toSeconds();
    }
}