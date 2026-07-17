package com.clinica.notificacion.listener;

import com.clinica.notificacion.config.RabbitMQConfig;
import com.clinica.notificacion.dto.AppointmentNotificationRequest;
import com.clinica.notificacion.dto.ClinicAlertRequest;
import com.clinica.notificacion.dto.WelcomeEmailRequest;
import com.clinica.notificacion.service.EmailService;
import com.clinica.notificacion.service.WebAlertService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class NotificationEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationEventConsumer.class);

    private final EmailService emailService;
    private final WebAlertService webAlertService;
    private final ObjectMapper objectMapper;

    public NotificationEventConsumer(EmailService emailService,
                                     WebAlertService webAlertService,
                                     ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.webAlertService = webAlertService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void consumeEmailEvent(Message message,
                                  Channel channel,
                                  @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("[email-event-listener] Mensaje recibido en la cola de email. RoutingKey: {}, Body: {}", routingKey, body);

        try {
            if (routingKey != null && routingKey.contains("welcome")) {
                WelcomeEmailRequest request = objectMapper.readValue(body, WelcomeEmailRequest.class);
                emailService.sendWelcomeEmail(request);
            } else if (routingKey != null && routingKey.contains("appointment")) {
                AppointmentNotificationRequest request = objectMapper.readValue(body, AppointmentNotificationRequest.class);
                emailService.sendAppointmentEmail(request);
            } else {
                log.warn("[email-event-listener] RoutingKey no soportada para emails: {}", routingKey);
            }
            channel.basicAck(tag, false);
        } catch (Exception ex) {
            log.error("[email-event-listener] Error al procesar evento de email. Mandando a DLQ...", ex);
            channel.basicNack(tag, false, false);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.WS_ALERT_QUEUE)
    public void consumeClinicAlert(Message message,
                                   Channel channel,
                                   @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("[ws-event-listener] Alerta de WebSocket recibida. Body: {}", body);

        try {
            ClinicAlertRequest request = objectMapper.readValue(body, ClinicAlertRequest.class);
            webAlertService.pushAlert(request);
            channel.basicAck(tag, false);
        } catch (Exception ex) {
            log.error("[ws-event-listener] Error al procesar alerta de WebSocket. Mandando a DLQ...", ex);
            channel.basicNack(tag, false, false);
        }
    }
}
