package com.clinica.notificacion.service;

import com.clinica.notificacion.dto.ClinicAlertRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class WebAlertService {

    private static final Logger log = LoggerFactory.getLogger(WebAlertService.class);

    public static final String TOPIC_PREFIX = "/topic/clinic-notifications/";
    private final SimpMessagingTemplate messagingTemplate;

    public WebAlertService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void pushAlert(ClinicAlertRequest alert) {
        if (alert.targetUserId() == null || alert.targetUserId().isBlank()) {
            log.warn("[websocket-alert] Alerta descartada: targetUserId vacio.");
            return;
        }

        Map<String, Object> payload = Map.of(
                "title", alert.title(),
                "message", alert.message(),
                "severity", alert.severity() != null ? alert.severity() : "INFO",
                "timestamp", Instant.now().toString()
        );

        String destination = TOPIC_PREFIX + alert.targetUserId();
        messagingTemplate.convertAndSend(destination, payload);
        log.info("[websocket-alert] Alerta push enviada a {}", destination);
    }
}
