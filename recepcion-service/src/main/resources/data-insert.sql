-- ============================================================
-- DATOS DE PRUEBA - Módulo Recepción (SIHCE)
-- Ejecutar en pgAdmin (Query Tool) dentro de recepcion_db
-- ============================================================
-- NOTA: Las tablas se crean automáticamente al iniciar el
-- servicio con el perfil 'prod' (spring.jpa.hibernate.ddl-auto=update).
-- Si prefieres crearlas manualmente, ejecuta primero init-postgresql.sql
-- ============================================================

-- ==================== PACIENTES ====================

INSERT INTO pacientes (id, tipo_documento, nro_documento, apellido_paterno, apellido_materno, nombres, fecha_nacimiento, sexo, telefono, email, direccion, aseguradora, nro_historia, alergias, created_at, updated_at)
VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'DNI', '12345678', 'García', 'Pérez', 'Juan Carlos', '1990-05-15', 'Masculino', '987654321', 'juan.garcia@email.com', 'Av. Principal 123, Lima', 'SIS', 'HC-20260000001', NULL, NOW(), NOW());

INSERT INTO pacientes (id, tipo_documento, nro_documento, apellido_paterno, apellido_materno, nombres, fecha_nacimiento, sexo, telefono, email, direccion, aseguradora, nro_historia, alergias, created_at, updated_at)
VALUES
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 'DNI', '87654321', 'López', 'Martínez', 'María Elena', '1985-08-22', 'Femenino', '912345678', 'maria.lopez@email.com', 'Jr. Secundaria 456, Arequipa', 'EsSalud', 'HC-20260000002', 'Penicilina', NOW(), NOW());

-- ==================== CONSENTIMIENTOS INFORMADOS ====================

-- Consentimiento 1: Paciente Juan Carlos - FIRMADO (aceptado=true, con firma)
INSERT INTO consentimientos_informados (id, paciente_id, tipo, texto_legal, texto_legal_hash, version_texto, firma_base64, firma_hash, aceptado, fecha_exposicion, fecha_firma, ip_origen, user_id, metadata, created_at)
VALUES
('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a33',
 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
 'TRATAMIENTO_DATOS',
 'Autorizo el tratamiento de mis datos personales conforme a la Ley N°29733 de Proteccion de Datos Personales.',
 'd1d5cfd8e9dd3023db1a51759ec7f0e2329a000469071c3022384280aa1c5cc0',
 '1.0',
 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==',
 '53f01d18fd6a95d0d6c5890b5f790ef5feda33980e6165ae2d58ebe46b35d363',
 TRUE,
 NOW(),
 NOW(),
 '192.168.1.100',
 'recepcionista01',
 '{"userAgent":"Postman","dispositivo":"Windows 11","browser":"Chrome"}',
 NOW());

-- Consentimiento 2: Paciente María Elena - PENDIENTE (aceptado=false, sin firma)
INSERT INTO consentimientos_informados (id, paciente_id, tipo, texto_legal, texto_legal_hash, version_texto, firma_base64, firma_hash, aceptado, fecha_exposicion, fecha_firma, ip_origen, user_id, metadata, created_at)
VALUES
('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a44',
 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22',
 'PROCEDIMIENTO',
 'Autorizo el procedimiento medico indicado por el Dr. Perez.',
 'bf5eaeb3127c7d115c44fb2bd8175e88d212fbdd41400b2fc6ff1458fd0745d5',
 '1.2',
 NULL,
 NULL,
 FALSE,
 NOW(),
 NULL,
 '192.168.1.101',
 'recepcionista02',
 '{"userAgent":"pgAdmin","dispositivo":"Windows 11"}',
 NOW());

-- ============================================================
-- VERIFICAR DATOS INSERTADOS
-- ============================================================
-- SELECT p.nro_historia, p.apellido_paterno, p.nombres, p.aseguradora,
--        ci.tipo, ci.aceptado, ci.fecha_firma
-- FROM pacientes p
-- LEFT JOIN consentimientos_informados ci ON ci.paciente_id = p.id
-- ORDER BY p.nro_historia;
-- ============================================================
