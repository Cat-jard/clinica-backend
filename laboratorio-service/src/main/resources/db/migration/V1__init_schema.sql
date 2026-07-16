-- ============================================================
-- V1: Esquema base de laboratorio (PostgreSQL) - desde JPA (validate)
-- ============================================================

CREATE TABLE examenes_laboratorio (
    critico boolean NOT NULL,
    id uuid NOT NULL,
    orden_id uuid NOT NULL,
    unidad character varying(40),
    area character varying(80) NOT NULL,
    resultado character varying(80),
    analizador character varying(120),
    valor_ref character varying(120),
    nombre character varying(160) NOT NULL,
    observaciones text
);
CREATE TABLE ordenes_laboratorio (
    paciente_edad integer,
    created_at timestamp(6) without time zone NOT NULL,
    fecha_solicitud timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    paciente_sexo character varying(10),
    atencion_id uuid,
    id uuid NOT NULL,
    paciente_id uuid,
    paciente_dni character varying(20) NOT NULL,
    prioridad character varying(20) NOT NULL,
    ayuno character varying(30),
    estado character varying(30) NOT NULL,
    nro_historia character varying(30),
    nro_orden character varying(30) NOT NULL,
    origen_muestra character varying(80),
    especialidad_medico character varying(120) NOT NULL,
    paciente_nombre character varying(120) NOT NULL,
    medico_solicitante character varying(160) NOT NULL,
    paciente_apellidos character varying(160) NOT NULL,
    indicaciones text
);
ALTER TABLE ONLY examenes_laboratorio
    ADD CONSTRAINT examenes_laboratorio_pkey PRIMARY KEY (id);
ALTER TABLE ONLY ordenes_laboratorio
    ADD CONSTRAINT ordenes_laboratorio_nro_orden_key UNIQUE (nro_orden);
ALTER TABLE ONLY ordenes_laboratorio
    ADD CONSTRAINT ordenes_laboratorio_pkey PRIMARY KEY (id);
ALTER TABLE ONLY examenes_laboratorio
    ADD CONSTRAINT fk_examen_orden FOREIGN KEY (orden_id) REFERENCES ordenes_laboratorio(id);
