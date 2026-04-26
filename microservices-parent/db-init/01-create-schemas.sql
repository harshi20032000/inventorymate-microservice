-- Create all schemas
CREATE SCHEMA IF NOT EXISTS "order"     AUTHORIZATION postgres;
CREATE SCHEMA IF NOT EXISTS party       AUTHORIZATION postgres;
CREATE SCHEMA IF NOT EXISTS payment     AUTHORIZATION postgres;
CREATE SCHEMA IF NOT EXISTS product     AUTHORIZATION postgres;
CREATE SCHEMA IF NOT EXISTS transport   AUTHORIZATION postgres;
CREATE SCHEMA IF NOT EXISTS "user"      AUTHORIZATION postgres;
CREATE SCHEMA IF NOT EXISTS warehouse   AUTHORIZATION postgres;
CREATE SCHEMA IF NOT EXISTS audit       AUTHORIZATION postgres;

-- Grant usage so each schema is accessible
GRANT USAGE ON SCHEMA "order"    TO postgres;
GRANT USAGE ON SCHEMA party      TO postgres;
GRANT USAGE ON SCHEMA payment    TO postgres;
GRANT USAGE ON SCHEMA product    TO postgres;
GRANT USAGE ON SCHEMA transport  TO postgres;
GRANT USAGE ON SCHEMA "user"     TO postgres;
GRANT USAGE ON SCHEMA warehouse  TO postgres;
GRANT USAGE ON SCHEMA audit      TO postgres;