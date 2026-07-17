package com.clinica.radiologia.listener;

import com.clinica.radiologia.config.RabbitMQConfig;
import com.clinica.radiologia.dto.RadiologiaSolicitudRequest;
import com.clinica.radiologia.service.RadiologiaService;
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
public class RadiologiaEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(RadiologiaEventConsumer.class);

    private final RadiologiaService radiologiaService;
    private final ObjectMapper objectMapper;

    public RadiologiaEventConsumer(RadiologiaService radiologiaService, ObjectMapper objectMapper) {
        this.radiologiaService = radiologiaService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.STUDY_QUEUE)
    public void consumeStudyRequest(Message message,
                                    Channel channel,
                                    @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("[radiologia-consumer] Mensaje de solicitud de estudio recibido. Body: {}", body);

        try {
            RadiologiaSolicitudRequest request = objectMapper.readValue(body, RadiologiaSolicitudRequest.class);
            radiologiaService.crearDesdeSolicitud(request);
            log.info("[radiologia-consumer] Estudio creado exitosamente para orden.");
            channel.basicAck(tag, false);
        } catch (Exception ex) {
            log.error("[radiologia-consumer] Error al procesar solicitud de estudio. Mandando a DLQ...", ex);
            channel.basicNack(tag, false, false);
        }
    }
}
