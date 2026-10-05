package com.migueljuncalp.trazasan.identity.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "user_sessions",
        schema = "identity",
        indexes = {
                @Index(
                        name = "ix_user_sessions_user_id",
                        columnList = "user_id"
                )
        }
)
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(
            name = "refresh_token_hash",
            nullable = false,
            unique = true,
            length = 64
    )
    private String refreshTokenHash;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private Instant createdAt = Instant.now();

    @Column(name = "last_used_at")
    private Instant lastUsedAt;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    protected UserSession() {
    }

    public UserSession(
            AppUser user,
            String refreshTokenHash,
            Instant expiresAt,
            String userAgent,
            String ipAddress
    ) {
        this.user = user;
        this.refreshTokenHash = refreshTokenHash;
        this.expiresAt = expiresAt;
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
    }

    public void revoke(Instant revokedAt) {
        this.revokedAt = revokedAt;
    }

    public void registerUse(Instant usedAt) {
        this.lastUsedAt = usedAt;
    }

    public void rotateToken(
            String newRefreshTokenHash,
            Instant newExpiresAt,
            Instant usedAt
    ) {
        this.refreshTokenHash = newRefreshTokenHash;
        this.expiresAt = newExpiresAt;
        this.lastUsedAt = usedAt;
    }

    public boolean isActive(Instant now) {
        return revokedAt == null && expiresAt.isAfter(now);
    }

    public UUID getId() {
        return id;
    }

    public AppUser getUser() {
        return user;
    }

    public String getRefreshTokenHash() {
        return refreshTokenHash;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }
}
