-- ============================================================
-- SCRIPT SQL - Tablas del módulo Citas (SIHCE)
-- Motor: PostgreSQL (con uuid-ossp)
-- ============================================================

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ==================== CITAS ====================
CREATE TABLE IF NOT EXISTS citas (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    paciente_id UUID NOT NULL,
    medico_id BIGINT NOT NULL,
    fecha_cita DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PROGRAMADA',
    motivo VARCHAR(100) NOT NULL,
    observaciones TEXT,
    tipo_seguro VARCHAR(20),
    numero_historia VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ==================== CANCELACIONES DE CITAS ====================
CREATE TABLE IF NOT EXISTS cancelaciones_citas (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    cita_id UUID NOT NULL UNIQUE,
    motivo VARCHAR(200) NOT NULL,
    cancelado_por VARCHAR(100) NOT NULL,
    fecha_cancelacion TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cancelacion_cita FOREIGN KEY (cita_id) REFERENCES citas(id) ON DELETE RESTRICT
);

-- ==================== INDICES ====================
CREATE INDEX IF NOT EXISTS idx_citas_paciente_id ON citas(paciente_id);
CREATE INDEX IF NOT EXISTS idx_citas_medico_id ON citas(medico_id);
CREATE INDEX IF NOT EXISTS idx_citas_fecha ON citas(fecha_cita);
CREATE INDEX IF NOT EXISTS idx_citas_estado ON citas(estado);
CREATE INDEX IF NOT EXISTS idx_cancelaciones_cita_id ON cancelaciones_citas(cita_id);
