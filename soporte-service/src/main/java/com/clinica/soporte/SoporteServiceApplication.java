package com.clinica.soporte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Microservicio de Soporte Tecnico / TI del SIHCE.
 *
 * <p>Responsabilidades:
 * <ul>
 *   <li>Gestion (CRUD) de tickets de incidencias del personal de la clinica.</li>
 *   <li>Seguimiento de estado (Abierto, En proceso, Resuelto, Cerrado) y prioridad.</li>
 *   <li>Asignacion de incidencias al equipo de Soporte.</li>
 * </ul>
 */
@SpringBootApplication
public class SoporteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoporteServiceApplication.class, args);
    }
}
