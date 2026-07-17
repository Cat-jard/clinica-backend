ALTER TABLE registros_triaje
    ADD COLUMN IF NOT EXISTS estado character varying(20) NOT NULL DEFAULT 'CLASIFICADO',
    ADD COLUMN IF NOT EXISTS cola_id uuid;

CREATE INDEX idx_registros_estado ON registros_triaje(estado);
