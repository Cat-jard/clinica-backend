CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS estudios_radiologia (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nro_orden VARCHAR(30) NOT NULL UNIQUE,
    atencion_id UUID,
    paciente_id UUID,
    paciente_nombre VARCHAR(120) NOT NULL,
    paciente_apellidos VARCHAR(160) NOT NULL,
    paciente_dni VARCHAR(20) NOT NULL,
    paciente_edad INTEGER,
    paciente_sexo VARCHAR(10),
    nro_historia VARCHAR(30),
    medico_solicitante VARCHAR(160) NOT NULL,
    especialidad_medico VARCHAR(120) NOT NULL,
    modalidad VARCHAR(80) NOT NULL,
    tipo_estudio VARCHAR(160) NOT NULL,
    region_anatomica VARCHAR(80) NOT NULL,
    fecha_solicitud TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_estudio TIMESTAMP,
    prioridad VARCHAR(20) NOT NULL DEFAULT 'Normal' CHECK (prioridad IN ('Normal','Urgente')),
    estado VARCHAR(30) NOT NULL DEFAULT 'Pendiente' CHECK (estado IN ('Pendiente','En Proceso','Borrador','Firmado','Revisado')),
    hallazgos TEXT,
    impresion_diagnostica TEXT,
    recomendaciones TEXT,
    codigo_cie10 VARCHAR(20),
    dosis_radiacion VARCHAR(80),
    firmado_en TIMESTAMP,
    es_critico BOOLEAN NOT NULL DEFAULT FALSE,
    indicaciones TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS series_radiologia (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    estudio_id UUID NOT NULL REFERENCES estudios_radiologia(id) ON DELETE CASCADE,
    descripcion VARCHAR(120) NOT NULL,
    num_cortes INTEGER NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_rad_estado ON estudios_radiologia(estado);
CREATE INDEX IF NOT EXISTS idx_rad_prioridad ON estudios_radiologia(prioridad);
CREATE INDEX IF NOT EXISTS idx_rad_paciente_dni ON estudios_radiologia(paciente_dni);
CREATE INDEX IF NOT EXISTS idx_rad_fecha ON estudios_radiologia(fecha_solicitud);
CREATE INDEX IF NOT EXISTS idx_rad_serie_estudio ON series_radiologia(estudio_id);
