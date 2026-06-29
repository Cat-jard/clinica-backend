package com.clinica.soporte.mapper;

import com.clinica.soporte.domain.Ticket;
import com.clinica.soporte.dto.TicketResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class TicketMapper {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

    public TicketResponse aResponse(Ticket t) {
        return new TicketResponse(
                String.valueOf(t.getId()),
                t.getCodigo(),
                t.getTitulo(),
                t.getDescripcion(),
                t.getSolicitanteDni(),
                t.getSolicitanteNombre(),
                t.getCategoria().getEtiqueta(),
                t.getPrioridad().getEtiqueta(),
                t.getEstado().getEtiqueta(),
                t.getAsignadoA() != null ? t.getAsignadoA() : "Sin asignar",
                t.getFechaCreacion().format(FORMATO),
                t.getFechaActualizacion() != null ? t.getFechaActualizacion().format(FORMATO) : "—"
        );
    }
}
