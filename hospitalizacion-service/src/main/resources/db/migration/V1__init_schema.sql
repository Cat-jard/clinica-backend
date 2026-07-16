-- ============================================================
-- V1: Esquema base de hospitalizacion (PostgreSQL) - desde JPA (validate)
-- ============================================================

CREATE TABLE autorizaciones_ingreso (
    firmado boolean NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    hospitalizacion_id uuid NOT NULL,
    id uuid NOT NULL,
    representante_dni character varying(20),
    representante_nombre character varying(300),
    firma_base64 text,
    texto_legal text NOT NULL
);
CREATE TABLE camas (
    created_at timestamp(6) without time zone NOT NULL,
    fecha_ingreso timestamp(6) without time zone,
    updated_at timestamp(6) without time zone NOT NULL,
    numero character varying(10) NOT NULL,
    id uuid NOT NULL,
    paciente_id uuid,
    estado character varying(20) NOT NULL,
    servicio character varying(50) NOT NULL,
    medico_nombre character varying(200),
    paciente_nombre character varying(300),
    diagnostico text
);
CREATE TABLE epicrisis (
    firmado boolean NOT NULL,
    proxima_cita date,
    created_at timestamp(6) without time zone NOT NULL,
    fecha_alta timestamp(6) without time zone NOT NULL,
    fecha_ingreso timestamp(6) without time zone NOT NULL,
    hospitalizacion_id uuid NOT NULL,
    id uuid NOT NULL,
    medico_id character varying(100) NOT NULL,
    diagnostico_final character varying(200) NOT NULL,
    diagnostico_ingreso character varying(200) NOT NULL,
    medico_nombre character varying(200) NOT NULL,
    complicaciones text,
    evolucion text NOT NULL,
    firma_base64 text,
    motivo_ingreso text NOT NULL,
    procedimientos text,
    recomendaciones text NOT NULL,
    tratamiento text NOT NULL
);
CREATE TABLE hospitalizaciones (
    created_at timestamp(6) without time zone NOT NULL,
    fecha_alta timestamp(6) without time zone,
    fecha_ingreso timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    cama_numero character varying(10) NOT NULL,
    cama_id uuid NOT NULL,
    id uuid NOT NULL,
    medico_id uuid NOT NULL,
    paciente_id uuid NOT NULL,
    estado character varying(20) NOT NULL,
    paciente_dni character varying(20) NOT NULL,
    tipo_alta character varying(30),
    servicio character varying(50) NOT NULL,
    user_id_alta character varying(100),
    user_id_ingreso character varying(100) NOT NULL,
    diagnostico_alta character varying(200),
    diagnostico_ingreso character varying(200) NOT NULL,
    medico_nombre character varying(200) NOT NULL,
    paciente_nombre character varying(300) NOT NULL,
    motivo_ingreso text NOT NULL,
    observaciones text
);
ALTER TABLE ONLY autorizaciones_ingreso
    ADD CONSTRAINT autorizaciones_ingreso_hospitalizacion_id_key UNIQUE (hospitalizacion_id);
ALTER TABLE ONLY autorizaciones_ingreso
    ADD CONSTRAINT autorizaciones_ingreso_pkey PRIMARY KEY (id);
ALTER TABLE ONLY camas
    ADD CONSTRAINT camas_pkey PRIMARY KEY (id);
ALTER TABLE ONLY epicrisis
    ADD CONSTRAINT epicrisis_hospitalizacion_id_key UNIQUE (hospitalizacion_id);
ALTER TABLE ONLY epicrisis
    ADD CONSTRAINT epicrisis_pkey PRIMARY KEY (id);
ALTER TABLE ONLY hospitalizaciones
    ADD CONSTRAINT hospitalizaciones_pkey PRIMARY KEY (id);
ALTER TABLE ONLY autorizaciones_ingreso
    ADD CONSTRAINT fk_autorizacion_hospitalizacion FOREIGN KEY (hospitalizacion_id) REFERENCES hospitalizaciones(id);
ALTER TABLE ONLY epicrisis
    ADD CONSTRAINT fk_epicrisis_hospitalizacion FOREIGN KEY (hospitalizacion_id) REFERENCES hospitalizaciones(id);
