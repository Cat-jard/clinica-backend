-- ============================================================
-- V2: Datos semilla (demo) de radiologia - estudios con series
-- estado: Pendiente | En Proceso | Borrador | Firmado
-- prioridad: Normal | Urgente
-- ============================================================

INSERT INTO estudios_radiologia (id, nro_orden, paciente_id, paciente_dni, paciente_nombre,
       paciente_apellidos, paciente_edad, paciente_sexo, nro_historia, medico_solicitante,
       especialidad_medico, modalidad, tipo_estudio, region_anatomica, prioridad, estado,
       es_critico, indicaciones, fecha_solicitud, created_at, updated_at) VALUES
('f1000000-0000-0000-0000-000000000001', 'RX-000001',
 '11111111-1111-1111-1111-111111111111', '45123789', 'Juan Carlos', 'Perez Lopez',
 41, 'M', 'HC-000001', 'Luis Torres Vega', 'Medicina General',
 'Radiografía', 'Radiografia de torax PA', 'Torax', 'Normal', 'Pendiente',
 false, 'Descartar proceso neumonico', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('f1000000-0000-0000-0000-000000000002', 'TAC-000001',
 '22222222-2222-2222-2222-222222222222', '43987654', 'Ana Maria', 'Torres Rios',
 33, 'F', 'HC-000002', 'Carmen Vega Flores', 'Cardiologia',
 'Tomografía (TAC)', 'TAC de craneo sin contraste', 'Craneo', 'Urgente', 'Pendiente',
 false, 'Cefalea intensa de inicio subito', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO series_radiologia (id, estudio_id, descripcion, num_cortes) VALUES
('f2000000-0000-0000-0000-000000000001', 'f1000000-0000-0000-0000-000000000001', 'Proyeccion PA', 1),
('f2000000-0000-0000-0000-000000000002', 'f1000000-0000-0000-0000-000000000001', 'Proyeccion lateral', 1),
('f2000000-0000-0000-0000-000000000003', 'f1000000-0000-0000-0000-000000000002', 'Cortes axiales', 120),
('f2000000-0000-0000-0000-000000000004', 'f1000000-0000-0000-0000-000000000002', 'Reconstruccion coronal', 60);
