-- Set search path to user schema
SET search_path TO "user";

-- The users table will be created by Hibernate (ddl-auto=update) before this runs
-- But to be safe we create it here too so the insert doesn't fail
-- Hibernate will skip creation if table already exists

CREATE TABLE IF NOT EXISTS "user".users (
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(20)  NOT NULL
);

-- Insert admin user only if not already present
-- Password is BCrypt encoded 'Password@123'
INSERT INTO "user".users (username, password, role)
VALUES (
    'admin',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhy2',
    'ROLE_ADMIN'
)
ON CONFLICT (username) DO NOTHING;

-- Insert a default rep for testing
INSERT INTO "user".users (username, password, role)
VALUES (
    'rep1',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhy2',
    'ROLE_REPS'
)
ON CONFLICT (username) DO NOTHING;