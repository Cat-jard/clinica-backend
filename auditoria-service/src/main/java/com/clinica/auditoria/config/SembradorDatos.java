package com.clinica.auditoria.config;

import com.clinica.auditoria.domain.AccionAuditoria;
import com.clinica.auditoria.domain.RegistroAuditoria;
import com.clinica.auditoria.repository.RegistroAuditoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Carga algunos eventos de ejemplo la primera vez (tabla vacia) para poder
 * probar las consultas de la bitacora sin depender de los demas servicios.
 */
@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
public class SembradorDatos implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SembradorDatos.class);

    private final RegistroAuditoriaRepository repositorio;

    public SembradorDatos(RegistroAuditoriaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void run(String... args) {
        if (repositorio.count() > 0) {
            return;
        }

        crear("patricia.nunez@clinica.pe", "Patricia Núñez Campos", "ADMIN",       AccionAuditoria.LOGIN,           "Autenticación", "Inicio de sesión correcto",                  null,           "192.168.1.10");
        crear("patricia.nunez@clinica.pe", "Patricia Núñez Campos", "ADMIN",       AccionAuditoria.CREACION,        "Usuarios",      "Alta de nuevo usuario (Médico)",             "Usuario #11",  "192.168.1.10");
        crear("elena.castro@clinica.pe",   "Elena Castro Díaz",     "SOPORTE",     AccionAuditoria.ACTUALIZACION,   "Usuarios",      "Cambio de estado a Inactivo",                "Usuario #10",  "192.168.1.22");
        crear("luis.torres@clinica.pe",    "Luis Torres Vega",      "MEDICO",      AccionAuditoria.CONSULTA,        "Historia",      "Consulta de historia clínica",               "Paciente #305", "192.168.1.40");
        crear(null,                        null,                     null,         AccionAuditoria.ACCESO_DENEGADO, "Autenticación", "Intento de acceso con credenciales inválidas", null,          "200.10.5.7");

        log.info("Sembrados {} registros de auditoria de ejemplo.", repositorio.count());
    }

    private void crear(String email, String nombre, String rol, AccionAuditoria accion,
                       String modulo, String descripcion, String entidad, String ip) {
        RegistroAuditoria r = new RegistroAuditoria();
        r.setUsuarioEmail(email);
        r.setUsuarioNombre(nombre);
        r.setRol(rol);
        r.setAccion(accion);
        r.setModulo(modulo);
        r.setDescripcion(descripcion);
        r.setEntidad(entidad);
        r.setIp(ip);
        repositorio.save(r);
    }
}
