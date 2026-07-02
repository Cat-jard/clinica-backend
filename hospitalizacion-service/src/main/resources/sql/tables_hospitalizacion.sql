CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS hospitalizaciones (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    cama_id UUID NOT NULL,
    cama_numero VARCHAR(10) NOT NULL,
    servicio VARCHAR(50) NOT NULL,
    paciente_id UUID NOT NULL,
    paciente_nombre VARCHAR(300) NOT NULL,
    paciente_dni VARCHAR(20) NOT NULL,
    medico_id UUID NOT NULL,
    medico_nombre VARCHAR(200) NOT NULL,
    motivo_ingreso TEXT NOT NULL,
    diagnostico_ingreso VARCHAR(200) NOT NULL,
    diagnostico_alta VARCHAR(200),
    fecha_ingreso TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_alta TIMESTAMP,
    tipo_alta VARCHAR(30) CHECK (tipo_alta IN ('ALTA_DOMICILIARIA','TRASLADO','FALLECIMIENTO')),
    estado VARCHAR(20) NOT NULL DEFAULT 'HOSPITALIZADO' CHECK (estado IN ('HOSPITALIZADO','ALTA','TRASLADO')),
    observaciones TEXT,
    user_id_ingreso VARCHAR(100) NOT NULL,
    user_id_alta VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX IF NOT EXISTS idx_hosp_paciente ON hospitalizaciones(paciente_id);
CREATE INDEX IF NOT EXISTS idx_hosp_cama ON hospitalizaciones(cama_id);
CREATE INDEX IF NOT EXISTS idx_hosp_estado ON hospitalizaciones(estado);

CREATE TABLE IF NOT EXISTS autorizaciones_ingreso (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    hospitalizacion_id UUID NOT NULL UNIQUE REFERENCES hospitalizaciones(id),
    representante_nombre VARCHAR(300),
    representante_dni VARCHAR(20),
    texto_legal TEXT NOT NULL,
    firma_base64 TEXT,
    firmado BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS epicrisis (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    hospitalizacion_id UUID NOT NULL UNIQUE REFERENCES hospitalizaciones(id),
    fecha_ingreso TIMESTAMP NOT NULL,
    fecha_alta TIMESTAMP NOT NULL,
    motivo_ingreso TEXT NOT NULL,
    diagnostico_ingreso VARCHAR(200) NOT NULL,
    diagnostico_final VARCHAR(200) NOT NULL,
    evolucion TEXT NOT NULL,
    procedimientos TEXT,
    complicaciones TEXT,
    tratamiento TEXT NOT NULL,
    recomendaciones TEXT NOT NULL,
    proxima_cita DATE,
    firmado BOOLEAN NOT NULL DEFAULT FALSE,
    firma_base64 TEXT,
    medico_id VARCHAR(100) NOT NULL,
    medico_nombre VARCHAR(200) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
