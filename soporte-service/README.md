# Microservicio `soporte-service` — SIHCE

Microservicio de **Soporte Técnico / TI** del Sistema de Historia Clínica
Electrónica. Gestiona los **tickets de incidencias** del personal de la clínica
(altas, seguimiento de estado, prioridad y asignación al equipo de Soporte).

## Stack
- Java 21 · Spring Boot 3.4 · Spring Cloud 2024.0.0
- Spring Web · Spring Data JPA · Spring Security · Bean Validation
- **PostgreSQL** (BD relacional) · **Flyway** (migraciones)
- Cliente de **Eureka** (descubrimiento) y **Config Server** (config centralizada)
- Valida el **JWT** emitido por `usuario-service` (clave compartida, sin BD)

## Catálogos
| Concepto | Valores |
|----------|---------|
| **Categoría** | Hardware · Software · Red / Conectividad · Accesos / Cuentas · Otro |
| **Prioridad** | Baja · Media · Alta · Crítica |
| **Estado** | Abierto · En proceso · Resuelto · Cerrado |

## Endpoints
| Método | Ruta | Acceso | Descripción |
|--------|------|--------|-------------|
| `GET`  | `/api/tickets?estado=&prioridad=&q=` | autenticado | Listado con filtros |
| `GET`  | `/api/tickets/resumen` | autenticado | Conteo total y por estado |
| `GET`  | `/api/tickets/{id}` | autenticado | Detalle |
| `POST` | `/api/tickets` | autenticado | Abrir ticket (cualquier usuario) |
| `PUT`  | `/api/tickets/{id}` | ADMIN/SOPORTE | Actualizar |
| `PATCH`| `/api/tickets/{id}/estado` | ADMIN/SOPORTE | Cambiar estado / asignar |

> Cualquier usuario autenticado puede **abrir** un ticket; la **gestión**
> (editar, cambiar estado, asignar) queda reservada a ADMIN/SOPORTE (RBAC).

## Puesta en marcha (desarrollo)
1. Crear la base de datos en PostgreSQL:
   ```sql
   CREATE DATABASE clinica_soporte;
   ```
   (sobreescribible con `DB_USER`, `DB_PASSWORD`, `DB_HOST`, `DB_PORT`, `DB_NAME`).
2. Ejecutar: `mvn spring-boot:run`. Corre en `http://localhost:8083`.
3. Flyway crea el esquema y un *seeder* carga 5 tickets de ejemplo.

### Ejemplo de creación
```bash
curl -X POST http://localhost:8083/api/tickets \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"titulo":"PC no enciende","descripcion":"El equipo de admisión no arranca","solicitanteNombre":"Rosa García","categoria":"HARDWARE","prioridad":"ALTA"}'
```

> Eureka y Config Server son **opcionales** para arrancar.
