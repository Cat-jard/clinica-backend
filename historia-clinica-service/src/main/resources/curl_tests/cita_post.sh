#!/bin/bash
curl -v POST "http://localhost:8002/api/citas/crear" \
  -H "Content-Type: application/json" \
  -d '{
    "pacienteId": "b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a22",
    "medicoId": 4,
    "fechaCita": "2026-07-12",
    "horaInicio": "09:00:00",
    "horaFin": "09:30:00",
    "motivo": "Consulta médica por dolor abdominal.",
    "observaciones": "Paciente refiere dolor desde hace dos días.",
    "tipoSeguro": "ESSALUD"
  }'