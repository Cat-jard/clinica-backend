-- ============================================================
-- V1: Esquema base del microservicio de historia clinica
-- SIHCE - Atenciones, diagnosticos, recetas
-- ============================================================

CREATE TABLE atenciones_medicas (
    id uuid NOT NULL,
    paciente_id uuid NOT NULL,
    medico_id bigint NOT NULL,
    medico_nombre character varying(200),
    especialidad character varying(100),
    consultorio character varying(100),
    fecha_atencion date NOT NULL,
    hora_inicio time without time zone NOT NULL,
    hora_fin time without time zone,
    estado character varying(20) NOT NULL DEFAULT 'BORRADOR',
    -- Anamnesis (embedded)
    anamnesis_motivo_consulta text,
    anamnesis_enfermedad_actual text,
    anamnesis_antecedentes_patologicos text,
    anamnesis_antecedentes_quirurgicos text,
    anamnesis_antecedentes_alergicos text,
    anamnesis_antecedentes_familiares text,
    anamnesis_habitos text,
    anamnesis_medicacion_actual text,
    -- Examen Fisico (embedded)
    examen_general text,
    examen_cabeza_cuello text,
    examen_torax_pulmones text,
    examen_corazon text,
    examen_abdomen text,
    examen_extremidades text,
    examen_neurologico text,
    examen_otros text,
    -- Indicaciones y procedimientos
    indicaciones_generales text,
    procedimientos_realizados text,
    -- Firma
    firmada_en timestamp without time zone,
    firma_medico_id bigint,
    firma_base64 text,
    firma_hash character varying(64),
    -- Auditoria
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);

CREATE TABLE diagnosticos (
    id uuid NOT NULL,
    atencion_id uuid NOT NULL,
    codigo character varying(10) NOT NULL,
    descripcion character varying(500) NOT NULL,
    tipo character varying(20) NOT NULL,
    orden smallint NOT NULL DEFAULT 0
);

CREATE TABLE recetas (
    id uuid NOT NULL,
    atencion_id uuid NOT NULL,
    estado character varying(20) NOT NULL DEFAULT 'BORRADOR',
    firmada_en timestamp without time zone,
    created_at timestamp without time zone NOT NULL
);

CREATE TABLE items_receta (
    id uuid NOT NULL,
    receta_id uuid NOT NULL,
    medicamento character varying(200) NOT NULL,
    concentracion character varying(50) NOT NULL,
    presentacion character varying(100) NOT NULL,
    dosis character varying(100) NOT NULL,
    via character varying(20) NOT NULL,
    frecuencia character varying(100) NOT NULL,
    duracion character varying(100) NOT NULL,
    cantidad integer NOT NULL DEFAULT 1,
    indicaciones_especiales text
);

CREATE TABLE solicitudes_examenes (
    id uuid NOT NULL,
    atencion_id uuid NOT NULL,
    estado character varying(20) NOT NULL DEFAULT 'BORRADOR',
    created_at timestamp without time zone NOT NULL
);

CREATE TABLE items_examen (
    id uuid NOT NULL,
    solicitud_id uuid NOT NULL,
    tipo character varying(20) NOT NULL,
    nombre character varying(200) NOT NULL,
    origen_muestra character varying(100),
    ayuno character varying(100),
    urgente boolean NOT NULL DEFAULT false,
    indicaciones text
);

CREATE TABLE interconsultas (
    id uuid NOT NULL,
    atencion_id uuid NOT NULL,
    especialidad_destino character varying(100) NOT NULL,
    medico_destino character varying(200),
    motivo_interconsulta text NOT NULL,
    hallazgos_relevantes text,
    pregunta_especialista text,
    urgencia character varying(20) NOT NULL DEFAULT 'Normal',
    estado character varying(20) NOT NULL DEFAULT 'Enviada'
);

ALTER TABLE ONLY atenciones_medicas
    ADD CONSTRAINT atenciones_medicas_pkey PRIMARY KEY (id);
ALTER TABLE ONLY diagnosticos
    ADD CONSTRAINT diagnosticos_pkey PRIMARY KEY (id);
ALTER TABLE ONLY recetas
    ADD CONSTRAINT recetas_pkey PRIMARY KEY (id);
ALTER TABLE ONLY items_receta
    ADD CONSTRAINT items_receta_pkey PRIMARY KEY (id);
ALTER TABLE ONLY solicitudes_examenes
    ADD CONSTRAINT solicitudes_examenes_pkey PRIMARY KEY (id);
ALTER TABLE ONLY items_examen
    ADD CONSTRAINT items_examen_pkey PRIMARY KEY (id);
ALTER TABLE ONLY interconsultas
    ADD CONSTRAINT interconsultas_pkey PRIMARY KEY (id);

ALTER TABLE ONLY diagnosticos
    ADD CONSTRAINT fk_diagnostico_atencion FOREIGN KEY (atencion_id) REFERENCES atenciones_medicas(id);
ALTER TABLE ONLY recetas
    ADD CONSTRAINT fk_receta_atencion FOREIGN KEY (atencion_id) REFERENCES atenciones_medicas(id);
ALTER TABLE ONLY items_receta
    ADD CONSTRAINT fk_item_receta FOREIGN KEY (receta_id) REFERENCES recetas(id);
ALTER TABLE ONLY solicitudes_examenes
    ADD CONSTRAINT fk_solicitud_atencion FOREIGN KEY (atencion_id) REFERENCES atenciones_medicas(id);
ALTER TABLE ONLY items_examen
    ADD CONSTRAINT fk_item_examen FOREIGN KEY (solicitud_id) REFERENCES solicitudes_examenes(id);
ALTER TABLE ONLY interconsultas
    ADD CONSTRAINT fk_interconsulta_atencion FOREIGN KEY (atencion_id) REFERENCES atenciones_medicas(id);

CREATE INDEX idx_atenciones_paciente ON atenciones_medicas(paciente_id);
CREATE INDEX idx_atenciones_medico_fecha ON atenciones_medicas(medico_id, fecha_atencion);
CREATE INDEX idx_diagnosticos_atencion ON diagnosticos(atencion_id);
