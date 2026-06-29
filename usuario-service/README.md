# Microservicio `usuario-service` — SIHCE

Microservicio de **Usuarios, Roles y Autenticación** del Sistema de Historia Clínica
Electrónica. Es la pieza de seguridad del ecosistema: emite los tokens JWT y aplica
el control de acceso por rol (RBAC) que consumen los demás microservicios.

## Stack
- Java 21 · Spring Boot 3.4 · Spring Cloud 2024.0.0
- Spring Web · Spring Data JPA · Spring Security · Bean Validation
- **PostgreSQL** (BD relacional) · **Flyway** (migraciones)
- Cliente de **Eureka** (descubrimiento) y **Config Server** (config centralizada)
- JWT (jjwt 0.12)

## Roles (RBAC) — 8 módulos del sistema
| Enum | Etiqueta | Ruta frontend |
|------|----------|---------------|
| `ADMIN` | Admin | `/admin` |
| `RECEPCION` | Recepción | `/recepcionista` |
| `ENFERMERIA` | Enfermería | `/triaje` |
| `MEDICO` | Médico | `/medico` |
| `LABORATORIO` | Laboratorio | `/laboratorio` |
| `RADIOLOGO` | Radiólogo | `/radiologo` |
| `SOPORTE` | Soporte | `/soporte` |
| `PACIENTE` | Paciente | `/portal` |
| `FARMACIA` | Farmacia | `/admin` |

## Endpoints
| Método | Ruta | Acceso | Descripción |
|--------|------|--------|-------------|
| `POST` | `/api/auth/login` | público | Login → JWT + datos + ruta del módulo |
| `GET`  | `/api/auth/me` | autenticado | Usuario del token actual |
| `GET`  | `/api/usuarios?rol=&q=` | autenticado | Listado con filtros |
| `GET`  | `/api/usuarios/resumen` | autenticado | Conteo total/activos/inactivos |
| `GET`  | `/api/usuarios/roles` | autenticado | Catálogo de roles |
| `GET`  | `/api/usuarios/{id}` | autenticado | Detalle |
| `POST` | `/api/usuarios` | ADMIN/SOPORTE | Crear |
| `PUT`  | `/api/usuarios/{id}` | ADMIN/SOPORTE | Actualizar |
| `PATCH`| `/api/usuarios/{id}/estado` | ADMIN/SOPORTE | Activar/desactivar (no se elimina) |

## Puesta en marcha (desarrollo)
1. Crear la base de datos en PostgreSQL:
   ```sql
   CREATE DATABASE clinica_usuarios;
   ```
   (usuario/clave por defecto: `postgres` / `postgres`; sobreescribibles con
   `DB_USER`, `DB_PASSWORD`, `DB_HOST`, `DB_PORT`, `DB_NAME`).
2. Ejecutar: `mvn spring-boot:run` (o **Run** en IntelliJ). Corre en `http://localhost:8081`.
3. Flyway crea el esquema y un *seeder* carga 10 usuarios de ejemplo
   (contraseña por defecto **`Clinica123`**).

> Eureka y Config Server son **opcionales** para arrancar: si no están levantados,
> el servicio funciona igual en modo local.

### Ejemplo de login
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"patricia.nunez@clinica.pe","password":"Clinica123"}'
```

## Posición en la arquitectura de microservicios
```
                  ┌────────────────┐
   Frontend  ───► │  API Gateway   │ ──► usuario-service (8081)  ◄── este repo
   (Next.js)      │   (8080)       │ ──► cita-service, triaje-service, ...
                  └──────┬─────────┘
                         │ (descubre vía)
                  ┌──────▼─────────┐        ┌────────────────┐
                  │ Eureka Server  │        │ Config Server  │
                  │    (8761)      │        │    (8888)      │
                  └────────────────┘        └────────────────┘
```
Los microservicios de infraestructura (Config Server, Eureka, API Gateway) y los
de dominio se listan en el README de la carpeta raíz.
