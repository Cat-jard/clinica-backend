-- ============================================================
-- V1: Esquema base del microservicio de usuarios (PostgreSQL)
-- SIHCE - Sistema de Historia Clinica Electronica
-- ============================================================

CREATE TABLE usuarios (
    id              BIGSERIAL    PRIMARY KEY,
    dni             VARCHAR(8)   NOT NULL,
    nombre          VARCHAR(80)  NOT NULL,
    apellidos       VARCHAR(120) NOT NULL,
    email           VARCHAR(150) NOT NULL,
    telefono        VARCHAR(9)   NOT NULL,
    password_hash   VARCHAR(100) NOT NULL,
    rol             VARCHAR(20)  NOT NULL,
    especialidad    VARCHAR(80),
    estado          VARCHAR(10)  NOT NULL DEFAULT 'ACTIVO',
    ultimo_acceso   TIMESTAMP,
    fecha_creacion  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP,

    -- Reglas de integridad (BD relacional)
    CONSTRAINT uk_usuarios_dni   UNIQUE (dni),
    CONSTRAINT uk_usuarios_email UNIQUE (email),
    CONSTRAINT chk_usuarios_dni      CHECK (dni ~ '^[0-9]{8}$'),
    CONSTRAINT chk_usuarios_telefono CHECK (telefono ~ '^[0-9]{9}$'),
    CONSTRAINT chk_usuarios_estado   CHECK (estado IN ('ACTIVO', 'INACTIVO')),
    CONSTRAINT chk_usuarios_rol      CHECK (rol IN (
        'ADMIN', 'RECEPCION', 'ENFERMERIA', 'MEDICO',
        'LABORATORIO', 'RADIOLOGO', 'FARMACIA', 'SOPORTE', 'PACIENTE'
    ))
);

-- Indices para busquedas frecuentes (filtro por rol / estado y login por email)
CREATE INDEX idx_usuarios_rol    ON usuarios (rol);
CREATE INDEX idx_usuarios_estado ON usuarios (estado);

COMMENT ON TABLE  usuarios IS 'Personal del sistema y cuentas de pacientes (RBAC - Ley 29733)';
COMMENT ON COLUMN usuarios.dni IS 'Documento de identidad, llave maestra (8 digitos) - Ley 30024 RENHICE';
COMMENT ON COLUMN usuarios.estado IS 'ACTIVO / INACTIVO. Los usuarios no se eliminan, solo se desactivan.';
