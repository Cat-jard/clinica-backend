package com.clinica.usuario.messaging;

import com.clinica.usuario.config.RabbitMQConfig;
import com.clinica.usuario.domain.Rol;
import com.clinica.usuario.dto.UsuarioResponse;
import com.clinica.usuario.mapper.UsuarioMapper;
import com.clinica.usuario.repository.UsuarioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MedicoRequestListener {

    private static final Logger log = LoggerFactory.getLogger(MedicoRequestListener.class);

    private final UsuarioRepository repositorio;
    private final UsuarioMapper mapper;
    private final ObjectMapper objectMapper;

    public MedicoRequestListener(UsuarioRepository repositorio, UsuarioMapper mapper, ObjectMapper objectMapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.MEDICO_REQUEST_QUEUE)
    public String handleObtenerMedico(Long id) throws JsonProcessingException {
        log.info("[medico-listener] Peticion RPC recibida para medico ID: {}", id);
        UsuarioResponse medico = repositorio.findById(id)
                .filter(u -> u.getRol() == Rol.MEDICO)
                .map(mapper::aResponse)
                .orElse(null);
        return objectMapper.writeValueAsString(medico);
    }
}
