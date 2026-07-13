#!/bin/bash
curl -X POST "http://localhost:8003/api/triaje/registrar" \
  -H "Content-Type: application/json" \
  -d '{
    "colaId": "11111111-1111-1111-1111-111111111111",
    "pacienteId": "22222222-2222-2222-2222-222222222222",
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