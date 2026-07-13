# radiologia-service

Microservicio para Medico Radiologo / Diagnostico por Imagenes.

## Flujo principal

- `POST /api/radiologia/estudios/desde-solicitud`: crea un estudio desde una solicitud medica.
- `GET /api/radiologia/estudios`: devuelve la cola de lectura.
- `PATCH /api/radiologia/estudios/{id}/iniciar`: marca el estudio en proceso.
- `PATCH /api/radiologia/estudios/{id}/borrador`: guarda un informe borrador.
- `PATCH /api/radiologia/estudios/{id}/firmar`: firma el informe y simula el envio a historia clinica.

El visor DICOM y las series son simulados por ahora, porque las imagenes reales aun no forman parte del alcance del proyecto.
