# laboratorio-service

Microservicio para la cola de Laboratorio Clinico y para recibir la solicitud firmada de examenes desde el modulo Medico.

## Flujo principal

- `POST /api/solicitudes-examenes`: recibe la orden firmada del medico.
- Los items `Laboratorio` se guardan como ordenes en la cola de laboratorio.
- Los items `Imagenes` se envian a `radiologia-service` mediante Feign.
- `GET /api/laboratorio/ordenes`: devuelve la cola de laboratorio.
- `PATCH /api/laboratorio/ordenes/{id}/muestra`: registra muestra.
- `PATCH /api/laboratorio/ordenes/{id}/resultados`: deja resultados por validar.
- `PATCH /api/laboratorio/ordenes/{id}/validar`: valida y firma resultados.

Los datos de paciente y medico se reciben en el request; no se consultan todavia otros servicios para no invadir el trabajo de los demas modulos.
