package com.clinica.usuario.config;

import com.clinica.usuario.domain.EstadoUsuario;
import com.clinica.usuario.domain.Rol;
import com.clinica.usuario.domain.Usuario;
import com.clinica.usuario.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Carga usuarios de ejemplo (uno por rol) la primera vez, para poder probar el
 * login. Solo se ejecuta si la tabla esta vacia. Todos comparten la contrasena
 * por defecto definida en {@code app.seed.default-password} (por defecto "Clinica123").
 */
@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
public class SembradorDatos implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SembradorDatos.class);

    private final UsuarioRepository repositorio;
    private final PasswordEncoder passwordEncoder;
    private final String passwordPorDefecto;

    public SembradorDatos(UsuarioRepository repositorio,
                          PasswordEncoder passwordEncoder,
                          @org.springframework.beans.factory.annotation.Value("${app.seed.default-password}") String passwordPorDefecto) {
        this.repositorio = repositorio;
        this.passwordEncoder = passwordEncoder;
        this.passwordPorDefecto = passwordPorDefecto;
    }

    @Override
    public void run(String... args) {
        if (repositorio.count() > 0) {
            return;
        }

        crear("47812345", "Patricia", "Núñez Campos",  "patricia.nunez@clinica.pe",  "986444555", Rol.ADMIN,       null,                EstadoUsuario.ACTIVO);
        crear("40128734", "Rosa",     "García Pérez",   "rosa.garcia@clinica.pe",     "987654321", Rol.RECEPCION,   null,                EstadoUsuario.ACTIVO);
        crear("41298765", "Lucía",    "Ramírez Soto",   "lucia.ramirez@clinica.pe",   "987111222", Rol.ENFERMERIA,  null,                EstadoUsuario.ACTIVO);
        crear("42876541", "Luis",     "Torres Vega",    "luis.torres@clinica.pe",     "987333444", Rol.MEDICO,      "Medicina General",  EstadoUsuario.ACTIVO);
        crear("43219087", "Carmen",   "Vega Flores",    "carmen.vega@clinica.pe",     "987555666", Rol.MEDICO,      "Cardiología",       EstadoUsuario.ACTIVO);
        crear("45678123", "María",    "Torres Huamán",  "maria.torres@clinica.pe",    "987999000", Rol.LABORATORIO, null,                EstadoUsuario.ACTIVO);
        crear("44567812", "Ricardo",  "Mendoza Quispe", "ricardo.mendoza@clinica.pe", "987777888", Rol.RADIOLOGO,   null,                EstadoUsuario.ACTIVO);
        crear("46781234", "Jorge",    "Salas Ríos",     "jorge.salas@clinica.pe",     "986111333", Rol.FARMACIA,    null,                EstadoUsuario.ACTIVO);
        crear("49234567", "Elena",    "Castro Díaz",    "elena.castro@clinica.pe",    "986888999", Rol.SOPORTE,     null,                EstadoUsuario.ACTIVO);
        crear("48123456", "Daniel",   "Rojas Medina",   "daniel.rojas@clinica.pe",    "986666777", Rol.MEDICO,      "Pediatría",         EstadoUsuario.INACTIVO);

        log.info("Sembrados {} usuarios de ejemplo. Contrasena por defecto: '{}'",
                repositorio.count(), passwordPorDefecto);
    }

    private void crear(String dni, String nombre, String apellidos, String email,
                       String telefono, Rol rol, String especialidad, EstadoUsuario estado) {
        Usuario u = new Usuario();
        u.setDni(dni);
        u.setNombre(nombre);
        u.setApellidos(apellidos);
        u.setEmail(email);
        u.setTelefono(telefono);
        u.setRol(rol);
        u.setEspecialidad(especialidad);
        u.setEstado(estado);
        u.setPasswordHash(passwordEncoder.encode(passwordPorDefecto));
        repositorio.save(u);
    }
}
