CREATE TABLE identity.user_sessions (
                                        id UUID PRIMARY KEY,
                                        user_id UUID NOT NULL,
                                        refresh_token_hash VARCHAR(64) NOT NULL UNIQUE,
                                        expires_at TIMESTAMPTZ NOT NULL,
                                        revoked_at TIMESTAMPTZ,
                                        created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        last_used_at TIMESTAMPTZ,
                                        user_agent VARCHAR(500),
                                        ip_address VARCHAR(45),

                                        CONSTRAINT fk_user_sessions_user
                                            FOREIGN KEY (user_id)
                                                REFERENCES identity.users(id)
                                                ON DELETE CASCADE
);

CREATE INDEX ix_user_sessions_user_id
    ON identity.user_sessions(user_id);

CREATE INDEX ix_user_sessions_expires_at
    ON identity.user_sessions(expires_at);