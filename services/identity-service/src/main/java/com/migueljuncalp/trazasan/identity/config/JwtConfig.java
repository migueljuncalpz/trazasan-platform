package com.migueljuncalp.trazasan.identity.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {


    @Bean
    RSAPublicKey jwtPublicKey(JwtProperties properties) throws IOException {
        try (var input = properties.publicKey().getInputStream()) {
            return RsaKeyConverters.x509().convert(input);
        }
    }

    @Bean
    RSAPrivateKey jwtPrivateKey(JwtProperties properties) throws IOException {
        try (var input = properties.privateKey().getInputStream()) {
            return RsaKeyConverters.pkcs8().convert(input);
        }
    }

    @Bean
    JwtEncoder jwtEncoder(
        RSAPublicKey publicKey,
        RSAPrivateKey privateKey
    ) {
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .build();

        var jwkSet = new JWKSet(rsaKey);
        var jwkSource =
            new ImmutableJWKSet<SecurityContext>(jwkSet);

        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    JwtDecoder jwtDecoder(
        RSAPublicKey publicKey,
        JwtProperties properties
    ) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder
            .withPublicKey(publicKey)
            .signatureAlgorithm(SignatureAlgorithm.RS256)
            .build();

        decoder.setJwtValidator(
            JwtValidators.createDefaultWithIssuer(properties.issuer())
        );

        return decoder;
    }
}