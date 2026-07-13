package com.recepcion.service.service;

import com.recepcion.service.dto.ColaTriajeResponse;
import com.recepcion.service.dto.IngresarColaRequest;
import com.recepcion.service.entity.Cola;
import com.recepcion.service.entity.Paciente;
import com.recepcion.service.exception.ResourceNotFoundException;
import com.recepcion.service.repository.ColaRepository;
import com.recepcion.service.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ColaService {

    private final ColaRepository colaRepository;
    private final PacienteRepository pacienteRepository;

    public ColaTriajeResponse ingresar(IngresarColaRequest request) {
        Paciente paciente = pacienteRepository.findById(request.pacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        String ticket = generarTicket(LocalDate.now());
        Cola cola = new Cola();
        cola.setPaciente(paciente);
        cola.setPacienteNombre(request.pacienteNombre());
        cola.setPacienteDni(request.pacienteDni());
        cola.setTicket(ticket);
        cola.setHoraLlegada(request.horaLlegada() != null ? request.horaLlegada() : LocalTime.now());
        cola.setMedicoNombre(request.medicoNombre());
        cola.setEspecialidad(request.especialidad());
        cola.setMotivo(request.motivo());
        cola.setEstado("EN_ESPERA");
        cola.setCitaId(request.citaId());
        cola.setFecha(LocalDate.now());
        cola = colaRepository.save(cola);
        return toResponse(cola);
    }

    @Transactional(readOnly = true)
    public List<ColaTriajeResponse> obtenerColaTriaje(LocalDate fecha) {
        return colaRepository.findByFechaOrderByTicketAsc(fecha)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void actualizarEstado(UUID id, String estado) {
        Cola cola = colaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrada de cola no encontrada"));
        cola.setEstado(estado);
        colaRepository.save(cola);
    }

    private ColaTriajeResponse toResponse(Cola cola) {
        return new ColaTriajeResponse(
                cola.getId(),
                cola.getPaciente().getId(),
                cola.getPacienteNombre(),
                cola.getPacienteDni(),
                cola.getTicket(),
                cola.getHoraLlegada().toString(),
                cola.getMedicoNombre(),
                cola.getEspecialidad(),
                cola.getMotivo(),
                cola.getEstado(),
                cola.getCitaId(),
                cola.getFecha()
        );
    }

    private String generarTicket(LocalDate fecha) {
        long count = colaRepository.findByFechaOrderByTicketAsc(fecha).size();
        String fechaStr = fecha.toString().replace("-", "");
        return String.format("TKT-%s-%04d", fechaStr, count + 1);
    }
}
