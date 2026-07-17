package com.historiaclinica.service.service;

import com.historiaclinica.service.dto.*;
import com.historiaclinica.service.entity.*;
import com.historiaclinica.service.exception.BadRequestException;
import com.historiaclinica.service.exception.ResourceNotFoundException;
import com.historiaclinica.service.mapper.AtencionMapper;
import com.historiaclinica.service.repository.AtencionMedicaRepository;
import com.historiaclinica.service.repository.DiagnosticoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AtencionService {

    private final AtencionMedicaRepository atencionRepository;
    private final DiagnosticoRepository diagnosticoRepository;
    private final AtencionMapper mapper;

    public AtencionResponse iniciarAtencion(IniciarAtencionRequest request) {
        AtencionMedica atencion = new AtencionMedica();
        atencion.setPacienteId(request.getPacienteId());
        atencion.setMedicoId(request.getMedicoId());
        atencion.setMedicoNombre(request.getMedicoNombre());
        atencion.setEspecialidad(request.getEspecialidad());
        atencion.setConsultorio(request.getConsultorio());
        atencion.setFechaAtencion(LocalDate.now());
        atencion.setHoraInicio(LocalTime.now());
        atencion.setEstado("BORRADOR");
        atencion.setAnamnesis(new Anamnesis());
        atencion.setExamenFisico(new ExamenFisico());
        atencion.setDiagnosticos(new ArrayList<>());
        atencion.setRecetas(new ArrayList<>());
        atencion.setSolicitudesExamenes(new ArrayList<>());
        atencion.setInterconsultas(new ArrayList<>());

        atencion = atencionRepository.save(atencion);
        return mapper.toAtencionResponse(atencion);
    }

    public AtencionResponse obtenerAtencion(UUID id) {
        AtencionMedica atencion = atencionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atencion no encontrada con id " + id));
        return mapper.toAtencionResponse(atencion);
    }

    public AtencionResponse buscarOBorradorActivo(UUID pacienteId, Long medicoId) {
        return atencionRepository
                .findFirstByPacienteIdAndMedicoIdAndFechaAtencionAndEstadoOrderByHoraInicioDesc(
                        pacienteId, medicoId, LocalDate.now(), "BORRADOR")
                .map(mapper::toAtencionResponse)
                .orElse(null);
    }

    public AtencionResponse guardarAtencion(UUID id, GuardarAtencionRequest request) {
        AtencionMedica atencion = atencionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atencion no encontrada con id " + id));

        if ("FIRMADA".equals(atencion.getEstado())) {
            throw new BadRequestException("No se puede modificar una atencion ya firmada");
        }

        updateAnamnesis(atencion, request.getAnamnesis());
        updateExamenFisico(atencion, request.getExamenFisico());
        updateDiagnosticos(atencion, request.getDiagnosticos());
        updateRecetas(atencion, request.getRecetas());
        updateSolicitudesExamenes(atencion, request.getSolicitudesExamenes());
        updateInterconsultas(atencion, request.getInterconsultas());

        if (request.getIndicacionesGenerales() != null) {
            atencion.setIndicacionesGenerales(request.getIndicacionesGenerales());
        }
        if (request.getProcedimientosRealizados() != null) {
            atencion.setProcedimientosRealizados(request.getProcedimientosRealizados());
        }

        atencion = atencionRepository.save(atencion);
        return mapper.toAtencionResponse(atencion);
    }

    public AtencionResponse firmarAtencion(UUID id, FirmarAtencionRequest request) {
        AtencionMedica atencion = atencionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atencion no encontrada con id " + id));

        if ("FIRMADA".equals(atencion.getEstado())) {
            throw new BadRequestException("La atencion ya esta firmada");
        }

        atencion.setEstado("FIRMADA");
        atencion.setFirmadaEn(LocalDateTime.now());
        atencion.setFirmaMedicoId(request.getMedicoId());
        atencion.setFirmaBase64(request.getFirmaBase64());
        atencion.setFirmaHash(sha256(request.getFirmaBase64()));
        atencion.setHoraFin(LocalTime.now());

        atencion = atencionRepository.save(atencion);
        return mapper.toAtencionResponse(atencion);
    }

    public List<AtencionResponse> listarPorPaciente(UUID pacienteId) {
        return mapper.toAtencionResponseList(
                atencionRepository.findByPacienteIdOrderByFechaAtencionDesc(pacienteId));
    }

    public List<HistorialPacienteItem> listarHistorialPaciente(UUID pacienteId) {
        List<AtencionMedica> atenciones = atencionRepository
                .findByPacienteIdOrderByFechaAtencionDesc(pacienteId);
        return mapper.toHistorialItemList(atenciones);
    }

    private void updateAnamnesis(AtencionMedica atencion, AnamnesisDTO dto) {
        if (dto == null) return;
        Anamnesis a = atencion.getAnamnesis();
        if (a == null) a = new Anamnesis();
        a.setMotivoConsulta(dto.getMotivoConsulta());
        a.setEnfermedadActual(dto.getEnfermedadActual());
        a.setAntecedentesPatologicos(dto.getAntecedentesPatologicos() != null ? String.join(",", dto.getAntecedentesPatologicos()) : null);
        a.setAntecedentesQuirurgicos(dto.getAntecedentesQuirurgicos());
        a.setAntecedentesAlergicos(dto.getAntecedentesAlergicos());
        a.setAntecedentesFamiliares(dto.getAntecedentesFamiliares());
        a.setHabitos(dto.getHabitos() != null ? String.join(",", dto.getHabitos()) : null);
        a.setMedicacionActual(dto.getMedicacionActual());
        atencion.setAnamnesis(a);
    }

    private void updateExamenFisico(AtencionMedica atencion, ExamenFisicoDTO dto) {
        if (dto == null) return;
        ExamenFisico e = atencion.getExamenFisico();
        if (e == null) e = new ExamenFisico();
        e.setExamenGeneral(dto.getExamenGeneral());
        e.setCabezaCuello(dto.getCabezaCuello());
        e.setToraxPulmones(dto.getToraxPulmones());
        e.setCorazon(dto.getCorazon());
        e.setAbdomen(dto.getAbdomen());
        e.setExtremidades(dto.getExtremidades());
        e.setNeurologico(dto.getNeurologico());
        e.setOtros(dto.getOtros());
        atencion.setExamenFisico(e);
    }

    private void updateDiagnosticos(AtencionMedica atencion, List<DiagnosticoDTO> dtos) {
        atencion.getDiagnosticos().clear();
        if (dtos == null) return;
        short orden = 0;
        for (DiagnosticoDTO dto : dtos) {
            Diagnostico d = new Diagnostico();
            d.setAtencion(atencion);
            d.setCodigo(dto.getCodigo());
            d.setDescripcion(dto.getDescripcion());
            d.setTipo(dto.getTipo());
            d.setOrden(orden++);
            atencion.getDiagnosticos().add(d);
        }
    }

    private void updateRecetas(AtencionMedica atencion, List<RecetaDTO> dtos) {
        atencion.getRecetas().clear();
        if (dtos == null) return;
        for (RecetaDTO dto : dtos) {
            Receta r = new Receta();
            r.setAtencion(atencion);
            r.setEstado(dto.getEstado() != null ? dto.getEstado() : "BORRADOR");
            r.setCreatedAt(LocalDateTime.now());
            if (dto.getItems() != null) {
                for (ItemRecetaDTO itemDto : dto.getItems()) {
                    ItemReceta item = new ItemReceta();
                    item.setReceta(r);
                    item.setMedicamento(itemDto.getMedicamento());
                    item.setConcentracion(itemDto.getConcentracion());
                    item.setPresentacion(itemDto.getPresentacion());
                    item.setDosis(itemDto.getDosis());
                    item.setVia(itemDto.getVia());
                    item.setFrecuencia(itemDto.getFrecuencia());
                    item.setDuracion(itemDto.getDuracion());
                    item.setCantidad(itemDto.getCantidad());
                    item.setIndicacionesEspeciales(itemDto.getIndicacionesEspeciales());
                    r.getItems().add(item);
                }
            }
            atencion.getRecetas().add(r);
        }
    }

    private void updateSolicitudesExamenes(AtencionMedica atencion, List<SolicitudExamenDTO> dtos) {
        atencion.getSolicitudesExamenes().clear();
        if (dtos == null) return;
        for (SolicitudExamenDTO dto : dtos) {
            SolicitudExamen s = new SolicitudExamen();
            s.setAtencion(atencion);
            s.setEstado(dto.getEstado() != null ? dto.getEstado() : "BORRADOR");
            s.setCreatedAt(LocalDateTime.now());
            if (dto.getItems() != null) {
                for (ItemExamenDTO itemDto : dto.getItems()) {
                    ItemExamen item = new ItemExamen();
                    item.setSolicitud(s);
                    item.setTipo(itemDto.getTipo());
                    item.setNombre(itemDto.getNombre());
                    item.setOrigenMuestra(itemDto.getOrigenMuestra());
                    item.setAyuno(itemDto.getAyuno());
                    item.setUrgente(itemDto.getUrgente() != null ? itemDto.getUrgente() : false);
                    item.setIndicaciones(itemDto.getIndicaciones());
                    s.getItems().add(item);
                }
            }
            atencion.getSolicitudesExamenes().add(s);
        }
    }

    private void updateInterconsultas(AtencionMedica atencion, List<InterconsultaDTO> dtos) {
        atencion.getInterconsultas().clear();
        if (dtos == null) return;
        for (InterconsultaDTO dto : dtos) {
            Interconsulta i = new Interconsulta();
            i.setAtencion(atencion);
            i.setEspecialidadDestino(dto.getEspecialidadDestino());
            i.setMedicoDestino(dto.getMedicoDestino());
            i.setMotivoInterconsulta(dto.getMotivoInterconsulta());
            i.setHallazgosRelevantes(dto.getHallazgosRelevantes());
            i.setPreguntaEspecialista(dto.getPreguntaEspecialista());
            i.setUrgencia(dto.getUrgencia() != null ? dto.getUrgencia() : "Normal");
            i.setEstado(dto.getEstado() != null ? dto.getEstado() : "Enviada");
            atencion.getInterconsultas().add(i);
        }
    }

    private String sha256(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 no disponible", e);
        }
    }
}
