-- ============================================================
-- V2: Datos semilla (demo) de hospitalizacion - camas
-- estado valido: DISPONIBLE / OCUPADO
-- ============================================================

INSERT INTO camas (id, numero, servicio, estado, paciente_id, paciente_nombre,
                   medico_nombre, diagnostico, fecha_ingreso, created_at, updated_at) VALUES
('d0000000-0000-0000-0000-000000000101', '101', 'Medicina General', 'DISPONIBLE',
 NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('d0000000-0000-0000-0000-000000000102', '102', 'Medicina General', 'OCUPADO',
 '33333333-3333-3333-3333-333333333333', 'Pedro Gonzales Ramos', 'Luis Torres Vega',
 'Observacion por dolor abdominal', CURRENT_TIMESTAMP - INTERVAL '1 day',
 CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('d0000000-0000-0000-0000-000000000201', '201', 'Cardiologia', 'DISPONIBLE',
 NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('d0000000-0000-0000-0000-000000000202', '202', 'Cardiologia', 'OCUPADO',
 '22222222-2222-2222-2222-222222222222', 'Ana Maria Torres Rios', 'Carmen Vega Flores',
 'Monitoreo post-evaluacion cardiologica', CURRENT_TIMESTAMP - INTERVAL '2 hour',
 CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('d0000000-0000-0000-0000-000000000301', '301', 'UCI', 'DISPONIBLE',
 NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('d0000000-0000-0000-0000-000000000302', '302', 'UCI', 'DISPONIBLE',
 NULL, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
