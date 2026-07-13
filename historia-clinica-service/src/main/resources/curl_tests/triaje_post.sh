#!/bin/bash
curl -X POST "http://localhost:8003/api/triaje/registrar" \
  -H "Content-Type: application/json" \
  -d '{
    "colaId": "11111111-1111-1111-1111-111111111111",
    "pacienteId": "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
    "citaId": "33333333-3333-3333-3333-333333333333",
    "enfermeraId": "ENF-001",
    "signos": {
      "pasSistolica": 120,
      "pasDiastolica": 80,
      "frecCardiaca": 72,
      "frecRespiratoria": 16,
      "temperatura": 36.8,
      "spo2": 98,
      "peso": 74.5,
      "talla": 175
    },
    "motivoConsulta": "Dolor abdominal de inicio súbito.",
    "nivelConciencia": "Alerta",
    "dolor": 7,
    "prioridad": "III-AMARILLO",
    "justificacion": "Paciente presenta dolor intenso con inicio reciente."
  }'