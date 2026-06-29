package com.clinica.soporte.config;

import com.clinica.soporte.domain.CategoriaTicket;
import com.clinica.soporte.domain.EstadoTicket;
import com.clinica.soporte.domain.PrioridadTicket;
import com.clinica.soporte.domain.Ticket;
import com.clinica.soporte.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Carga algunos tickets de ejemplo la primera vez (tabla vacia) para poder
 * probar el listado y los filtros del modulo de Soporte.
 */
@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
public class SembradorDatos implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SembradorDatos.class);

    private final TicketRepository repositorio;

    public SembradorDatos(TicketRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void run(String... args) {
        if (repositorio.count() > 0) {
            return;
        }

        crear("La impresora de recepción no imprime", "El equipo de recepción no logra imprimir las hojas de admisión.",
                "40128734", "Rosa García Pérez", CategoriaTicket.HARDWARE, PrioridadTicket.ALTA, EstadoTicket.ABIERTO, null);
        crear("No puedo acceder al módulo de Historia Clínica", "Al iniciar sesión aparece 'Acceso denegado' al abrir Historia.",
                "42876541", "Luis Torres Vega", CategoriaTicket.ACCESO, PrioridadTicket.CRITICA, EstadoTicket.EN_PROCESO, "Elena Castro Díaz");
        crear("Lentitud en el sistema de laboratorio", "Los resultados tardan mucho en cargar desde media mañana.",
                "45678123", "María Torres Huamán", CategoriaTicket.SOFTWARE, PrioridadTicket.MEDIA, EstadoTicket.ABIERTO, null);
        crear("Solicitud de nuevo correo institucional", "El nuevo médico necesita una cuenta de correo de la clínica.",
                "43219087", "Carmen Vega Flores", CategoriaTicket.ACCESO, PrioridadTicket.BAJA, EstadoTicket.RESUELTO, "Elena Castro Díaz");
        crear("Sin conexión a internet en consultorio 3", "El punto de red del consultorio 3 no tiene enlace.",
                null, "Soporte en sitio", CategoriaTicket.RED, PrioridadTicket.ALTA, EstadoTicket.CERRADO, "Elena Castro Díaz");

        log.info("Sembrados {} tickets de soporte de ejemplo.", repositorio.count());
    }

    private void crear(String titulo, String descripcion, String dni, String solicitante,
                       CategoriaTicket categoria, PrioridadTicket prioridad, EstadoTicket estado, String asignadoA) {
        Ticket t = new Ticket();
        t.setCodigo(String.format("TCK-%04d", repositorio.count() + 1));
        t.setTitulo(titulo);
        t.setDescripcion(descripcion);
        t.setSolicitanteDni(dni);
        t.setSolicitanteNombre(solicitante);
        t.setCategoria(categoria);
        t.setPrioridad(prioridad);
        t.setEstado(estado);
        t.setAsignadoA(asignadoA);
        repositorio.save(t);
    }
}
