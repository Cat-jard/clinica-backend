-- ============================================================
-- V1: Esquema base del microservicio de triaje (PostgreSQL)
-- SIHCE - generado desde el modelo JPA (coincide con ddl-auto: validate)
-- ============================================================

CREATE TABLE entradas_kardex (
    egresos_hidricos integer NOT NULL,
    firmado boolean NOT NULL,
    frec_cardiaca integer,
    frec_respiratoria integer,
    ingresos_hidricos integer NOT NULL,
    pas_diastolica integer,
    pas_sistolica integer,
    spo2 integer,
    temperatura numeric(4,1),
    created_at timestamp(6) without time zone NOT NULL,
    fecha_hora timestamp(6) without time zone NOT NULL,
    cita_id uuid,
    id uuid NOT NULL,
    paciente_id uuid NOT NULL,
    firma_hash character varying(64),
    firmado_por character varying(100),
    paciente_nombre character varying(300) NOT NULL,
    evolucion text NOT NULL,
    firma_base64 text
);
CREATE TABLE medicamentos_kardex (
    hora character varying(10) NOT NULL,
    entrada_kardex_id uuid NOT NULL,
    id uuid NOT NULL,
    dosis character varying(50) NOT NULL,
    via character varying(50) NOT NULL,
    nombre character varying(200) NOT NULL
);
CREATE TABLE observaciones_pacientes (
    created_at timestamp(6) without time zone NOT NULL,
    hora_ingreso timestamp(6) without time zone NOT NULL,
    medico_id bigint,
    prioridad character varying(10) NOT NULL,
    id uuid NOT NULL,
    paciente_id uuid NOT NULL,
    estado character varying(30) NOT NULL,
    especialidad character varying(100),
    medico_nombre character varying(200),
    paciente_nombre character varying(300) NOT NULL,
    motivo text NOT NULL
);
CREATE TABLE registros_triaje (
    con_cita boolean NOT NULL,
    dolor integer NOT NULL,
    fecha_triaje date NOT NULL,
    hora_llegada time(0) without time zone NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    medico_id bigint,
    "timestamp" timestamp(6) without time zone NOT NULL,
    cita_id uuid,
    especialidad_id uuid,
    id uuid NOT NULL,
    paciente_id uuid NOT NULL,
    nivel_conciencia character varying(20) NOT NULL,
    paciente_dni character varying(20) NOT NULL,
    prioridad character varying(20) NOT NULL,
    ticket character varying(20) NOT NULL,
    destino character varying(40) NOT NULL,
    enfermera_id character varying(100) NOT NULL,
    especialidad_nombre character varying(100),
    medico_nombre character varying(200),
    paciente_nombre character varying(300) NOT NULL,
    justificacion text NOT NULL,
    motivo_consulta text NOT NULL
);
CREATE TABLE signos_vitales (
    frec_cardiaca integer NOT NULL,
    frec_respiratoria integer NOT NULL,
    imc numeric(4,1),
    pas_diastolica integer NOT NULL,
    pas_sistolica integer NOT NULL,
    peso numeric(5,1) NOT NULL,
    spo2 integer NOT NULL,
    talla integer NOT NULL,
    temperatura numeric(4,1) NOT NULL,
    id uuid NOT NULL,
    registro_triaje_id uuid NOT NULL
);
ALTER TABLE ONLY entradas_kardex
    ADD CONSTRAINT entradas_kardex_pkey PRIMARY KEY (id);
ALTER TABLE ONLY medicamentos_kardex
    ADD CONSTRAINT medicamentos_kardex_pkey PRIMARY KEY (id);
ALTER TABLE ONLY observaciones_pacientes
    ADD CONSTRAINT observaciones_pacientes_pkey PRIMARY KEY (id);
ALTER TABLE ONLY registros_triaje
    ADD CONSTRAINT registros_triaje_pkey PRIMARY KEY (id);
ALTER TABLE ONLY signos_vitales
    ADD CONSTRAINT signos_vitales_pkey PRIMARY KEY (id);
ALTER TABLE ONLY signos_vitales
    ADD CONSTRAINT signos_vitales_registro_triaje_id_key UNIQUE (registro_triaje_id);
ALTER TABLE ONLY medicamentos_kardex
    ADD CONSTRAINT fk_medicamento_entrada_kardex FOREIGN KEY (entrada_kardex_id) REFERENCES entradas_kardex(id);
ALTER TABLE ONLY signos_vitales
    ADD CONSTRAINT fk_signos_registro_triaje FOREIGN KEY (registro_triaje_id) REFERENCES registros_triaje(id);
