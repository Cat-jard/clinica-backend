-- ============================================================
-- DATOS DE PRUEBA - Modulo Triaje (SIHCE)
-- Ejecutar en pgAdmin dentro de sihce_triaje
-- NOTA: Las tablas se crean automaticamente con ddl-auto=update
-- ============================================================

-- ==================== REGISTROS DE TRIAGE ====================

INSERT INTO registros_triaje (id, paciente_id, paciente_nombre, paciente_dni, medico_id, medico_nombre, especialidad_nombre, cita_id, ticket, hora_llegada, fecha_triaje, motivo_consulta, nivel_conciencia, dolor, prioridad, destino, justificacion, enfermera_id, timestamp, con_cita, created_at)
VALUES
('a0000000-0000-0000-0000-000000000001',
 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
 'Juan Carlos Garcia Perez',
 '12345678',
 1,
 'Dr. Luis Torres',
 'Medicina General',
 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
 'T-260701001',
 '08:30:00',
 CURRENT_DATE,
 'Dolor abdominal agudo con nausea',
 'Alerta',
 7,
 'II-NARANJA',
 'Emergencias',
 'Paciente presenta dolor abdominal severo de 7/10, requiere evaluacion inmediata por emergencias',
 'ENFERMERA001',
 NOW(),
 TRUE,
 NOW());

INSERT INTO registros_triaje (id, paciente_id, paciente_nombre, paciente_dni, cita_id, ticket, hora_llegada, fecha_triaje, motivo_consulta, nivel_conciencia, dolor, prioridad, destino, justificacion, enfermera_id, timestamp, con_cita, created_at)
VALUES
('a0000000-0000-0000-0000-000000000002',
 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22',
 'Maria Elena Lopez Martinez',
 '87654321',
 NULL,
 'T-260701002',
 '10:15:00',
 CURRENT_DATE,
 'Control de presion arterial y revision general',
 'Alerta',
 2,
 'IV-VERDE',
 'Consultorio normal',
 'Paciente acude para control rutinario de presion, dolor leve 2/10, puede esperar atencion en consultorio normal',
 'ENFERMERA002',
 NOW(),
 FALSE,
 NOW());

-- ==================== SIGNOS VITALES ====================

INSERT INTO signos_vitales (id, registro_triaje_id, pas_sistolica, pas_diastolica, frec_cardiaca, frec_respiratoria, temperatura, spo2, peso, talla, imc)
VALUES
('b0000000-0000-0000-0000-000000000001',
 'a0000000-0000-0000-0000-000000000001',
 140, 90, 95, 20, 37.5, 96, 78.5, 172, 26.5);

INSERT INTO signos_vitales (id, registro_triaje_id, pas_sistolica, pas_diastolica, frec_cardiaca, frec_respiratoria, temperatura, spo2, peso, talla, imc)
VALUES
('b0000000-0000-0000-0000-000000000002',
 'a0000000-0000-0000-0000-000000000002',
 120, 80, 72, 16, 36.6, 98, 65.0, 160, 25.4);

-- ==================== OBSERVACIONES ====================

INSERT INTO observaciones_pacientes (id, paciente_id, paciente_nombre, medico_id, medico_nombre, especialidad, horaIngreso, prioridad, motivo, estado, created_at)
VALUES
('c0000000-0000-0000-0000-000000000001',
 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
 'Juan Carlos Garcia Perez',
 1,
 'Dr. Luis Torres',
 'Medicina General',
 NOW(),
 'II-NARANJA',
 'Dolor abdominal agudo - en observacion para pruebas diagnosticas',
 'EN_OBSERVACION',
 NOW());

-- ==================== ENTRADAS KARDEX ====================

INSERT INTO entradas_kardex (id, paciente_id, paciente_nombre, cita_id, fecha_hora, pas_sistolica, pas_diastolica, frec_cardiaca, frec_respiratoria, temperatura, spo2, ingresos_hidricos, egresos_hidricos, evolucion, firmado, firmado_por, firma_hash, created_at)
VALUES
('d0000000-0000-0000-0000-000000000001',
 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
 'Juan Carlos Garcia Perez',
 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
 NOW(),
 138, 88, 92, 18, 37.2, 97, 1200, 800,
 'Paciente estable, dolor controlado con analgesicos. Se administro Paracetamol 1g EV. Pendiente de resultados de laboratorio.',
 TRUE,
 'Dr. Luis Torres',
 'a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2e3f4a5b6c7d8e9f0a1b',
 NOW());

-- ==================== MEDICAMENTOS KARDEX ====================

INSERT INTO medicamentos_kardex (id, entrada_kardex_id, nombre, dosis, via, hora)
VALUES
('e0000000-0000-0000-0000-000000000001',
 'd0000000-0000-0000-0000-000000000001',
 'Paracetamol', '1g', 'EV', '08:00');

INSERT INTO medicamentos_kardex (id, entrada_kardex_id, nombre, dosis, via, hora)
VALUES
('e0000000-0000-0000-0000-000000000002',
 'd0000000-0000-0000-0000-000000000001',
 'Omeprazol', '40mg', 'EV', '08:30');

-- ============================================================
-- VERIFICAR DATOS INSERTADOS
-- ============================================================
-- SELECT r.ticket, r.paciente_nombre, r.prioridad, r.destino,
--        s.pas_sistolica, s.spo2, s.imc
-- FROM registros_triaje r
-- LEFT JOIN signos_vitales s ON s.registro_triaje_id = r.id
-- ORDER BY r.ticket;
-- ============================================================
