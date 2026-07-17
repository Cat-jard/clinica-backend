package com.recepcion.service.messaging;

import com.recepcion.service.config.RabbitMQConfig;
import com.recepcion.service.dto.PacienteSyncEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RecepcionEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(RecepcionEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public RecepcionEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishPacienteSync(PacienteSyncEvent event) {
        try {
            log.info("[recepcion-publisher] Publicando evento de sincronizacion para paciente: {} {}", 
                    event.nombres(), event.apellidoPaterno());
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLINIC_EXCHANGE,
                    RabbitMQConfig.PACIENTE_SYNC_ROUTING_KEY,
                    event
            );
        } catch (Exception ex) {
            log.error("[recepcion-publisher] Error al publicar evento de sincronizacion de paciente", ex);
        }
    }
}
