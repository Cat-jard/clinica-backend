-- ============================================================
-- V2: Datos semilla (demo) de recepcion
-- Pacientes con UUID fijos, reutilizados por citas/triaje/etc.
-- ============================================================

INSERT INTO pacientes (id, tipo_documento, nro_documento, nombres, apellido_paterno,
                       apellido_materno, fecha_nacimiento, sexo, telefono, email, direccion,
                       aseguradora, nro_historia, alergias, created_at, updated_at) VALUES
('11111111-1111-1111-1111-111111111111', 'DNI', '45123789', 'Juan Carlos', 'Perez', 'Lopez',
 DATE '1985-03-12', 'M', '987654321', 'juan.perez@correo.pe', 'Av. Los Olivos 123, Lima',
 'SIS', 'HC-000001', 'Penicilina', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('22222222-2222-2222-2222-222222222222', 'DNI', '43987654', 'Ana Maria', 'Torres', 'Rios',
 DATE '1992-07-25', 'F', '986111222', 'ana.torres@correo.pe', 'Jr. Union 456, Lima',
 'EsSalud', 'HC-000002', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('33333333-3333-3333-3333-333333333333', 'DNI', '42765432', 'Pedro', 'Gonzales', 'Ramos',
 DATE '1978-11-03', 'M', '985333444', NULL, 'Calle Las Flores 789, Callao',
 'Particular', 'HC-000003', 'Sulfas, Mariscos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Cola de hoy (pacientes en espera)
INSERT INTO cola (id, paciente_id, cita_id, ticket, estado, fecha, hora_llegada,
                  paciente_dni, paciente_nombre, especialidad, medico_nombre, motivo,
                  created_at, updated_at) VALUES
('c0000000-0000-0000-0000-000000000001', '11111111-1111-1111-1111-111111111111',
 'a1000000-0000-0000-0000-000000000001', 'A-001', 'EN_ESPERA', CURRENT_DATE, TIME '08:45',
 '45123789', 'Juan Carlos Perez Lopez', 'Medicina General', 'Luis Torres Vega',
 'Control de presion arterial', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('c0000000-0000-0000-0000-000000000002', '22222222-2222-2222-2222-222222222222',
 'a1000000-0000-0000-0000-000000000002', 'A-002', 'EN_ESPERA', CURRENT_DATE, TIME '09:20',
 '43987654', 'Ana Maria Torres Rios', 'Cardiologia', 'Carmen Vega Flores',
 'Evaluacion cardiologica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
