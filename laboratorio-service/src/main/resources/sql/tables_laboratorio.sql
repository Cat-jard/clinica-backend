CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS ordenes_laboratorio (
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
    fecha_solicitud TIMESTAMP NOT NULL DEFAULT NOW(),
    prioridad VARCHAR(20) NOT NULL DEFAULT 'Normal' CHECK (prioridad IN ('Normal','Urgente')),
    estado VARCHAR(30) NOT NULL DEFAULT 'Pendiente' CHECK (estado IN ('Pendiente','Muestra Registrada','En Proceso','Resultados Pendientes','Validado','Rechazado')),
    origen_muestra VARCHAR(80),
    ayuno VARCHAR(30),
    indicaciones TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS examenes_laboratorio (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    orden_id UUID NOT NULL REFERENCES ordenes_laboratorio(id) ON DELETE CASCADE,
    nombre VARCHAR(160) NOT NULL,
    area VARCHAR(80) NOT NULL,
    analizador VARCHAR(120),
    resultado VARCHAR(80),
    unidad VARCHAR(40),
    valor_ref VARCHAR(120),
    critico BOOLEAN NOT NULL DEFAULT FALSE,
    observaciones TEXT
);

CREATE INDEX IF NOT EXISTS idx_lab_estado ON ordenes_laboratorio(estado);
CREATE INDEX IF NOT EXISTS idx_lab_prioridad ON ordenes_laboratorio(prioridad);
CREATE INDEX IF NOT EXISTS idx_lab_paciente_dni ON ordenes_laboratorio(paciente_dni);
CREATE INDEX IF NOT EXISTS idx_lab_fecha ON ordenes_laboratorio(fecha_solicitud);
CREATE INDEX IF NOT EXISTS idx_lab_examen_orden ON examenes_laboratorio(orden_id);
