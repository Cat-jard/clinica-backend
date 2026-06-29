package com.clinica.soporte.service;

import com.clinica.soporte.domain.CategoriaTicket;
import com.clinica.soporte.domain.EstadoTicket;
import com.clinica.soporte.domain.PrioridadTicket;
import com.clinica.soporte.domain.Ticket;
import com.clinica.soporte.dto.ActualizarTicketRequest;
import com.clinica.soporte.dto.CambiarEstadoRequest;
import com.clinica.soporte.dto.CrearTicketRequest;
import com.clinica.soporte.dto.TicketResponse;
import com.clinica.soporte.exception.RecursoNoEncontradoException;
import com.clinica.soporte.mapper.TicketMapper;
import com.clinica.soporte.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Logica de negocio para la gestion de tickets de soporte. */
@Service
@Transactional
public class SoporteService {

    private final TicketRepository repositorio;
    private final TicketMapper mapper;

    public SoporteService(TicketRepository repositorio, TicketMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> listar(String estado, String prioridad, String texto) {
        EstadoTicket estadoFiltro = (estado == null || estado.isBlank() || "Todos".equalsIgnoreCase(estado))
                ? null : EstadoTicket.desde(estado);
        PrioridadTicket prioridadFiltro = (prioridad == null || prioridad.isBlank() || "Todas".equalsIgnoreCase(prioridad))
                ? null : PrioridadTicket.desde(prioridad);
        String textoFiltro = (texto == null || texto.isBlank()) ? null : texto.trim();
        return repositorio.buscar(estadoFiltro, prioridadFiltro, textoFiltro).stream().map(mapper::aResponse).toList();
    }

    @Transactional(readOnly = true)
    public TicketResponse obtener(Long id) {
        return mapper.aResponse(buscarEntidad(id));
    }

    /** Conteo total y por estado para el tablero de Soporte. */
    @Transactional(readOnly = true)
    public Map<String, Long> resumen() {
        Map<String, Long> resumen = new LinkedHashMap<>();
        resumen.put("total", repositorio.count());
        for (EstadoTicket e : EstadoTicket.values()) {
            resumen.put(e.name().toLowerCase(), repositorio.countByEstado(e));
        }
        return resumen;
    }

    public TicketResponse crear(CrearTicketRequest req) {
        Ticket t = new Ticket();
        t.setCodigo(generarCodigo());
        t.setTitulo(req.titulo().trim());
        t.setDescripcion(req.descripcion().trim());
        t.setSolicitanteDni(req.solicitanteDni());
        t.setSolicitanteNombre(req.solicitanteNombre().trim());
        t.setCategoria(CategoriaTicket.desde(req.categoria()));
        t.setPrioridad(PrioridadTicket.desde(req.prioridad()));
        t.setEstado(EstadoTicket.ABIERTO);
        return mapper.aResponse(repositorio.save(t));
    }

    public TicketResponse actualizar(Long id, ActualizarTicketRequest req) {
        Ticket t = buscarEntidad(id);
        t.setTitulo(req.titulo().trim());
        t.setDescripcion(req.descripcion().trim());
        t.setCategoria(CategoriaTicket.desde(req.categoria()));
        t.setPrioridad(PrioridadTicket.desde(req.prioridad()));
        if (req.estado() != null && !req.estado().isBlank()) {
            t.setEstado(EstadoTicket.desde(req.estado()));
        }
        if (req.asignadoA() != null) {
            t.setAsignadoA(req.asignadoA().isBlank() ? null : req.asignadoA().trim());
        }
        return mapper.aResponse(repositorio.save(t));
    }

    public TicketResponse cambiarEstado(Long id, CambiarEstadoRequest req) {
        Ticket t = buscarEntidad(id);
        t.setEstado(EstadoTicket.desde(req.estado()));
        if (req.asignadoA() != null && !req.asignadoA().isBlank()) {
            t.setAsignadoA(req.asignadoA().trim());
        }
        return mapper.aResponse(repositorio.save(t));
    }

    private Ticket buscarEntidad(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Ticket no encontrado con id " + id));
    }

    /** Genera un codigo correlativo "TCK-0001". */
    private String generarCodigo() {
        long siguiente = repositorio.count() + 1;
        String codigo;
        do {
            codigo = String.format("TCK-%04d", siguiente++);
        } while (repositorio.existsByCodigo(codigo));
        return codigo;
    }
}
