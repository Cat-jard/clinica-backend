# Microservicio `auditoria-service` — SIHCE

Microservicio de **Auditoría y Trazabilidad** del Sistema de Historia Clínica
Electrónica. Mantiene una **bitácora inalterable** de los eventos del sistema
(quién hizo qué, cuándo y desde dónde), que consume el módulo de Soporte / TI.

## Stack
- Java 21 · Spring Boot 3.4 · Spring Cloud 2024.0.0
- Spring Web · Spring Data JPA · Spring Security · Bean Validation
- **PostgreSQL** (BD relacional) · **Flyway** (migraciones)
- Cliente de **Eureka** (descubrimiento) y **Config Server** (config centralizada)
- Valida el **JWT** emitido por `usuario-service` (clave compartida, sin BD)

## Acciones registradas
`LOGIN` · `LOGOUT` · `CREACION` · `ACTUALIZACION` · `ELIMINACION` · `CONSULTA` · `ACCESO_DENEGADO`

## Endpoints
| Método | Ruta | Acceso | Descripción |
|--------|------|--------|-------------|
| `POST` | `/api/auditoria` | servicio-a-servicio | Registrar un evento |
| `GET`  | `/api/auditoria?accion=&modulo=&q=` | ADMIN/SOPORTE | Listado con filtros |
| `GET`  | `/api/auditoria/resumen` | ADMIN/SOPORTE | Conteo total y por acción |
| `GET`  | `/api/auditoria/{id}` | ADMIN/SOPORTE | Detalle |

> El registro (`POST`) es abierto para que **cualquier microservicio** pueda
> dejar su traza; la **consulta** está reservada a ADMIN/SOPORTE (RBAC).
> Los registros son **inmutables**: no hay `PUT`, `PATCH` ni `DELETE`.

## Puesta en marcha (desarrollo)
1. Crear la base de datos en PostgreSQL:
   ```sql
   CREATE DATABASE clinica_auditoria;
   ```
   (sobreescribible con `DB_USER`, `DB_PASSWORD`, `DB_HOST`, `DB_PORT`, `DB_NAME`).
2. Ejecutar: `mvn spring-boot:run`. Corre en `http://localhost:8082`.
3. Flyway crea el esquema y un *seeder* carga 5 eventos de ejemplo.

### Ejemplo de registro
```bash
curl -X POST http://localhost:8082/api/auditoria \
  -H "Content-Type: application/json" \
  -d '{"usuarioEmail":"luis.torres@clinica.pe","usuarioNombre":"Luis Torres","rol":"MEDICO","accion":"CONSULTA","modulo":"Historia","descripcion":"Consulta de historia clínica","entidad":"Paciente #305"}'
```

> Eureka y Config Server son **opcionales** para arrancar.
