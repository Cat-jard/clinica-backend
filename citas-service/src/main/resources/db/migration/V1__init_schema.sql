-- ============================================================
-- V1: Esquema base del microservicio de citas (PostgreSQL)
-- SIHCE - Sistema de Historia Clinica Electronica
-- Generado a partir del modelo JPA (Hibernate) para que
-- 'ddl-auto: validate' coincida exactamente con estas tablas.
-- ============================================================

-- Agenda de citas
CREATE TABLE citas (
    id               uuid                          NOT NULL,
    paciente_id      uuid                          NOT NULL,
    medico_id        bigint                        NOT NULL,
    fecha_cita       date                          NOT NULL,
    hora_inicio      time(0) without time zone     NOT NULL,
    hora_fin         time(0) without time zone     NOT NULL,
    estado           character varying(20)         NOT NULL,
    motivo           character varying(100)        NOT NULL,
    observaciones    text,
    tipo_seguro      character varying(20),
    numero_historia  character varying(20),
    paciente_nombre  character varying(300),
    medico_nombre    character varying(200),
    created_at       timestamp(6) without time zone NOT NULL,
    updated_at       timestamp(6) without time zone NOT NULL,
    CONSTRAINT citas_pkey PRIMARY KEY (id)
);

-- Registro de cancelaciones (1:1 con la cita)
CREATE TABLE cancelaciones_citas (
    id                 uuid                          NOT NULL,
    cita_id            uuid                          NOT NULL,
    motivo             character varying(200)        NOT NULL,
    cancelado_por      character varying(100)        NOT NULL,
    fecha_cancelacion  timestamp(6) without time zone NOT NULL,
    created_at         timestamp(6) without time zone NOT NULL,
    CONSTRAINT cancelaciones_citas_pkey PRIMARY KEY (id),
    CONSTRAINT cancelaciones_citas_cita_id_key UNIQUE (cita_id),
    CONSTRAINT fk_cancelacion_cita FOREIGN KEY (cita_id) REFERENCES citas (id)
);

-- Indices para busquedas frecuentes (agenda por medico / por paciente / por fecha)
CREATE INDEX idx_citas_medico  ON citas (medico_id);
CREATE INDEX idx_citas_pacient ON citas (paciente_id);
CREATE INDEX idx_citas_fecha   ON citas (fecha_cita);
CREATE INDEX idx_citas_estado  ON citas (estado);
