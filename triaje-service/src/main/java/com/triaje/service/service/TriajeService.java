package com.triaje.service.service;

import com.triaje.service.dto.*;
import com.triaje.service.entity.*;
import com.triaje.service.exception.*;
import com.triaje.service.feign.dto.PacienteColaTriajeResponse;
import com.triaje.service.mapper.TriajeMapper;
import com.triaje.service.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TriajeService {

    private final RegistroTriajeRepository registroTriajeRepository;
    private final SignosVitalesRepository signosVitalesRepository;
    private final ObservacionPacienteRepository observacionRepository;
    private final EntradaKardexRepository kardexRepository;
    private final MedicamentoKardexRepository medicamentoRepository;
    private final TriajeMapper mapper;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public List<ColaTriajeResponse> obtenerColaTriaje(LocalDate fecha) {
        try {
            String fechaStr = fecha.format(DateTimeFormatter.ISO_LOCAL_DATE);
            String json = (String) rabbitTemplate.convertSendAndReceive(
                    com.triaje.service.config.RabbitMQConfig.CLINIC_EXCHANGE,
                    com.triaje.service.config.RabbitMQConfig.COLA_TRIAJE_REQUEST_ROUTING_KEY,
                    fechaStr
            );
            if (json == null || "null".equals(json)) return Collections.emptyList();

            List<PacienteColaTriajeResponse> list = objectMapper.readValue(json, new TypeReference<List<PacienteColaTriajeResponse>>() {});
            return list.stream()
                    .filter(p -> "EN_ESPERA".equals(p.getEstado()))
                    .map(p -> new ColaTriajeResponse(
                            p.getId(), p.getPacienteId(), p.getPacienteNombre(),
                            p.getPacienteDni(), p.getTicket(), p.getHoraLlegada(),
                            p.getMedicoNombre(), p.getEspecialidad(), p.getMotivo(), p.getCitaId()))
                    .toList();
        } catch (Exception e) {
            throw new RecepcionServiceException("Error al obtener cola de triaje: " + e.getMessage(), e);
        }
    }

    public void iniciarTriaje(UUID colaId) {
        try {
            Map<String, Object> command = Map.of("id", colaId.toString(), "estado", "EN_TRIAGE");
            String json = objectMapper.writeValueAsString(command);
            rabbitTemplate.convertAndSend(
                    com.triaje.service.config.RabbitMQConfig.CLINIC_EXCHANGE,
                    com.triaje.service.config.RabbitMQConfig.COLA_TRIAJE_COMMAND_ROUTING_KEY,
                    json
            );
        } catch (Exception e) {
            throw new RecepcionServiceException("Error al iniciar triaje: " + e.getMessage(), e);
        }
    }

    public PacienteConCitaDto obtenerDatosPaciente(UUID pacienteId) {
        var paciente = obtenerPacienteDesdeRecepcion(pacienteId);
        String pacienteNombre = paciente.getNombres() + " " + paciente.getApellidoPaterno() + " " + paciente.getApellidoMaterno();
        String pacienteDni = paciente.getNroDocumento();
        String nroHistoria = paciente.getNroHistoria();

        UUID ultimaCitaId = null;
        String medicoNombre = null;
        String especialidadNombre = null;
        Long medicoId = null;
        UUID especialidadId = null;
        boolean conCita = false;

        try {
            String json = (String) rabbitTemplate.convertSendAndReceive(
                    com.triaje.service.config.RabbitMQConfig.CLINIC_EXCHANGE,
                    com.triaje.service.config.RabbitMQConfig.CITAS_PACIENTE_REQUEST_ROUTING_KEY,
                    pacienteId
            );
            if (json != null && !"null".equals(json)) {
                List<com.triaje.service.feign.dto.CitaResponse> list = objectMapper.readValue(json, new TypeReference<List<com.triaje.service.feign.dto.CitaResponse>>() {});
                if (!list.isEmpty()) {
                    var ultimaCita = list.getFirst();
                    ultimaCitaId = ultimaCita.getId();
                    medicoId = ultimaCita.getMedicoId();
                    conCita = true;
                }
            }
        } catch (Exception ignored) {
        }

        return new PacienteConCitaDto(pacienteId, pacienteNombre, pacienteDni, nroHistoria,
                ultimaCitaId, medicoId, medicoNombre, especialidadId, especialidadNombre, conCita);
    }

    public RegistroTriajeResponse registrarTriaje(RegistrarTriajeRequest request) {
        if (request.getSignos().getSpo2() < 90 &&
            ("IV-VERDE".equals(request.getPrioridad()) || "V-AZUL".equals(request.getPrioridad()))) {
            throw new InvalidPrioridadException(
                    "SpO2 menor a 90: no se puede asignar prioridad " + request.getPrioridad());
        }

        var datosPaciente = obtenerDatosPaciente(request.getPacienteId());

        String ticket = generarTicket();
        String destino = calcularDestino(request.getPrioridad());

        RegistroTriaje registro = new RegistroTriaje();
        registro.setPacienteId(request.getPacienteId());
        registro.setPacienteNombre(datosPaciente.pacienteNombre());
        registro.setPacienteDni(datosPaciente.pacienteDni());
        registro.setMedicoId(datosPaciente.medicoId());
        registro.setMedicoNombre(datosPaciente.medicoNombre());
        registro.setEspecialidadId(datosPaciente.especialidadId());
        registro.setEspecialidadNombre(datosPaciente.especialidadNombre());
        registro.setCitaId(datosPaciente.ultimaCitaId());
        if (request.getColaId() != null) registro.setColaId(request.getColaId());
        registro.setTicket(ticket);
        registro.setHoraLlegada(LocalTime.now());
        registro.setFechaTriaje(LocalDate.now());
        registro.setMotivoConsulta(request.getMotivoConsulta());
        registro.setNivelConciencia(request.getNivelConciencia());
        registro.setDolor(request.getDolor());
        registro.setPrioridad(request.getPrioridad());
        registro.setDestino(destino);
        registro.setJustificacion(request.getJustificacion());
        registro.setEnfermeraId(request.getEnfermeraId());
        registro.setTimestamp(LocalDateTime.now());
        registro.setConCita(datosPaciente.conCita());
        registro = registroTriajeRepository.save(registro);

        SignosVitales signos = new SignosVitales();
        signos.setRegistroTriaje(registro);
        signos.setPasSistolica(request.getSignos().getPasSistolica());
        signos.setPasDiastolica(request.getSignos().getPasDiastolica());
        signos.setFrecCardiaca(request.getSignos().getFrecCardiaca());
        signos.setFrecRespiratoria(request.getSignos().getFrecRespiratoria());
        signos.setTemperatura(request.getSignos().getTemperatura());
        signos.setSpo2(request.getSignos().getSpo2());
        signos.setPeso(request.getSignos().getPeso());
        signos.setTalla(request.getSignos().getTalla());
        signos.setImc(calcularImc(request.getSignos().getPeso(), request.getSignos().getTalla()));
        signos = signosVitalesRepository.save(signos);
        registro.setSignosVitales(signos);

        try {
            Map<String, Object> command = Map.of("id", request.getColaId().toString(), "estado", "CLASIFICADO");
            String json = objectMapper.writeValueAsString(command);
            rabbitTemplate.convertAndSend(
                    com.triaje.service.config.RabbitMQConfig.CLINIC_EXCHANGE,
                    com.triaje.service.config.RabbitMQConfig.COLA_TRIAJE_COMMAND_ROUTING_KEY,
                    json
            );
        } catch (Exception e) {
            throw new RecepcionServiceException("Error al actualizar estado de cola: " + e.getMessage(), e);
        }

        if (requiereObservacion(request.getPrioridad())) {
            ObservacionPaciente obs = new ObservacionPaciente();
            obs.setPacienteId(request.getPacienteId());
            obs.setPacienteNombre(datosPaciente.pacienteNombre());
            obs.setMedicoId(datosPaciente.medicoId());
            obs.setMedicoNombre(datosPaciente.medicoNombre());
            obs.setEspecialidad(datosPaciente.especialidadNombre());
            obs.setHoraIngreso(LocalDateTime.now());
            obs.setPrioridad(request.getPrioridad());
            obs.setMotivo(request.getMotivoConsulta());
            obs.setEstado("EN_OBSERVACION");
            observacionRepository.save(obs);
        }

        return toRegistroResponse(registro, signos);
    }

    public void marcarAtendido(UUID pacienteId) {
        var opt = registroTriajeRepository.findTopByPacienteIdOrderByTimestampDesc(pacienteId);
        if (opt.isPresent()) {
            var registro = opt.get();
            registro.setEstado("ATENDIDO");
            registroTriajeRepository.save(registro);

            try {
                Map<String, Object> command = new java.util.HashMap<>();
                command.put("estado", "ATENDIDO");
                if (registro.getColaId() != null) {
                    command.put("id", registro.getColaId().toString());
                } else {
                    command.put("pacienteId", pacienteId.toString());
                }
                String json = objectMapper.writeValueAsString(command);
                rabbitTemplate.convertAndSend(
                        com.triaje.service.config.RabbitMQConfig.CLINIC_EXCHANGE,
                        com.triaje.service.config.RabbitMQConfig.COLA_TRIAJE_COMMAND_ROUTING_KEY,
                        json
                );
            } catch (Exception e) {
                // Log error but don't throw — the local state is already updated
                System.err.println("Error sending RabbitMQ ATENDIDO command: " + e.getMessage());
            }
        }
    }

    public RegistroTriajeResponse obtenerUltimoRegistro(UUID pacienteId) {
        var opt = registroTriajeRepository.findTopByPacienteIdOrderByTimestampDesc(pacienteId);
        if (opt.isEmpty()) throw new ResourceNotFoundException("No hay registro de triaje para el paciente");
        var registro = opt.get();
        return toRegistroResponse(registro, registro.getSignosVitales());
    }

    public Page<RegistroTriajeResponse> listarRegistros(LocalDate fecha, String prioridad, Pageable pageable) {
        Page<RegistroTriaje> page;
        if (fecha != null && prioridad != null) {
            page = registroTriajeRepository.findByFechaTriajeAndPrioridadOrderByTimestampDesc(fecha, prioridad, pageable);
        } else if (fecha != null) {
            page = registroTriajeRepository.findByFechaTriajeOrderByTimestampDesc(fecha, pageable);
        } else if (prioridad != null) {
            page = registroTriajeRepository.findByPrioridadOrderByTimestampDesc(prioridad, pageable);
        } else {
            page = registroTriajeRepository.findAll(pageable);
        }
        return page.map(r -> toRegistroResponse(r, r.getSignosVitales()));
    }

    public EntradaKardexResponse crearKardex(CrearKardexRequest request) {
        var datosPaciente = obtenerDatosPaciente(request.getPacienteId());

        EntradaKardex entrada = new EntradaKardex();
        entrada.setPacienteId(request.getPacienteId());
        entrada.setPacienteNombre(datosPaciente.pacienteNombre());
        entrada.setCitaId(datosPaciente.ultimaCitaId());
        entrada.setFechaHora(LocalDateTime.now());
        entrada.setIngresosHidricos(request.getIngresosHidricos());
        entrada.setEgresosHidricos(request.getEgresosHidricos());
        entrada.setEvolucion(request.getEvolucion());
        entrada.setFirmado(false);

        if (request.getSignos() != null) {
            entrada.setPasSistolica(request.getSignos().getPasSistolica());
            entrada.setPasDiastolica(request.getSignos().getPasDiastolica());
            entrada.setFrecCardiaca(request.getSignos().getFrecCardiaca());
            entrada.setFrecRespiratoria(request.getSignos().getFrecRespiratoria());
            entrada.setTemperatura(request.getSignos().getTemperatura());
            entrada.setSpo2(request.getSignos().getSpo2());
        }

        entrada = kardexRepository.save(entrada);

        if (request.getMedicamentos() != null) {
            for (var medDto : request.getMedicamentos()) {
                MedicamentoKardex med = new MedicamentoKardex();
                med.setEntradaKardex(entrada);
                med.setNombre(medDto.getNombre());
                med.setDosis(medDto.getDosis());
                med.setVia(medDto.getVia());
                med.setHora(medDto.getHora());
                medicamentoRepository.save(med);
                entrada.getMedicamentos().add(med);
            }
        }

        return toKardexResponse(entrada);
    }

    public EntradaKardexResponse firmarKardex(UUID kardexId, FirmarKardexRequest request) {
        EntradaKardex entrada = kardexRepository.findById(kardexId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrada de kardex no encontrada"));

        if (entrada.getFirmado()) {
            throw new BadRequestException("La entrada de kardex ya esta firmada");
        }

        entrada.setFirmado(true);
        entrada.setFirmaBase64(request.getFirmaBase64());
        entrada.setFirmaHash(sha256(request.getFirmaBase64()));
        entrada = kardexRepository.save(entrada);

        return toKardexResponse(entrada);
    }

    public List<EntradaKardexResponse> listarKardex(UUID pacienteId) {
        return kardexRepository.findByPacienteIdOrderByFechaHoraDesc(pacienteId)
                .stream()
                .map(this::toKardexResponse)
                .toList();
    }

    public List<ObservacionPacienteResponse> listarObservaciones() {
        return observacionRepository.findByEstadoOrderByHoraIngresoDesc("EN_OBSERVACION")
                .stream()
                .map(this::toObservacionResponse)
                .toList();
    }

    public ObservacionPacienteResponse darAlta(UUID observacionId, AltaRequest request) {
        ObservacionPaciente obs = observacionRepository.findById(observacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Observacion no encontrada"));

        if (kardexRepository.existsByPacienteIdAndFirmadoFalse(obs.getPacienteId())) {
            throw new KardexPendienteFirmaException(
                    "No se puede dar de alta: hay entradas de kardex pendientes de firma");
        }

        String nuevoEstado = switch (request.getTipoAlta()) {
            case "DOMICILIARIA" -> "ALTA_DOMICILIARIA";
            case "HOSPITALIZACION" -> "HOSPITALIZACION";
            case "TRASLADO" -> "TRASLADO";
            default -> throw new BadRequestException("Tipo de alta no valido");
        };

        obs.setEstado(nuevoEstado);
        obs = observacionRepository.save(obs);
        return toObservacionResponse(obs);
    }

    public EntradaKardexResponse solicitarReevaluacion(UUID kardexId) {
        EntradaKardex entrada = kardexRepository.findById(kardexId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrada de kardex no encontrada"));

        CrearKardexRequest request = new CrearKardexRequest();
        request.setPacienteId(entrada.getPacienteId());
        request.setEvolucion("Reevaluacion solicitada - " + LocalDateTime.now().toString());
        request.setIngresosHidricos(0);
        request.setEgresosHidricos(0);

        return crearKardex(request);
    }

    public Map<String, Object> obtenerKPIs(LocalDate fecha) {
        Map<String, Object> kpis = new LinkedHashMap<>();
        long total = registroTriajeRepository.countByFechaTriaje(fecha);
        kpis.put("totalTriajes", total);
        kpis.put("rojo", registroTriajeRepository.countByFechaAndPrioridad(fecha, "I-ROJO"));
        kpis.put("naranja", registroTriajeRepository.countByFechaAndPrioridad(fecha, "II-NARANJA"));
        kpis.put("amarillo", registroTriajeRepository.countByFechaAndPrioridad(fecha, "III-AMARILLO"));
        kpis.put("verde", registroTriajeRepository.countByFechaAndPrioridad(fecha, "IV-VERDE"));
        kpis.put("azul", registroTriajeRepository.countByFechaAndPrioridad(fecha, "V-AZUL"));
        return kpis;
    }

    public List<Map<String, Object>> distribucionPrioridades(LocalDate fecha) {
        return registroTriajeRepository.countGroupByPrioridad(fecha).stream()
                .map(row -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("prioridad", row[0]);
                    m.put("cantidad", row[1]);
                    return m;
                })
                .toList();
    }

    public List<Map<String, Object>> llegadasPorHora(LocalDate fecha) {
        return registroTriajeRepository.countLlegadasPorHora(fecha).stream()
                .map(row -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("hora", row[0].toString());
                    m.put("cantidad", row[1]);
                    return m;
                })
                .toList();
    }

    public List<Map<String, Object>> topMotivos(LocalDate fecha) {
        return registroTriajeRepository.topMotivos(fecha, PageRequest.of(0, 5)).stream()
                .map(row -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("motivo", row[0]);
                    m.put("cantidad", row[1]);
                    return m;
                })
                .toList();
    }

    public Map<String, Object> spo2Promedio(LocalDate fecha) {
        Double avg = registroTriajeRepository.avgSpO2ByFecha(fecha);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("fecha", fecha.toString());
        result.put("spo2Promedio", avg != null ? Math.round(avg * 10.0) / 10.0 : null);
        return result;
    }

    private String generarTicket() {
        String fechaPart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        long count = registroTriajeRepository.countByFechaTriaje(LocalDate.now()) + 1;
        return "T-" + fechaPart + String.format("%03d", count);
    }

    private String calcularDestino(String prioridad) {
        return switch (prioridad) {
            case "I-ROJO" -> "Reanimacion/UCI";
            case "II-NARANJA" -> "Emergencias";
            case "III-AMARILLO" -> "Consultorio prioritario";
            case "IV-VERDE" -> "Consultorio normal";
            case "V-AZUL" -> "Consulta externa";
            default -> throw new BadRequestException("Prioridad no valida: " + prioridad);
        };
    }

    private boolean requiereObservacion(String prioridad) {
        return "I-ROJO".equals(prioridad) || "II-NARANJA".equals(prioridad) || "III-AMARILLO".equals(prioridad);
    }

    private BigDecimal calcularImc(BigDecimal peso, Integer tallaCm) {
        if (tallaCm == null || tallaCm == 0 || peso == null) return null;
        BigDecimal tallaM = BigDecimal.valueOf(tallaCm / 100.0);
        return peso.divide(tallaM.multiply(tallaM), 1, RoundingMode.HALF_UP);
    }

    private com.triaje.service.feign.dto.PacienteResponse obtenerPacienteDesdeRecepcion(UUID pacienteId) {
        String json = (String) rabbitTemplate.convertSendAndReceive(
                com.triaje.service.config.RabbitMQConfig.CLINIC_EXCHANGE,
                com.triaje.service.config.RabbitMQConfig.PACIENTE_REQUEST_ROUTING_KEY,
                pacienteId
        );
        if (json == null || "null".equals(json)) {
            throw new ResourceNotFoundException("Paciente no encontrado con id " + pacienteId);
        }
        try {
            return objectMapper.readValue(json, com.triaje.service.feign.dto.PacienteResponse.class);
        } catch (Exception e) {
            throw new RecepcionServiceException("Error al obtener paciente: " + e.getMessage(), e);
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

    private RegistroTriajeResponse toRegistroResponse(RegistroTriaje r, SignosVitales s) {
        SignosVitalesDTO signosDTO = null;
        if (s != null) {
            signosDTO = new SignosVitalesDTO();
            signosDTO.setPasSistolica(s.getPasSistolica());
            signosDTO.setPasDiastolica(s.getPasDiastolica());
            signosDTO.setFrecCardiaca(s.getFrecCardiaca());
            signosDTO.setFrecRespiratoria(s.getFrecRespiratoria());
            signosDTO.setTemperatura(s.getTemperatura());
            signosDTO.setSpo2(s.getSpo2());
            signosDTO.setPeso(s.getPeso());
            signosDTO.setTalla(s.getTalla());
        }
        return new RegistroTriajeResponse(
                r.getId(), r.getPacienteId(), r.getPacienteNombre(), r.getPacienteDni(),
                r.getMedicoId(), r.getMedicoNombre(), r.getEspecialidadId(), r.getEspecialidadNombre(),
                r.getCitaId(), r.getTicket(), r.getHoraLlegada(), r.getFechaTriaje(),
                r.getMotivoConsulta(), r.getNivelConciencia(), r.getDolor(), r.getPrioridad(),
                r.getDestino(), r.getJustificacion(), r.getEnfermeraId(), r.getConCita(),
                r.getTimestamp(), r.getEstado(), signosDTO);
    }

    private EntradaKardexResponse toKardexResponse(EntradaKardex e) {
        List<MedicamentoKardexDTO> meds = new ArrayList<>();
        if (e.getMedicamentos() != null) {
            meds = e.getMedicamentos().stream().map(m -> {
                var dto = new MedicamentoKardexDTO();
                dto.setNombre(m.getNombre());
                dto.setDosis(m.getDosis());
                dto.setVia(m.getVia());
                dto.setHora(m.getHora());
                return dto;
            }).toList();
        }
        return new EntradaKardexResponse(
                e.getId(), e.getPacienteId(), e.getPacienteNombre(), e.getCitaId(),
                e.getFechaHora(), e.getPasSistolica(), e.getPasDiastolica(),
                e.getFrecCardiaca(), e.getFrecRespiratoria(), e.getTemperatura(),
                e.getSpo2(), e.getIngresosHidricos(), e.getEgresosHidricos(),
                e.getEvolucion(), e.getFirmado(), e.getFirmadoPor(), e.getCreatedAt(), meds);
    }

    private ObservacionPacienteResponse toObservacionResponse(ObservacionPaciente o) {
        return new ObservacionPacienteResponse(
                o.getId(), o.getPacienteId(), o.getPacienteNombre(),
                o.getMedicoId(), o.getMedicoNombre(), o.getEspecialidad(),
                o.getHoraIngreso(), o.getPrioridad(), o.getMotivo(),
                o.getEstado(), o.getCreatedAt());
    }

    public record PacienteConCitaDto(
            UUID pacienteId, String pacienteNombre, String pacienteDni, String nroHistoria,
            UUID ultimaCitaId, Long medicoId, String medicoNombre,
            UUID especialidadId, String especialidadNombre, boolean conCita) {}
}
