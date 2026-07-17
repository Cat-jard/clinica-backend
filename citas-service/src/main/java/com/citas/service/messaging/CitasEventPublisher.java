package com.citas.service.messaging;

import com.citas.service.config.RabbitMQConfig;
import com.citas.service.dto.AppointmentNotificationRequest;
import com.citas.service.dto.ClinicAlertRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class CitasEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(CitasEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public CitasEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishAppointmentCreated(String patientEmail, String patientName, String doctorId,
                                          String doctorName, String specialty, String date, String time) {
        try {
            AppointmentNotificationRequest emailEvent = new AppointmentNotificationRequest(
                    patientEmail, patientName, doctorName, specialty, date, time, "CREAR"
            );
            log.info("[citas-publisher] Publicando correo de cita creada para: {}", patientEmail);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLINIC_EXCHANGE,
                    RabbitMQConfig.APPOINTMENT_EMAIL_ROUTING_KEY,
                    emailEvent
            );

            ClinicAlertRequest alertEvent = new ClinicAlertRequest(
                    doctorId,
                    "Nueva cita programada",
                    "Tienes una nueva cita medica programada con el paciente " + patientName + " el " + date + " a las " + time + ".",
                    "INFO"
            );
            log.info("[citas-publisher] Publicando alerta push WebSocket para medico ID: {}", doctorId);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLINIC_EXCHANGE,
                    RabbitMQConfig.APPOINTMENT_ALERT_ROUTING_KEY,
                    alertEvent
            );
        } catch (Exception ex) {
            log.error("[citas-publisher] Error al publicar cita creada", ex);
        }
    }

    public void publishAppointmentCancelled(String patientEmail, String patientName, String doctorId,
                                            String doctorName, String specialty, String date, String time) {
        try {
            AppointmentNotificationRequest emailEvent = new AppointmentNotificationRequest(
                    patientEmail, patientName, doctorName, specialty, date, time, "CANCELAR"
            );
            log.info("[citas-publisher] Publicando correo de cita cancelada para: {}", patientEmail);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLINIC_EXCHANGE,
                    RabbitMQConfig.APPOINTMENT_EMAIL_ROUTING_KEY,
                    emailEvent
            );

            ClinicAlertRequest alertEvent = new ClinicAlertRequest(
                    doctorId,
                    "Cita cancelada",
                    "La cita programada con el paciente " + patientName + " el " + date + " a las " + time + " ha sido cancelada.",
                    "WARNING"
            );
            log.info("[citas-publisher] Publicando alerta push WebSocket para medico ID: {}", doctorId);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLINIC_EXCHANGE,
                    RabbitMQConfig.APPOINTMENT_ALERT_ROUTING_KEY,
                    alertEvent
            );
        } catch (Exception ex) {
            log.error("[citas-publisher] Error al publicar cita cancelada", ex);
        }
    }
}
