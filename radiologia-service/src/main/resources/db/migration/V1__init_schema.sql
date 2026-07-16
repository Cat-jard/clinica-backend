-- ============================================================
-- V1: Esquema base de radiologia (PostgreSQL) - desde JPA (validate)
-- ============================================================

CREATE TABLE estudios_radiologia (
    es_critico boolean NOT NULL,
    paciente_edad integer,
    created_at timestamp(6) without time zone NOT NULL,
    fecha_estudio timestamp(6) without time zone,
    fecha_solicitud timestamp(6) without time zone NOT NULL,
    firmado_en timestamp(6) without time zone,
    updated_at timestamp(6) without time zone NOT NULL,
    paciente_sexo character varying(10),
    atencion_id uuid,
    id uuid NOT NULL,
    paciente_id uuid,
    codigo_cie10 character varying(20),
    paciente_dni character varying(20) NOT NULL,
    prioridad character varying(20) NOT NULL,
    estado character varying(30) NOT NULL,
    nro_historia character varying(30),
    nro_orden character varying(30) NOT NULL,
    dosis_radiacion character varying(80),
    modalidad character varying(80) NOT NULL,
    region_anatomica character varying(80) NOT NULL,
    especialidad_medico character varying(120) NOT NULL,
    paciente_nombre character varying(120) NOT NULL,
    medico_solicitante character varying(160) NOT NULL,
    paciente_apellidos character varying(160) NOT NULL,
    tipo_estudio character varying(160) NOT NULL,
    hallazgos text,
    impresion_diagnostica text,
    indicaciones text,
    recomendaciones text
);
CREATE TABLE series_radiologia (
    num_cortes integer NOT NULL,
    estudio_id uuid NOT NULL,
    id uuid NOT NULL,
    descripcion character varying(120) NOT NULL
);
ALTER TABLE ONLY estudios_radiologia
    ADD CONSTRAINT estudios_radiologia_nro_orden_key UNIQUE (nro_orden);
ALTER TABLE ONLY estudios_radiologia
    ADD CONSTRAINT estudios_radiologia_pkey PRIMARY KEY (id);
ALTER TABLE ONLY series_radiologia
    ADD CONSTRAINT series_radiologia_pkey PRIMARY KEY (id);
ALTER TABLE ONLY series_radiologia
    ADD CONSTRAINT fk_serie_estudio FOREIGN KEY (estudio_id) REFERENCES estudios_radiologia(id);
