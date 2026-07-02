package com.hospitalizacion.service.service;

import com.hospitalizacion.service.dto.CamaRequest;
import com.hospitalizacion.service.dto.CamaResponse;
import com.hospitalizacion.service.entity.Cama;
import com.hospitalizacion.service.exception.BadRequestException;
import com.hospitalizacion.service.exception.ResourceNotFoundException;
import com.hospitalizacion.service.repository.CamaRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CamaService {

    private final CamaRepository camaRepository;

    public List<CamaResponse> listar(String servicio, String estado) {
        if (servicio != null && estado != null) {
            return camaRepository.findByServicioAndEstado(servicio, estado).stream().map(this::toResponse).toList();
        }
        if (estado != null) {
            return camaRepository.findByEstado(estado).stream().map(this::toResponse).toList();
        }
        return camaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CamaResponse obtener(UUID id) {
        return toResponse(buscarCama(id));
    }

    public List<CamaResponse> listarDisponibles(String servicio) {
        if (servicio != null) {
            return camaRepository.findByServicioAndEstado(servicio, "DISPONIBLE").stream()
                    .map(this::toResponse).toList();
        }
        return camaRepository.findByEstado("DISPONIBLE").stream().map(this::toResponse).toList();
    }

    public CamaResponse crear(CamaRequest request) {
        if (camaRepository.findByNumeroAndServicio(request.getNumero(), request.getServicio()).isPresent()) {
            throw new BadRequestException("Ya existe una cama con el numero " + request.getNumero()
                    + " en el servicio " + request.getServicio());
        }
        Cama cama = new Cama();
        cama.setNumero(request.getNumero());
        cama.setServicio(request.getServicio());
        cama.setEstado("DISPONIBLE");
        cama = camaRepository.save(cama);
        return toResponse(cama);
    }

    public CamaResponse actualizar(UUID id, CamaRequest request) {
        Cama cama = buscarCama(id);
        cama.setNumero(request.getNumero());
        cama.setServicio(request.getServicio());
        cama = camaRepository.save(cama);
        return toResponse(cama);
    }

    public CamaResponse ocupar(UUID id, OcuparRequest request) {
        Cama cama = buscarCama(id);
        if (!"DISPONIBLE".equals(cama.getEstado())) {
            throw new BadRequestException("La cama " + cama.getNumero() + " no esta disponible");
        }
        cama.setEstado("OCUPADO");
        cama.setPacienteId(request.getPacienteId());
        cama.setPacienteNombre(request.getPacienteNombre());
        cama.setDiagnostico(request.getDiagnostico());
        cama.setMedicoNombre(request.getMedicoNombre());
        cama.setFechaIngreso(LocalDateTime.now());
        cama = camaRepository.save(cama);
        return toResponse(cama);
    }

    public CamaResponse liberar(UUID id) {
        Cama cama = buscarCama(id);
        cama.setEstado("DISPONIBLE");
        cama.setPacienteId(null);
        cama.setPacienteNombre(null);
        cama.setDiagnostico(null);
        cama.setMedicoNombre(null);
        cama.setFechaIngreso(null);
        cama = camaRepository.save(cama);
        return toResponse(cama);
    }

    public void eliminar(UUID id) {
        Cama cama = buscarCama(id);
        camaRepository.delete(cama);
    }

    private Cama buscarCama(UUID id) {
        return camaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cama no encontrada con id " + id));
    }

    private CamaResponse toResponse(Cama c) {
        return new CamaResponse(c.getId(), c.getNumero(), c.getServicio(), c.getEstado(),
                c.getPacienteId(), c.getPacienteNombre(), c.getFechaIngreso(),
                c.getDiagnostico(), c.getMedicoNombre(), c.getCreatedAt(), c.getUpdatedAt());
    }

    @Data
    public static class OcuparRequest {
        private UUID pacienteId;
        private String pacienteNombre;
        private String diagnostico;
        private UUID medicoId;
        private String medicoNombre;
    }
}
