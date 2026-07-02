CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS registros_triaje (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    paciente_id UUID NOT NULL,
    paciente_nombre VARCHAR(300) NOT NULL,
    paciente_dni VARCHAR(20) NOT NULL,
    medico_id UUID,
    medico_nombre VARCHAR(200),
    especialidad_id UUID,
    especialidad_nombre VARCHAR(100),
    cita_id UUID,
    ticket VARCHAR(10) NOT NULL,
    hora_llegada TIME NOT NULL,
    fecha_triaje DATE NOT NULL,
    motivo_consulta TEXT NOT NULL,
    nivel_conciencia VARCHAR(20) NOT NULL CHECK (nivel_conciencia IN ('Alerta','Verbal','Dolor','Inconsciente')),
    dolor INTEGER NOT NULL CHECK (dolor BETWEEN 0 AND 10),
    prioridad VARCHAR(10) NOT NULL CHECK (prioridad IN ('I-ROJO','II-NARANJA','III-AMARILLO','IV-VERDE','V-AZUL')),
    destino VARCHAR(40) NOT NULL,
    justificacion TEXT NOT NULL,
    enfermera_id VARCHAR(100) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT NOW(),
    con_cita BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX IF NOT EXISTS idx_triaje_paciente ON registros_triaje(paciente_id);
CREATE INDEX IF NOT EXISTS idx_triaje_fecha ON registros_triaje(fecha_triaje);
CREATE INDEX IF NOT EXISTS idx_triaje_cita ON registros_triaje(cita_id) WHERE cita_id IS NOT NULL;

CREATE TABLE IF NOT EXISTS signos_vitales (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    registro_triaje_id UUID NOT NULL UNIQUE REFERENCES registros_triaje(id),
    pas_sistolica INTEGER NOT NULL,
    pas_diastolica INTEGER NOT NULL,
    frec_cardiaca INTEGER NOT NULL,
    frec_respiratoria INTEGER NOT NULL,
    temperatura NUMERIC(4,1) NOT NULL,
    spo2 INTEGER NOT NULL CHECK (spo2 BETWEEN 0 AND 100),
    peso NUMERIC(5,1) NOT NULL,
    talla INTEGER NOT NULL,
    imc NUMERIC(4,1)
);

CREATE TABLE IF NOT EXISTS observaciones_pacientes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    paciente_id UUID NOT NULL,
    paciente_nombre VARCHAR(300) NOT NULL,
    medico_id UUID,
    medico_nombre VARCHAR(200),
    especialidad VARCHAR(100),
    horaIngreso TIMESTAMP NOT NULL DEFAULT NOW(),
    prioridad VARCHAR(10) NOT NULL,
    motivo TEXT NOT NULL,
    estado VARCHAR(30) NOT NULL DEFAULT 'EN_OBSERVACION' CHECK (estado IN ('EN_OBSERVACION','ALTA_DOMICILIARIA','HOSPITALIZACION','TRASLADO')),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX IF NOT EXISTS idx_obs_paciente ON observaciones_pacientes(paciente_id);

CREATE TABLE IF NOT EXISTS entradas_kardex (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    paciente_id UUID NOT NULL,
    paciente_nombre VARCHAR(300) NOT NULL,
    cita_id UUID,
    fecha_hora TIMESTAMP NOT NULL DEFAULT NOW(),
    pas_sistolica INTEGER,
    pas_diastolica INTEGER,
    frec_cardiaca INTEGER,
    frec_respiratoria INTEGER,
    temperatura NUMERIC(4,1),
    spo2 INTEGER,
    ingresos_hidricos INTEGER NOT NULL DEFAULT 0,
    egresos_hidricos INTEGER NOT NULL DEFAULT 0,
    evolucion TEXT NOT NULL,
    firmado BOOLEAN NOT NULL DEFAULT FALSE,
    firmado_por VARCHAR(100),
    firma_base64 TEXT,
    firma_hash VARCHAR(64),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX IF NOT EXISTS idx_kardex_paciente ON entradas_kardex(paciente_id);

CREATE TABLE IF NOT EXISTS medicamentos_kardex (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    entrada_kardex_id UUID NOT NULL REFERENCES entradas_kardex(id) ON DELETE CASCADE,
    nombre VARCHAR(200) NOT NULL,
    dosis VARCHAR(50) NOT NULL,
    via VARCHAR(50) NOT NULL,
    hora VARCHAR(10) NOT NULL
);
