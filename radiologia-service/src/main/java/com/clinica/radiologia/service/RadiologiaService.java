package com.clinica.radiologia.service;

import com.clinica.radiologia.dto.EstudioImagenDto;
import com.clinica.radiologia.dto.FirmarInformeRequest;
import com.clinica.radiologia.dto.InformeRadiologicoDto;
import com.clinica.radiologia.dto.PacienteRadiologiaDto;
import com.clinica.radiologia.dto.RadiologiaSolicitudRequest;
import com.clinica.radiologia.dto.SerieDicomDto;
import com.clinica.radiologia.entity.EstudioRadiologia;
import com.clinica.radiologia.entity.SerieRadiologia;
import com.clinica.radiologia.repository.EstudioRadiologiaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RadiologiaService {
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

    private final AtomicInteger secuencia = new AtomicInteger(313);
    private final EstudioRadiologiaRepository estudioRepository;

    public RadiologiaService(EstudioRadiologiaRepository estudioRepository) {
        this.estudioRepository = estudioRepository;
    }

    @Transactional
    public EstudioImagenDto crearDesdeSolicitud(RadiologiaSolicitudRequest request) {
        String modalidad = modalidadDesdeNombre(request.item().nombre());
        PacienteRadiologiaDto paciente = pacienteSeguro(request.paciente());
        EstudioRadiologia estudio = new EstudioRadiologia();
        estudio.setNroOrden("IMG-2026-" + secuencia.incrementAndGet());
        estudio.setAtencionId(parseUuidOrNull(request.atencionId()));
        estudio.setPacienteId(parseUuidOrNull(paciente.id()));
        estudio.setPacienteNombre(paciente.nombre());
        estudio.setPacienteApellidos(paciente.apellidos());
        estudio.setPacienteDni(paciente.dni());
        estudio.setPacienteEdad(paciente.edad());
        estudio.setPacienteSexo(paciente.sexo());
        estudio.setNroHistoria(paciente.nroHistoria());
        estudio.setMedicoSolicitante(valor(request.medicoSolicitante(), "Medico solicitante pendiente"));
        estudio.setEspecialidadMedico(valor(request.especialidadMedico(), "Medicina General"));
        estudio.setModalidad(modalidad);
        estudio.setTipoEstudio(request.item().nombre());
        estudio.setRegionAnatomica(regionDesdeNombre(request.item().nombre()));
        estudio.setFechaSolicitud(LocalDateTime.now());
        estudio.setFechaEstudio(LocalDateTime.now());
        estudio.setPrioridad(request.item().urgenteNormalizado() ? "Urgente" : "Normal");
        estudio.setEstado("Pendiente");
        estudio.setEsCritico(false);
        estudio.setIndicaciones(request.item().indicaciones());
        series(modalidad).forEach(serie -> {
            SerieRadiologia entity = new SerieRadiologia();
            entity.setDescripcion(serie.descripcion());
            entity.setNumCortes(serie.numCortes());
            estudio.agregarSerie(entity);
        });
        return toDto(estudioRepository.save(estudio));
    }

    @Transactional(readOnly = true)
    public List<EstudioImagenDto> listar() {
        return estudioRepository.findAllByOrderByPrioridadAscFechaSolicitudAsc().stream()
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
    public EstudioImagenDto obtener(String id) {
        return toDto(obtenerEntidad(id));
    }

    @Transactional
    public EstudioImagenDto iniciarLectura(String id) {
        EstudioRadiologia actual = obtenerEntidad(id);
        actual.setEstado("En Proceso");
        return toDto(estudioRepository.save(actual));
    }

    @Transactional
    public EstudioImagenDto guardarBorrador(String id, InformeRadiologicoDto informe) {
        EstudioRadiologia actual = obtenerEntidad(id);
        InformeRadiologicoDto completo = informe == null ? informeSimulado(actual) : informe;
        aplicarInforme(actual, completo);
        actual.setEstado("Borrador");
        return toDto(estudioRepository.save(actual));
    }

    @Transactional
    public EstudioImagenDto firmar(String id, FirmarInformeRequest request) {
        EstudioRadiologia actual = obtenerEntidad(id);
        InformeRadiologicoDto informe = request.informe() == null ? informeSimulado(actual) : request.informe();
        aplicarInforme(actual, informe);
        actual.setEstado("Firmado");
        actual.setFirmadoEn(LocalDateTime.now());
        actual.setEsCritico(Boolean.TRUE.equals(request.urgente()) || "Urgente".equals(actual.getPrioridad()));
        return toDto(estudioRepository.save(actual));
    }

    private InformeRadiologicoDto informeSimulado(EstudioRadiologia estudio) {
        return new InformeRadiologicoDto(
                "Imagenes simuladas cargadas correctamente. No se observan lesiones agudas evidentes.",
                "Estudio " + estudio.getTipoEstudio() + " sin hallazgos criticos simulados.",
                "Correlacionar con clinica y controles segun evolucion.",
                null,
                "Dosis simulada"
        );
    }

    private PacienteRadiologiaDto pacienteSeguro(PacienteRadiologiaDto paciente) {
        if (paciente == null) {
            return new PacienteRadiologiaDto(null, null, null, null, null, null, null).normalizado();
        }
        return paciente.normalizado();
    }

    private String modalidadDesdeNombre(String nombre) {
        String n = nombre == null ? "" : nombre.toLowerCase();
        if (n.contains("tac") || n.contains("tomografia")) return "Tomografia (TAC)";
        if (n.contains("resonancia") || n.contains("rmn")) return "Resonancia (RMN)";
        if (n.contains("eco")) return "Ecografia";
        if (n.contains("mamo")) return "Mamografia";
        return "Radiografia";
    }

    private String regionDesdeNombre(String nombre) {
        String n = nombre == null ? "" : nombre.toLowerCase();
        if (n.contains("torax")) return "Torax";
        if (n.contains("abd")) return "Abdomen";
        if (n.contains("craneo")) return "Craneo";
        if (n.contains("renal")) return "Abdomen";
        if (n.contains("mama")) return "Mama";
        return "General";
    }

    private List<SerieDicomDto> series(String modalidad) {
        return switch (modalidad) {
            case "Tomografia (TAC)" -> List.of(
                    new SerieDicomDto("s1", "Axial simulado", 120),
                    new SerieDicomDto("s2", "Coronal reconstruido simulado", 64)
            );
            case "Resonancia (RMN)" -> List.of(
                    new SerieDicomDto("s1", "Sagital T1 simulado", 24),
                    new SerieDicomDto("s2", "Axial T2 simulado", 30)
            );
            case "Ecografia" -> List.of(new SerieDicomDto("s1", "Cortes seleccionados simulados", 8));
            case "Mamografia" -> List.of(new SerieDicomDto("s1", "CC / MLO bilateral simulado", 4));
            default -> List.of(new SerieDicomDto("s1", "PA / Lateral simulado", 2));
        };
    }

    private String valor(String actual, String fallback) {
        return actual == null || actual.isBlank() ? fallback : actual;
    }

    private EstudioRadiologia obtenerEntidad(String id) {
        UUID uuid = UUID.fromString(id);
        return estudioRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Estudio radiologico no encontrado: " + id));
    }

    private void aplicarInforme(EstudioRadiologia estudio, InformeRadiologicoDto informe) {
        estudio.setHallazgos(informe.hallazgos());
        estudio.setImpresionDiagnostica(informe.impresionDiagnostica());
        estudio.setRecomendaciones(informe.recomendaciones());
        estudio.setCodigoCie10(informe.codigoCIE10());
        estudio.setDosisRadiacion(informe.dosisRadiacion());
    }

    private EstudioImagenDto toDto(EstudioRadiologia estudio) {
        PacienteRadiologiaDto paciente = new PacienteRadiologiaDto(
                estudio.getPacienteId() == null ? null : estudio.getPacienteId().toString(),
                estudio.getPacienteNombre(),
                estudio.getPacienteApellidos(),
                estudio.getPacienteDni(),
                estudio.getPacienteEdad(),
                estudio.getPacienteSexo(),
                estudio.getNroHistoria()
        );
        List<SerieDicomDto> seriesDto = estudio.getSeries().stream()
                .map(s -> new SerieDicomDto(s.getId().toString(), s.getDescripcion(), s.getNumCortes()))
                .toList();
        InformeRadiologicoDto informe = estudio.getHallazgos() == null ? null : new InformeRadiologicoDto(
                estudio.getHallazgos(),
                estudio.getImpresionDiagnostica(),
                estudio.getRecomendaciones(),
                estudio.getCodigoCie10(),
                estudio.getDosisRadiacion()
        );
        return new EstudioImagenDto(
                estudio.getId().toString(),
                estudio.getNroOrden(),
                paciente,
                estudio.getMedicoSolicitante(),
                estudio.getEspecialidadMedico(),
                estudio.getModalidad(),
                estudio.getTipoEstudio(),
                estudio.getRegionAnatomica(),
                estudio.getFechaSolicitud().format(FECHA),
                estudio.getFechaEstudio() == null ? null : estudio.getFechaEstudio().format(FECHA),
                estudio.getPrioridad(),
                estudio.getEstado(),
                seriesDto,
                informe,
                estudio.getFirmadoEn() == null ? null : estudio.getFirmadoEn().format(FECHA),
                estudio.isEsCritico(),
                estudio.getIndicaciones()
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
