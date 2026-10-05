CREATE TABLE IF NOT EXISTS identity.hospitals (
    id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    slug VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS identity.users (
    id UUID PRIMARY KEY,
    hospital_id UUID NOT NULL,
    email VARCHAR(254) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    role VARCHAR(30) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_hospital
        FOREIGN KEY (hospital_id) REFERENCES identity.hospitals (id),
    CONSTRAINT uq_users_hospital_email UNIQUE (hospital_id, email),
    CONSTRAINT ck_users_role CHECK (role IN ('ADMIN', 'HOSPITAL_USER'))
);

CREATE INDEX IF NOT EXISTS ix_users_email ON identity.users (email);
