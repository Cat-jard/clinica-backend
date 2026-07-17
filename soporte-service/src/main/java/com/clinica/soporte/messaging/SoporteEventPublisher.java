package com.clinica.soporte.messaging;

import com.clinica.soporte.config.RabbitMQConfig;
import com.clinica.soporte.dto.ClinicAlertRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SoporteEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(SoporteEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public SoporteEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishTicketAssigned(String targetUserId, String ticketCodigo, String ticketTitulo, String prioridad) {
        if (targetUserId == null || targetUserId.isBlank()) {
            return;
        }
        try {
            String severity = "ALTA".equalsIgnoreCase(prioridad) || "URGENTE".equalsIgnoreCase(prioridad) ? "CRITICAL" : "INFO";
            ClinicAlertRequest event = new ClinicAlertRequest(
                    targetUserId,
                    "Nuevo ticket asignado: " + ticketCodigo,
                    "Se te ha asignado el ticket '" + ticketTitulo + "' con prioridad " + prioridad + ".",
                    severity
            );
            log.info("[soporte-publisher] Publicando alerta de ticket asignado para usuario: {}", targetUserId);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLINIC_EXCHANGE,
                    RabbitMQConfig.TICKET_ALERT_ROUTING_KEY,
                    event
            );
        } catch (Exception ex) {
            log.error("[soporte-publisher] Error al publicar alerta de ticket", ex);
        }
    }
}
