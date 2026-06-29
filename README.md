# Clínica Backend — SIHCE (arquitectura de microservicios)

Backend del Sistema de Historia Clínica Electrónica. Arquitectura **Spring Cloud**.
Este repositorio implementa, por ahora, **únicamente el `usuario-service`**; el resto
de microservicios se listan abajo como hoja de ruta.

## ✅ Implementado
| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| **usuario-service** | 8081 | Autenticación (JWT + BCrypt), usuarios, roles, RBAC y registro de pacientes (datos demográficos/administrativos). |

> **Decisión de arquitectura:** la autenticación de **todas** las cuentas (incluido el
> paciente, rol `PACIENTE`) y el **registro de pacientes** viven en `usuario-service`.
> Por eso no existen `paciente-service` ni `portal-paciente-service`: el portal del
> paciente consume los demás servicios directamente a través del API Gateway.

## 🧱 Microservicios de infraestructura (Spring Cloud) — pendientes
Necesarios para que el ecosistema funcione como microservicios:
| Servicio | Puerto | Rol |
|----------|--------|-----|
| **config-server** | 8888 | Configuración centralizada de todos los servicios. |
| **eureka-server** (discovery) | 8761 | Registro y descubrimiento de servicios. |
| **api-gateway** | 8080 | Puerta de entrada única; enruta al frontend y aplica el JWT. |

## 🧩 Microservicios de dominio — pendientes (uno o varios por módulo/rol)
| Servicio | Módulo / Rol | Responsabilidad principal |
|----------|--------------|---------------------------|
| **cita-service** | Recepción | Agenda de citas, calendario, cola de espera. |
| **triaje-service** | Enfermería / Triaje | Signos vitales y clasificación de prioridad. |
| **historia-clinica-service** | Médico | HCE, atenciones, diagnósticos CIE-10. |
| **receta-service** (o farmacia-service) | Médico / Farmacia | Recetas electrónicas e inventario de farmacia. |
| **laboratorio-service** | Laboratorio Clínico | Muestras, resultados y control de calidad. |
| **radiologia-service** | Médico Radiólogo | Estudios DICOM e informes radiológicos. |
| **hospitalizacion-service** | Administración | Camas, ingresos y ocupación. |
| **facturacion-service** | Administración | Facturas, finanzas y aseguradoras. |
| **notificacion-service** | Transversal | Mensajería y notificaciones. |
| **auditoria-service** | Transversal / TI | Logs y trazabilidad inalterable (Ley 30024). |
| **soporte-service** | Soporte / TI | Incidencias, backups, monitoreo y BCP. |
| **reporteria-service** | Administración | Dashboard ejecutivo y reportes. |

> Mínimo para una demo funcional con el frontend actual:
> **config-server + eureka-server + api-gateway + usuario-service** (+ los de dominio
> que se vayan necesitando según el módulo a demostrar).

## Convenciones comunes
- Cada microservicio = proyecto Spring Boot independiente, cliente de Eureka y Config Server.
- Base de datos **PostgreSQL** por servicio (BD por microservicio) con migraciones **Flyway**.
- Seguridad: el `usuario-service` emite el JWT; los demás lo validan (RBAC por rol).

Ver detalles del servicio implementado en [`usuario-service/README.md`](usuario-service/README.md).
