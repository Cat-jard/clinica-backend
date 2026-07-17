package com.hospitalizacion.service.service;

import com.hospitalizacion.service.config.RabbitMQConfig;
import com.hospitalizacion.service.dto.*;
import com.hospitalizacion.service.entity.AutorizacionIngreso;
import com.hospitalizacion.service.entity.Epicrisis;
import com.hospitalizacion.service.entity.Hospitalizacion;
import com.hospitalizacion.service.exception.*;
import com.hospitalizacion.service.repository.AutorizacionIngresoRepository;
import com.hospitalizacion.service.repository.CamaRepository;
import com.hospitalizacion.service.repository.EpicrisisRepository;
import com.hospitalizacion.service.repository.HospitalizacionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class HospitalizacionService {

    private final HospitalizacionRepository hospitalizacionRepository;
    private final AutorizacionIngresoRepository autorizacionRepository;
    private final EpicrisisRepository epicrisisRepository;
    private final CamaService camaService;
    private final CamaRepository camaRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public Page<HospitalizacionResponse> listar(String estado, String servicio, Pageable pageable) {
        return hospitalizacionRepository.buscarConFiltros(estado, servicio, pageable)
                .map(this::toResponse);
    }

    public HospitalizacionResponse obtenerPorId(UUID id) {
        return toResponse(buscarHospitalizacion(id));
    }

    public HospitalizacionResponse obtenerActivaPorPaciente(UUID pacienteId) {
        var h = hospitalizacionRepository.findByPacienteIdAndEstado(pacienteId, "HOSPITALIZADO")
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No hay hospitalizacion activa para el paciente " + pacienteId));
        return toResponse(h);
    }

    public List<CamaResponse> obtenerCamasDisponibles(String servicio) {
        return camaService.listarDisponibles(servicio);
    }

    public HospitalizacionResponse registrarIngreso(IngresoRequest request) {
        if (hospitalizacionRepository.existsByPacienteIdAndEstado(request.getPacienteId(), "HOSPITALIZADO")) {
            throw new HospitalizacionActivaException(
                    "El paciente ya tiene una hospitalizacion activa");
        }

        var paciente = obtenerPaciente(request.getPacienteId());
        String pacienteNombre = paciente.getNombres() + " " + paciente.getApellidoPaterno()
                + " " + (paciente.getApellidoMaterno() != null ? paciente.getApellidoMaterno() : "");
        String pacienteDni = paciente.getNroDocumento();

        var disponibles = camaService.listarDisponibles(request.getServicio());

        CamaResponse cama;
        if (request.getCamaId() != null) {
            UUID camaId = request.getCamaId();
            cama = disponibles.stream()
                    .filter(c -> c.id().equals(camaId))
                    .findFirst()
                    .orElseThrow(() -> new CamaNoDisponibleException(
                            "La cama " + camaId + " no esta disponible en " + request.getServicio()));
        } else {
            cama = disponibles.stream()
                    .findFirst()
                    .orElseThrow(() -> new CamaNoDisponibleException(
                            "No hay camas disponibles en " + request.getServicio()));
        }

        var ocuparReq = new CamaService.OcuparRequest();
        ocuparReq.setPacienteId(request.getPacienteId());
        ocuparReq.setPacienteNombre(pacienteNombre);
        ocuparReq.setDiagnostico(request.getDiagnosticoIngreso());
        ocuparReq.setMedicoId(request.getMedicoId());
        ocuparReq.setMedicoNombre("");
        camaService.ocupar(cama.id(), ocuparReq);

        Hospitalizacion h = new Hospitalizacion();
        h.setCamaId(cama.id());
        h.setCamaNumero(cama.numero());
        h.setServicio(request.getServicio());
        h.setPacienteId(request.getPacienteId());
        h.setPacienteNombre(pacienteNombre);
        h.setPacienteDni(pacienteDni);
        h.setMedicoId(request.getMedicoId());
        h.setMedicoNombre("");
        h.setMotivoIngreso(request.getMotivoIngreso());
        h.setDiagnosticoIngreso(request.getDiagnosticoIngreso());
        h.setFechaIngreso(LocalDateTime.now());
        h.setEstado("HOSPITALIZADO");
        h.setUserIdIngreso(request.getUserId());
        h = hospitalizacionRepository.save(h);

        AutorizacionIngreso autorizacion = new AutorizacionIngreso();
        autorizacion.setHospitalizacion(h);
        autorizacion.setTextoLegal("Autorizo voluntariamente mi ingreso y hospitalizacion para recibir atencion medica. " +
                "He sido informado de los procedimientos, riesgos y beneficios del tratamiento propuesto. " +
                "Autorizo el uso de mis datos personales conforme a la Ley N°29733.");
        autorizacion.setFirmado(false);
        autorizacionRepository.save(autorizacion);

        return toResponse(h);
    }

    public AutorizacionResponse generarAutorizacion(UUID hospitalizacionId, AutorizacionRequest request) {
        Hospitalizacion h = buscarHospitalizacion(hospitalizacionId);

        AutorizacionIngreso a = autorizacionRepository.findByHospitalizacionId(hospitalizacionId)
                .orElseGet(() -> {
                    var nueva = new AutorizacionIngreso();
                    nueva.setHospitalizacion(h);
                    nueva.setTextoLegal("Autorizo voluntariamente mi ingreso y hospitalizacion...");
                    nueva.setFirmado(false);
                    return nueva;
                });

        if (request.getRepresentanteNombre() != null) a.setRepresentanteNombre(request.getRepresentanteNombre());
        if (request.getRepresentanteDni() != null) a.setRepresentanteDni(request.getRepresentanteDni());
        if (request.getFirmaBase64() != null) {
            a.setFirmaBase64(request.getFirmaBase64());
            a.setFirmado(true);
        }
        a = autorizacionRepository.save(a);

        return new AutorizacionResponse(a.getId(), a.getHospitalizacion().getId(),
                a.getRepresentanteNombre(), a.getRepresentanteDni(), a.getTextoLegal(),
                a.getFirmado(), a.getCreatedAt());
    }

    public HospitalizacionResponse trasladar(UUID hospitalizacionId, TrasladoRequest request) {
        Hospitalizacion h = buscarHospitalizacion(hospitalizacionId);
        if (!"HOSPITALIZADO".equals(h.getEstado())) {
            throw new AltaInvalidaException("La hospitalizacion no esta activa");
        }

        UUID camaAnteriorId = h.getCamaId();
        var disponibles = camaService.listarDisponibles(h.getServicio());
        CamaResponse nuevaCama = disponibles.stream()
                .filter(c -> c.id().equals(request.getNuevaCamaId()))
                .findFirst()
                .orElseThrow(() -> new CamaNoDisponibleException(
                        "La cama " + request.getNuevaCamaId() + " no esta disponible"));

        var ocuparReq = new CamaService.OcuparRequest();
        ocuparReq.setPacienteId(h.getPacienteId());
        ocuparReq.setPacienteNombre(h.getPacienteNombre());
        ocuparReq.setDiagnostico(h.getDiagnosticoIngreso());
        ocuparReq.setMedicoId(h.getMedicoId());
        ocuparReq.setMedicoNombre(h.getMedicoNombre());
        camaService.ocupar(nuevaCama.id(), ocuparReq);
        camaService.liberar(camaAnteriorId);

        h.setCamaId(nuevaCama.id());
        h.setCamaNumero(nuevaCama.numero());
        h.setServicio(nuevaCama.servicio());
        if (request.getMotivo() != null) {
            String obs = h.getObservaciones() != null ? h.getObservaciones() + "\n" : "";
            h.setObservaciones(obs + "Traslado: " + request.getMotivo());
        }
        h = hospitalizacionRepository.save(h);

        return toResponse(h);
    }

    public HospitalizacionResponse darAlta(UUID hospitalizacionId, AltaRequest request) {
        Hospitalizacion h = buscarHospitalizacion(hospitalizacionId);
        if (!"HOSPITALIZADO".equals(h.getEstado())) {
            throw new AltaInvalidaException("La hospitalizacion no esta activa");
        }
        if (epicrisisRepository.existsByHospitalizacionId(hospitalizacionId)) {
            throw new BadRequestException("La epicrisis ya fue generada para esta hospitalizacion");
        }

        Epicrisis e = new Epicrisis();
        e.setHospitalizacion(h);
        e.setFechaIngreso(h.getFechaIngreso());
        e.setFechaAlta(LocalDateTime.now());
        e.setMotivoIngreso(h.getMotivoIngreso());
        e.setDiagnosticoIngreso(h.getDiagnosticoIngreso());
        e.setDiagnosticoFinal(request.getDiagnosticoFinal());
        e.setEvolucion(request.getEvolucion());
        e.setProcedimientos(request.getProcedimientos());
        e.setComplicaciones(request.getComplicaciones());
        e.setTratamiento(request.getTratamiento());
        e.setRecomendaciones(request.getRecomendaciones());
        e.setProximaCita(request.getProximaCita());
        e.setFirmado(true);
        e.setFirmaBase64(request.getFirmaBase64());
        e.setMedicoId(request.getMedicoId());
        e.setMedicoNombre(request.getMedicoNombre());
        epicrisisRepository.save(e);

        camaService.liberar(h.getCamaId());

        h.setDiagnosticoAlta(request.getDiagnosticoFinal());
        h.setFechaAlta(LocalDateTime.now());
        h.setTipoAlta(request.getTipoAlta());
        h.setEstado("ALTA");
        h.setUserIdAlta(request.getMedicoId());
        h = hospitalizacionRepository.save(h);

        return toResponse(h);
    }

    public EpicrisisResponse obtenerEpicrisis(UUID hospitalizacionId) {
        Epicrisis e = epicrisisRepository.findByHospitalizacionId(hospitalizacionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Epicrisis no encontrada para hospitalizacion " + hospitalizacionId));
        return new EpicrisisResponse(e.getId(), e.getHospitalizacion().getId(),
                e.getFechaIngreso(), e.getFechaAlta(), e.getMotivoIngreso(),
                e.getDiagnosticoIngreso(), e.getDiagnosticoFinal(), e.getEvolucion(),
                e.getProcedimientos(), e.getComplicaciones(), e.getTratamiento(),
                e.getRecomendaciones(), e.getProximaCita(), e.getFirmado(),
                e.getMedicoId(), e.getMedicoNombre(), e.getCreatedAt());
    }

    public Map<String, Object> obtenerOcupacion() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("hospitalizados", hospitalizacionRepository.countByEstado("HOSPITALIZADO"));
        result.put("altas", hospitalizacionRepository.countByEstado("ALTA"));
        result.put("camasDisponibles", camaRepository.countByEstado("DISPONIBLE"));
        result.put("camasOcupadas", camaRepository.countByEstado("OCUPADO"));
        result.put("totalCamas", camaRepository.count());

        var porServicio = hospitalizacionRepository.countHospitalizadosPorServicio();
        List<Map<String, Object>> detalle = new ArrayList<>();
        for (Object[] row : porServicio) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("servicio", row[0]);
            item.put("cantidad", row[1]);
            detalle.add(item);
        }
        result.put("porServicio", detalle);
        return result;
    }

    private Hospitalizacion buscarHospitalizacion(UUID id) {
        return hospitalizacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospitalizacion no encontrada"));
    }

    private PacienteResponse obtenerPaciente(UUID pacienteId) {
        String json = (String) rabbitTemplate.convertSendAndReceive(
                RabbitMQConfig.CLINIC_EXCHANGE,
                RabbitMQConfig.PACIENTE_REQUEST_ROUTING_KEY,
                pacienteId
        );
        if (json == null || "null".equals(json)) {
            throw new ResourceNotFoundException("Paciente no encontrado con id " + pacienteId);
        }
        try {
            return objectMapper.readValue(json, PacienteResponse.class);
        } catch (Exception e) {
            throw new PacienteServiceException("Error al mapear paciente: " + e.getMessage(), e);
        }
    }

    private HospitalizacionResponse toResponse(Hospitalizacion h) {
        return new HospitalizacionResponse(
                h.getId(), h.getCamaId(), h.getCamaNumero(), h.getServicio(),
                h.getPacienteId(), h.getPacienteNombre(), h.getPacienteDni(),
                h.getMedicoId(), h.getMedicoNombre(), h.getMotivoIngreso(),
                h.getDiagnosticoIngreso(), h.getDiagnosticoAlta(),
                h.getFechaIngreso(), h.getFechaAlta(), h.getTipoAlta(),
                h.getEstado(), h.getObservaciones(), h.getUserIdIngreso(),
                h.getUserIdAlta(), h.getCreatedAt(), h.getUpdatedAt());
    }
}
