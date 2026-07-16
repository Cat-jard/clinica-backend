-- ============================================================
-- V2: Datos semilla (demo) del microservicio de citas
-- Medicos: id 4 = Luis Torres (Medicina General), id 5 = Carmen Vega (Cardiologia)
--          (coinciden con el sembrador de usuario-service)
-- Pacientes (UUID fijos, reutilizados por recepcion-service):
--   11111111-... Juan Carlos Perez Lopez   (HC-000001, SIS)
--   22222222-... Ana Maria Torres Rios      (HC-000002, EsSalud)
--   33333333-... Pedro Gonzales Ramos       (HC-000003, Particular)
-- ============================================================

INSERT INTO citas (id, paciente_id, medico_id, fecha_cita, hora_inicio, hora_fin,
                   estado, motivo, observaciones, tipo_seguro, numero_historia,
                   paciente_nombre, medico_nombre, created_at, updated_at) VALUES
('a1000000-0000-0000-0000-000000000001', '11111111-1111-1111-1111-111111111111', 4,
 CURRENT_DATE, TIME '09:00', TIME '09:30', 'PROGRAMADA', 'Consulta de medicina general',
 'Control de presion arterial', 'SIS', 'HC-000001',
 'Juan Carlos Perez Lopez', 'Luis Torres Vega', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('a1000000-0000-0000-0000-000000000002', '22222222-2222-2222-2222-222222222222', 5,
 CURRENT_DATE, TIME '10:00', TIME '10:30', 'PROGRAMADA', 'Evaluacion cardiologica',
 NULL, 'EsSalud', 'HC-000002',
 'Ana Maria Torres Rios', 'Carmen Vega Flores', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('a1000000-0000-0000-0000-000000000003', '33333333-3333-3333-3333-333333333333', 4,
 CURRENT_DATE - 1, TIME '11:00', TIME '11:30', 'ATENDIDA', 'Chequeo general',
 'Paciente estable, alta el mismo dia', 'Particular', 'HC-000003',
 'Pedro Gonzales Ramos', 'Luis Torres Vega', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('a1000000-0000-0000-0000-000000000004', '11111111-1111-1111-1111-111111111111', 5,
 CURRENT_DATE - 2, TIME '08:30', TIME '09:00', 'CANCELADA', 'Control cardiologico',
 NULL, 'SIS', 'HC-000001',
 'Juan Carlos Perez Lopez', 'Carmen Vega Flores', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Registro de la cancelacion de la ultima cita
INSERT INTO cancelaciones_citas (id, cita_id, motivo, cancelado_por, fecha_cancelacion, created_at) VALUES
('b1000000-0000-0000-0000-000000000004', 'a1000000-0000-0000-0000-000000000004',
 'El paciente solicito reprogramar', 'Rosa Garcia Perez (Recepcion)',
 CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
