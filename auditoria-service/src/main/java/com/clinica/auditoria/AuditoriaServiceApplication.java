package com.clinica.auditoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Microservicio de Auditoria y Trazabilidad del SIHCE.
 *
 * <p>Responsabilidades:
 * <ul>
 *   <li>Registrar eventos del sistema (quien hizo que, cuando y desde donde).</li>
 *   <li>Conservar un historial inalterable de acciones (Ley 30024 - RENHICE).</li>
 *   <li>Consultar la bitacora con filtros para el modulo de Soporte / TI.</li>
 * </ul>
 */
@SpringBootApplication
public class AuditoriaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditoriaServiceApplication.class, args);
    }
}
