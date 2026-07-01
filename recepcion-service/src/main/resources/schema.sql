CREATE TABLE IF NOT EXISTS pacientes (
    id UUID PRIMARY KEY,
    tipo_documento VARCHAR(20) NOT NULL,
    nro_documento VARCHAR(20) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100) NOT NULL,
    nombres VARCHAR(150) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    sexo VARCHAR(10) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    email VARCHAR(200),
    direccion VARCHAR(300),
    aseguradora VARCHAR(20) NOT NULL,
    nro_historia VARCHAR(20) NOT NULL,
    alergias TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_pacientes_nro_documento UNIQUE (nro_documento),
    CONSTRAINT uk_pacientes_nro_historia UNIQUE (nro_historia)
);

CREATE TABLE IF NOT EXISTS consentimientos_informados (
    id UUID PRIMARY KEY,
    paciente_id UUID NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    texto_legal TEXT NOT NULL,
    texto_legal_hash VARCHAR(64) NOT NULL,
    version_texto VARCHAR(10) NOT NULL,
    firma_base64 TEXT,
    firma_hash VARCHAR(64),
    aceptado BOOLEAN DEFAULT FALSE,
    fecha_exposicion TIMESTAMP NOT NULL,
    fecha_firma TIMESTAMP,
    ip_origen VARCHAR(45) NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    metadata TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_consentimiento_paciente FOREIGN KEY (paciente_id) REFERENCES pacientes(id) ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_pacientes_nro_documento ON pacientes(nro_documento);
CREATE INDEX IF NOT EXISTS idx_pacientes_nro_historia ON pacientes(nro_historia);
CREATE INDEX IF NOT EXISTS idx_pacientes_apellidos ON pacientes(apellido_paterno, apellido_materno);
CREATE INDEX IF NOT EXISTS idx_consentimientos_paciente_id ON consentimientos_informados(paciente_id);
