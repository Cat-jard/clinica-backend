-- ============================================================
-- V1: Esquema base del microservicio de soporte (PostgreSQL)
-- SIHCE - Sistema de Historia Clinica Electronica
-- ============================================================

CREATE TABLE tickets (
    id                  BIGSERIAL     PRIMARY KEY,
    codigo              VARCHAR(12)   NOT NULL,
    titulo              VARCHAR(120)  NOT NULL,
    descripcion         VARCHAR(1000) NOT NULL,
    solicitante_dni     VARCHAR(8),
    solicitante_nombre  VARCHAR(150)  NOT NULL,
    categoria           VARCHAR(15)   NOT NULL,
    prioridad           VARCHAR(10)   NOT NULL DEFAULT 'MEDIA',
    estado              VARCHAR(12)   NOT NULL DEFAULT 'ABIERTO',
    asignado_a          VARCHAR(150),
    fecha_creacion      TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP,

    -- Reglas de integridad (BD relacional)
    CONSTRAINT uk_tickets_codigo UNIQUE (codigo),
    CONSTRAINT chk_tickets_solicitante_dni CHECK (solicitante_dni IS NULL OR solicitante_dni ~ '^[0-9]{8}$'),
    CONSTRAINT chk_tickets_categoria CHECK (categoria IN ('HARDWARE', 'SOFTWARE', 'RED', 'ACCESO', 'OTRO')),
    CONSTRAINT chk_tickets_prioridad CHECK (prioridad IN ('BAJA', 'MEDIA', 'ALTA', 'CRITICA')),
    CONSTRAINT chk_tickets_estado    CHECK (estado IN ('ABIERTO', 'EN_PROCESO', 'RESUELTO', 'CERRADO'))
);

-- Indices para los filtros frecuentes del tablero de Soporte
CREATE INDEX idx_tickets_estado    ON tickets (estado);
CREATE INDEX idx_tickets_prioridad ON tickets (prioridad);

COMMENT ON TABLE  tickets IS 'Incidencias de Soporte Tecnico / TI de la clinica';
COMMENT ON COLUMN tickets.codigo IS 'Codigo correlativo legible (p. ej. TCK-0001)';
COMMENT ON COLUMN tickets.estado IS 'ABIERTO / EN_PROCESO / RESUELTO / CERRADO';
