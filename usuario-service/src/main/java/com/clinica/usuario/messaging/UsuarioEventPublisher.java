package com.clinica.usuario.messaging;

import com.clinica.usuario.config.RabbitMQConfig;
import com.clinica.usuario.dto.MedicoSyncEvent;
import com.clinica.usuario.dto.WelcomeEmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(UsuarioEventPublisher.class);

    public static final String CLINIC_EXCHANGE = "clinic.platform.topic";
    public static final String WELCOME_ROUTING_KEY = "event.email.welcome";
    public static final String MEDICO_SYNC_ROUTING_KEY = "event.data.medico.sync";

    private final RabbitTemplate rabbitTemplate;

    public UsuarioEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishWelcomeEmail(String dni, String email, String nombre, String apellidos) {
        try {
            WelcomeEmailRequest event = new WelcomeEmailRequest(dni, email, nombre, apellidos);
            log.info("[event-publisher] Publicando evento de bienvenida para DNI: {}, Email: {}", dni, email);
            rabbitTemplate.convertAndSend(
                    CLINIC_EXCHANGE,
                    WELCOME_ROUTING_KEY,
                    event
            );
        } catch (Exception ex) {
            log.error("[event-publisher] Fallo al publicar evento de bienvenida para {}", email, ex);
        }
    }

    public void publishMedicoSync(MedicoSyncEvent event) {
        try {
            log.info("[event-publisher] Publicando evento de sincronizacion de medico: {} {}", event.nombre(), event.apellidos());
            rabbitTemplate.convertAndSend(
                    CLINIC_EXCHANGE,
                    MEDICO_SYNC_ROUTING_KEY,
                    event
            );
        } catch (Exception ex) {
            log.error("[event-publisher] Fallo al publicar evento de sincronizacion de medico", ex);
        }
    }
}
