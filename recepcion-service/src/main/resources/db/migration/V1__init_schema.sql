-- ============================================================
-- V1: Esquema base del microservicio de recepcion (PostgreSQL)
-- SIHCE - generado desde el modelo JPA (coincide con ddl-auto: validate)
-- ============================================================

-- Datos demograficos/administrativos del paciente
CREATE TABLE pacientes (
    id                uuid                          NOT NULL,
    tipo_documento    character varying(20)         NOT NULL,
    nro_documento     character varying(20)         NOT NULL,
    nombres           character varying(150)        NOT NULL,
    apellido_paterno  character varying(100)        NOT NULL,
    apellido_materno  character varying(100)        NOT NULL,
    fecha_nacimiento  date                          NOT NULL,
    sexo              character varying(10)         NOT NULL,
    telefono          character varying(9)          NOT NULL,
    email             character varying(200),
    direccion         character varying(300),
    aseguradora       character varying(20)         NOT NULL,
    nro_historia      character varying(20)         NOT NULL,
    alergias          text,
    created_at        timestamp(6) without time zone NOT NULL,
    updated_at        timestamp(6) without time zone NOT NULL,
    CONSTRAINT pacientes_pkey PRIMARY KEY (id),
    CONSTRAINT pacientes_nro_documento_key UNIQUE (nro_documento),
    CONSTRAINT pacientes_nro_historia_key  UNIQUE (nro_historia)
);

-- Cola de espera / recepcion
CREATE TABLE cola (
    id               uuid                          NOT NULL,
    paciente_id      uuid                          NOT NULL,
    cita_id          uuid,
    ticket           character varying(20)         NOT NULL,
    estado           character varying(20)         NOT NULL,
    fecha            date                          NOT NULL,
    hora_llegada     time(0) without time zone     NOT NULL,
    paciente_dni     character varying(20)         NOT NULL,
    paciente_nombre  character varying(200)        NOT NULL,
    especialidad     character varying(100),
    medico_nombre    character varying(200),
    motivo           text,
    created_at       timestamp(6) without time zone NOT NULL,
    updated_at       timestamp(6) without time zone NOT NULL,
    CONSTRAINT cola_pkey PRIMARY KEY (id),
    CONSTRAINT fk_cola_paciente FOREIGN KEY (paciente_id) REFERENCES pacientes (id)
);

-- Consentimientos informados (Ley 29733)
CREATE TABLE consentimientos_informados (
    id                uuid                          NOT NULL,
    paciente_id       uuid                          NOT NULL,
    tipo              character varying(50)         NOT NULL,
    version_texto     character varying(10)         NOT NULL,
    texto_legal       text                          NOT NULL,
    texto_legal_hash  character varying(64)         NOT NULL,
    aceptado          boolean                       NOT NULL,
    firma_base64      text,
    firma_hash        character varying(64),
    fecha_exposicion  timestamp(6) without time zone NOT NULL,
    fecha_firma       timestamp(6) without time zone,
    ip_origen         character varying(45)         NOT NULL,
    user_id           character varying(100)        NOT NULL,
    metadata          text,
    created_at        timestamp(6) without time zone NOT NULL,
    CONSTRAINT consentimientos_informados_pkey PRIMARY KEY (id),
    CONSTRAINT fk_consentimiento_paciente FOREIGN KEY (paciente_id) REFERENCES pacientes (id)
);

CREATE INDEX idx_cola_estado ON cola (estado);
CREATE INDEX idx_cola_fecha  ON cola (fecha);
