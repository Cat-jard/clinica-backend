-- ============================================================
-- Tabla + datos de ejemplo del microservicio de usuarios
-- SIHCE - Sistema de Historia Clinica Electronica
-- ============================================================
-- USAR SOLO SI FLYWAY NO CORRE.
--   Este script hace manualmente lo que normalmente hacen Flyway
--   (crear la tabla) y el SembradorDatos de Spring (insertar usuarios).
--
-- Requisito previo: la base 'clinica_usuarios' ya debe existir.
--
-- Ejecucion (conectado a la base clinica_usuarios):
--   psql -U postgres -d clinica_usuarios -f datos_usuarios.sql
--
-- Contrasena de TODOS los usuarios: Clinica123
--   (hash BCrypt, el mismo que genera BCryptPasswordEncoder / fuerza 10)
-- ============================================================

-- ----- 1) Tabla (equivalente a V1__crear_tabla_usuarios.sql) -----
CREATE TABLE IF NOT EXISTS usuarios (
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

CREATE INDEX IF NOT EXISTS idx_usuarios_rol    ON usuarios (rol);
CREATE INDEX IF NOT EXISTS idx_usuarios_estado ON usuarios (estado);

-- ----- 2) Datos de ejemplo (equivalente a SembradorDatos.java) -----
-- Todos con contrasena 'Clinica123' (hash BCrypt compartido).
INSERT INTO usuarios (dni, nombre, apellidos, email, telefono, password_hash, rol, especialidad, estado) VALUES
    ('47812345', 'Patricia', 'Núñez Campos',   'patricia.nunez@clinica.pe',  '986444555', '$2b$10$LiMUNfCf1RGwE5hIbX/JQO77yXUqfBmKc32GfQFqx3oJkluV4DzyO', 'ADMIN',       NULL,               'ACTIVO'),
    ('40128734', 'Rosa',     'García Pérez',    'rosa.garcia@clinica.pe',     '987654321', '$2b$10$LiMUNfCf1RGwE5hIbX/JQO77yXUqfBmKc32GfQFqx3oJkluV4DzyO', 'RECEPCION',   NULL,               'ACTIVO'),
    ('41298765', 'Lucía',    'Ramírez Soto',    'lucia.ramirez@clinica.pe',   '987111222', '$2b$10$LiMUNfCf1RGwE5hIbX/JQO77yXUqfBmKc32GfQFqx3oJkluV4DzyO', 'ENFERMERIA',  NULL,               'ACTIVO'),
    ('42876541', 'Luis',     'Torres Vega',     'luis.torres@clinica.pe',     '987333444', '$2b$10$LiMUNfCf1RGwE5hIbX/JQO77yXUqfBmKc32GfQFqx3oJkluV4DzyO', 'MEDICO',      'Medicina General', 'ACTIVO'),
    ('43219087', 'Carmen',   'Vega Flores',     'carmen.vega@clinica.pe',     '987555666', '$2b$10$LiMUNfCf1RGwE5hIbX/JQO77yXUqfBmKc32GfQFqx3oJkluV4DzyO', 'MEDICO',      'Cardiología',      'ACTIVO'),
    ('45678123', 'María',    'Torres Huamán',   'maria.torres@clinica.pe',    '987999000', '$2b$10$LiMUNfCf1RGwE5hIbX/JQO77yXUqfBmKc32GfQFqx3oJkluV4DzyO', 'LABORATORIO', NULL,               'ACTIVO'),
    ('44567812', 'Ricardo',  'Mendoza Quispe',  'ricardo.mendoza@clinica.pe', '987777888', '$2b$10$LiMUNfCf1RGwE5hIbX/JQO77yXUqfBmKc32GfQFqx3oJkluV4DzyO', 'RADIOLOGO',   NULL,               'ACTIVO'),
    ('46781234', 'Jorge',    'Salas Ríos',      'jorge.salas@clinica.pe',     '986111333', '$2b$10$LiMUNfCf1RGwE5hIbX/JQO77yXUqfBmKc32GfQFqx3oJkluV4DzyO', 'FARMACIA',    NULL,               'ACTIVO'),
    ('49234567', 'Elena',    'Castro Díaz',     'elena.castro@clinica.pe',    '986888999', '$2b$10$LiMUNfCf1RGwE5hIbX/JQO77yXUqfBmKc32GfQFqx3oJkluV4DzyO', 'SOPORTE',     NULL,               'ACTIVO'),
    ('48123456', 'Daniel',   'Rojas Medina',    'daniel.rojas@clinica.pe',    '986666777', '$2b$10$LiMUNfCf1RGwE5hIbX/JQO77yXUqfBmKc32GfQFqx3oJkluV4DzyO', 'MEDICO',      'Pediatría',        'INACTIVO')
ON CONFLICT (dni) DO NOTHING;
