# Clínica Backend — SIHCE (arquitectura de microservicios)

Backend del Sistema de Historia Clínica Electrónica. Arquitectura **Spring Cloud**.
Este repositorio implementa, por ahora, los servicios marcados como ✅; el resto
de microservicios se listan abajo como hoja de ruta.

## ✅ Implementado
| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| **usuario-service** | 8081 | Autenticación (JWT + BCrypt), usuarios, roles, RBAC y registro de pacientes (datos demográficos/administrativos). |
| **auditoria-service** | 8082 | Bitácora inalterable de eventos del sistema (quién hizo qué, cuándo y desde dónde). Valida el JWT. |
| **soporte-service** | 8083 | Tickets de incidencias de TI: alta, seguimiento de estado, prioridad y asignación. Valida el JWT. |

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
| **reporteria-service** | Administración | Dashboard ejecutivo y reportes. |

> Mínimo para una demo funcional con el frontend actual:
> **config-server + eureka-server + api-gateway + usuario-service** (+ los de dominio
> que se vayan necesitando según el módulo a demostrar).

## Convenciones comunes
- Cada microservicio = proyecto Spring Boot independiente, cliente de Eureka y Config Server.
- Base de datos **PostgreSQL** por servicio (BD por microservicio) con migraciones **Flyway**.
- Seguridad: el `usuario-service` emite el JWT; los demás lo validan (RBAC por rol).

Ver detalles de cada servicio implementado en su README:
[`usuario-service`](usuario-service/README.md) ·
[`auditoria-service`](auditoria-service/README.md) ·
[`soporte-service`](soporte-service/README.md).
