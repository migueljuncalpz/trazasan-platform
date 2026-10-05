package com.migueljuncalp.trazasan.identity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import java.time.Duration;

@ConfigurationProperties(prefix = "trazasan.jwt")
public record JwtProperties(
    String issuer,
    Duration accessTokenDuration,
    Resource privateKey,
    Resource publicKey
) {
}