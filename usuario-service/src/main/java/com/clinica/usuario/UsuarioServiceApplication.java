package com.clinica.usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Microservicio de Usuarios y Roles del SIHCE.
 *
 * <p>Responsabilidades:
 * <ul>
 *   <li>Autenticacion (login) y emision de tokens JWT.</li>
 *   <li>Gestion (CRUD) de usuarios del personal de la clinica.</li>
 *   <li>Control de Acceso Basado en Roles (RBAC) para los 8 modulos del sistema.</li>
 * </ul>
 */
@SpringBootApplication
public class UsuarioServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsuarioServiceApplication.class, args);
    }
}
