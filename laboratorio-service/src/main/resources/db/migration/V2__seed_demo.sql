-- ============================================================
-- V2: Datos semilla (demo) de laboratorio - ordenes con examenes
-- estado: Pendiente | Muestra Registrada | Resultados Pendientes | Validado
-- prioridad: Normal | Urgente
-- ============================================================

INSERT INTO ordenes_laboratorio (id, nro_orden, paciente_id, paciente_dni, paciente_nombre,
       paciente_apellidos, paciente_edad, paciente_sexo, nro_historia, medico_solicitante,
       especialidad_medico, prioridad, estado, ayuno, origen_muestra, indicaciones,
       fecha_solicitud, created_at, updated_at) VALUES
('e1000000-0000-0000-0000-000000000001', 'LAB-000001',
 '11111111-1111-1111-1111-111111111111', '45123789', 'Juan Carlos', 'Perez Lopez',
 41, 'M', 'HC-000001', 'Luis Torres Vega', 'Medicina General', 'Normal', 'Pendiente',
 'Si', 'Suero', 'Control de rutina', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('e1000000-0000-0000-0000-000000000002', 'LAB-000002',
 '22222222-2222-2222-2222-222222222222', '43987654', 'Ana Maria', 'Torres Rios',
 33, 'F', 'HC-000002', 'Carmen Vega Flores', 'Cardiologia', 'Urgente', 'Pendiente',
 'No', 'Plasma', 'Descartar sindrome coronario', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO examenes_laboratorio (id, orden_id, nombre, area, analizador, unidad, valor_ref,
       resultado, critico, observaciones) VALUES
('e2000000-0000-0000-0000-000000000001', 'e1000000-0000-0000-0000-000000000001',
 'Hemograma completo', 'Hematologia', 'Sysmex XN-1000', NULL, NULL, NULL, false, NULL),
('e2000000-0000-0000-0000-000000000002', 'e1000000-0000-0000-0000-000000000001',
 'Glucosa basal', 'Quimica', 'Cobas c311', 'mg/dL', '70 - 100', NULL, false, NULL),
('e2000000-0000-0000-0000-000000000003', 'e1000000-0000-0000-0000-000000000002',
 'Perfil lipidico', 'Quimica', 'Cobas c311', 'mg/dL', 'Colesterol < 200', NULL, false, NULL),
('e2000000-0000-0000-0000-000000000004', 'e1000000-0000-0000-0000-000000000002',
 'Troponina I', 'Inmunologia', 'Architect i1000', 'ng/mL', '< 0.04', NULL, false, NULL);
