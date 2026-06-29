-- ============================================================
-- V1: Esquema base del microservicio de auditoria (PostgreSQL)
-- SIHCE - Sistema de Historia Clinica Electronica
-- ============================================================

CREATE TABLE registros_auditoria (
    id              BIGSERIAL    PRIMARY KEY,
    usuario_email   VARCHAR(150),
    usuario_nombre  VARCHAR(150),
    rol             VARCHAR(20),
    accion          VARCHAR(20)  NOT NULL,
    modulo          VARCHAR(60)  NOT NULL,
    descripcion     VARCHAR(300) NOT NULL,
    entidad         VARCHAR(120),
    ip              VARCHAR(45),
    fecha           TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Reglas de integridad (BD relacional)
    CONSTRAINT chk_auditoria_accion CHECK (accion IN (
        'LOGIN', 'LOGOUT', 'CREACION', 'ACTUALIZACION',
        'ELIMINACION', 'CONSULTA', 'ACCESO_DENEGADO'
    ))
);

-- Indices para las consultas frecuentes de la bitacora (filtro por accion/modulo y orden por fecha)
CREATE INDEX idx_auditoria_accion ON registros_auditoria (accion);
CREATE INDEX idx_auditoria_modulo ON registros_auditoria (modulo);
CREATE INDEX idx_auditoria_fecha  ON registros_auditoria (fecha DESC);

COMMENT ON TABLE  registros_auditoria IS 'Bitacora inalterable de eventos del sistema (trazabilidad - Ley 30024 RENHICE)';
COMMENT ON COLUMN registros_auditoria.accion IS 'Tipo de evento: LOGIN, CREACION, ACTUALIZACION, etc.';
COMMENT ON COLUMN registros_auditoria.entidad IS 'Recurso afectado por la accion (opcional)';
