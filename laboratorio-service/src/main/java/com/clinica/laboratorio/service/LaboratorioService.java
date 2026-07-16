package com.clinica.laboratorio.service;

import com.clinica.laboratorio.client.RadiologiaClient;
import com.clinica.laboratorio.dto.ExamenSolicitadoDto;
import com.clinica.laboratorio.dto.IngresarResultadosRequest;
import com.clinica.laboratorio.dto.ItemExamenRequest;
import com.clinica.laboratorio.dto.OrdenLabDto;
import com.clinica.laboratorio.dto.PacienteOrdenDto;
import com.clinica.laboratorio.dto.RegistrarMuestraRequest;
import com.clinica.laboratorio.dto.ResultadoItemRequest;
import com.clinica.laboratorio.dto.SolicitudExamenesRequest;
import com.clinica.laboratorio.dto.SolicitudExamenesResponse;
import com.clinica.laboratorio.entity.ExamenLaboratorio;
import com.clinica.laboratorio.entity.OrdenLaboratorio;
import com.clinica.laboratorio.repository.OrdenLaboratorioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LaboratorioService {
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

    private final AtomicInteger secuencia = new AtomicInteger(850);
    private final RadiologiaClient radiologiaClient;
    private final OrdenLaboratorioRepository ordenRepository;

    public LaboratorioService(RadiologiaClient radiologiaClient, OrdenLaboratorioRepository ordenRepository) {
        this.radiologiaClient = radiologiaClient;
        this.ordenRepository = ordenRepository;
    }

    @Transactional
    public SolicitudExamenesResponse crearSolicitud(SolicitudExamenesRequest request) {
        PacienteOrdenDto paciente = pacienteSeguro(request.paciente());
        List<OrdenLabDto> lab = new ArrayList<>();
        List<Object> radiologia = new ArrayList<>();

        for (ItemExamenRequest item : request.items()) {
            if (item.esLaboratorio()) {
                OrdenLaboratorio orden = crearOrdenLaboratorio(request, paciente, item);
                lab.add(toDto(ordenRepository.save(orden)));
            } else if (item.esImagen()) {
                Object estudio = radiologiaClient.crearEstudioDesdeSolicitud(
                        new RadiologiaClient.RadiologiaSolicitudRequest(
                                request.atencionId(),
                                paciente,
                                request.medicoSolicitante(),
                                request.especialidadMedico(),
                                item
                        )
                );
                radiologia.add(estudio);
            }
        }

        return new SolicitudExamenesResponse("Solicitud firmada y enviada", lab, radiologia);
    }

    @Transactional(readOnly = true)
    public List<OrdenLabDto> listarOrdenes() {
        return ordenRepository.findAllByOrderByPrioridadAscFechaSolicitudAsc().stream()
                .sorted((a, b) -> {
                    int pa = prioridadOrden(a.getPrioridad());
                    int pb = prioridadOrden(b.getPrioridad());
                    if (pa != pb) return Integer.compare(pa, pb);
                    return a.getFechaSolicitud().compareTo(b.getFechaSolicitud());
                })
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrdenLabDto obtener(String id) {
        return toDto(obtenerEntidad(id));
    }

    @Transactional
    public OrdenLabDto registrarMuestra(String id, RegistrarMuestraRequest request) {
        OrdenLaboratorio actual = obtenerEntidad(id);
        actual.setEstado("Muestra Registrada");
        if (request != null && request.origenMuestra() != null && !request.origenMuestra().isBlank()) {
            actual.setOrigenMuestra(request.origenMuestra());
        }
        return toDto(ordenRepository.save(actual));
    }

    @Transactional
    public OrdenLabDto ingresarResultados(String id, IngresarResultadosRequest request) {
        OrdenLaboratorio actual = obtenerEntidad(id);
        actual.setEstado("Resultados Pendientes");
        if (request != null && request.resultados() != null) {
            for (ResultadoItemRequest resultado : request.resultados()) {
                actual.getExamenes().stream()
                        .filter(e -> e.getId().toString().equals(resultado.examenId()))
                        .findFirst()
                        .ifPresent(e -> {
                            e.setResultado(resultado.resultado());
                            e.setUnidad(resultado.unidad());
                            e.setValorRef(resultado.valorRef());
                            e.setCritico(Boolean.TRUE.equals(resultado.critico()));
                            e.setObservaciones(resultado.observaciones());
                        });
            }
        }
        return toDto(ordenRepository.save(actual));
    }

    @Transactional
    public OrdenLabDto validar(String id) {
        OrdenLaboratorio actual = obtenerEntidad(id);
        actual.setEstado("Validado");
        return toDto(ordenRepository.save(actual));
    }

    private OrdenLaboratorio crearOrdenLaboratorio(
            SolicitudExamenesRequest request,
            PacienteOrdenDto paciente,
            ItemExamenRequest item
    ) {
        OrdenLaboratorio orden = new OrdenLaboratorio();
        orden.setNroOrden("ORD-2026-" + secuencia.incrementAndGet());
        orden.setAtencionId(parseUuidOrNull(request.atencionId()));
        orden.setPacienteId(parseUuidOrNull(paciente.id()));
        orden.setPacienteNombre(paciente.nombre());
        orden.setPacienteApellidos(paciente.apellidos());
        orden.setPacienteDni(paciente.dni());
        orden.setPacienteEdad(paciente.edad());
        orden.setPacienteSexo(paciente.sexo());
        orden.setNroHistoria(paciente.nroHistoria());
        orden.setMedicoSolicitante(valor(request.medicoSolicitante(), "Medico solicitante pendiente"));
        orden.setEspecialidadMedico(valor(request.especialidadMedico(), "Medicina General"));
        orden.setFechaSolicitud(LocalDateTime.now());
        orden.setPrioridad(item.urgenteNormalizado() ? "Urgente" : "Normal");
        orden.setEstado("Pendiente");
        orden.setOrigenMuestra(item.origenMuestra());
        orden.setAyuno(item.ayuno());
        orden.setIndicaciones(item.indicaciones());

        ExamenLaboratorio examen = new ExamenLaboratorio();
        examen.setNombre(item.nombre());
        examen.setArea(areaLaboratorio(item.nombre()));
        examen.setAnalizador(analizador(item.nombre()));
        orden.agregarExamen(examen);
        return orden;
    }

    private PacienteOrdenDto pacienteSeguro(PacienteOrdenDto paciente) {
        if (paciente == null) {
            return new PacienteOrdenDto(null, null, null, null, null, null, null).normalizado();
        }
        return paciente.normalizado();
    }

    private String areaLaboratorio(String examen) {
        String e = examen.toLowerCase();
        if (e.contains("orina") || e.contains("urocultivo")) return "Orina";
        if (e.contains("bacilo")) return "Microbiologia";
        if (e.contains("glucosa") || e.contains("creatinina") || e.contains("urea") || e.contains("lipid")) return "Quimica";
        return "Hematologia";
    }

    private String analizador(String examen) {
        return switch (areaLaboratorio(examen)) {
            case "Quimica" -> "Architect c8000 (Quimica)";
            case "Microbiologia" -> "Vitek 2 (Microbiologia)";
            case "Orina" -> "iQ200 (Urinalisis)";
            default -> "Coulter DxH 900 (Hematologia)";
        };
    }

    private String valor(String actual, String fallback) {
        return actual == null || actual.isBlank() ? fallback : actual;
    }

    private OrdenLaboratorio obtenerEntidad(String id) {
        UUID uuid = UUID.fromString(id);
        return ordenRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Orden de laboratorio no encontrada: " + id));
    }

    private OrdenLabDto toDto(OrdenLaboratorio orden) {
        PacienteOrdenDto paciente = new PacienteOrdenDto(
                orden.getPacienteId() == null ? null : orden.getPacienteId().toString(),
                orden.getPacienteNombre(),
                orden.getPacienteApellidos(),
                orden.getPacienteDni(),
                orden.getPacienteEdad(),
                orden.getPacienteSexo(),
                orden.getNroHistoria()
        );
        List<ExamenSolicitadoDto> examenes = orden.getExamenes().stream()
                .map(e -> new ExamenSolicitadoDto(e.getId().toString(), e.getNombre(), e.getArea(), e.getAnalizador()))
                .toList();
        return new OrdenLabDto(
                orden.getId().toString(),
                orden.getNroOrden(),
                paciente,
                orden.getMedicoSolicitante(),
                orden.getEspecialidadMedico(),
                orden.getFechaSolicitud().format(FECHA),
                examenes,
                orden.getPrioridad(),
                orden.getEstado(),
                orden.getOrigenMuestra(),
                orden.getAyuno(),
                orden.getIndicaciones()
        );
    }

    private int prioridadOrden(String prioridad) {
        return "Urgente".equals(prioridad) ? 0 : 1;
    }

    private UUID parseUuidOrNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
