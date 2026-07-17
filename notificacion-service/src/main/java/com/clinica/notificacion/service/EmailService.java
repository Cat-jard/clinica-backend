package com.clinica.notificacion.service;

import com.clinica.notificacion.config.NotificationProperties;
import com.clinica.notificacion.dto.AppointmentNotificationRequest;
import com.clinica.notificacion.dto.WelcomeEmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final NotificationProperties properties;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.host:}")
    private String mailHost;

    public EmailService(NotificationProperties properties) {
        this.properties = properties;
    }

    @Async
    public void sendWelcomeEmail(WelcomeEmailRequest request) {
        log.info("[email-welcome] Preparando correo de bienvenida para: {}", request.email());
        if (mailSender == null || mailHost == null || mailHost.isBlank() || "localhost".equals(mailHost)) {
            log.warn("[email-welcome] SMTP no configurado de verdad. Simulando envio a {} con exito.", request.email());
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(properties.email().from(), properties.email().fromName());
            helper.setTo(request.email());
            helper.setSubject("¡Te damos la bienvenida a Clinica Medica SIHCE!");
            helper.setText(buildWelcomeHtml(request), true);
            mailSender.send(message);
            log.info("[email-welcome] Correo enviado a {}", request.email());
        } catch (MessagingException | UnsupportedEncodingException ex) {
            log.error("[email-welcome] Error al enviar a {}", request.email(), ex);
            throw new RuntimeException("Error al enviar email", ex);
        }
    }

    @Async
    public void sendAppointmentEmail(AppointmentNotificationRequest request) {
        log.info("[email-appointment] Preparando correo de cita ({}) para: {}", request.action(), request.patientEmail());
        if (mailSender == null || mailHost == null || mailHost.isBlank() || "localhost".equals(mailHost)) {
            log.warn("[email-appointment] SMTP no configurado de verdad. Simulando envio a {} con exito.", request.patientEmail());
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(properties.email().from(), properties.email().fromName());
            helper.setTo(request.patientEmail());

            String subject = "CREAR".equalsIgnoreCase(request.action())
                    ? "Confirmacion de Cita Medica — Clinica SIHCE"
                    : "Cancelacion de Cita Medica — Clinica SIHCE";

            helper.setSubject(subject);
            helper.setText(buildAppointmentHtml(request), true);
            mailSender.send(message);
            log.info("[email-appointment] Correo enviado a {}", request.patientEmail());
        } catch (MessagingException | UnsupportedEncodingException ex) {
            log.error("[email-appointment] Error al enviar a {}", request.patientEmail(), ex);
            throw new RuntimeException("Error al enviar email", ex);
        }
    }

    private String buildWelcomeHtml(WelcomeEmailRequest req) {
        return """
                <!doctype html>
                <html>
                  <body style="font-family: Arial, sans-serif; background-color: #f4f7f6; padding: 20px; color: #33;">
                    <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; padding: 30px; box-shadow: 0 4px 8px rgba(0,0,0,0.05);">
                      <h2 style="color: #0d8390; margin-top: 0;">🏥 ¡Bienvenido/a a la Clinica Medica SIHCE!</h2>
                      <p>Estimado/a <b>%s %s</b>,</p>
                      <p>Tu cuenta ha sido creada exitosamente en nuestra plataforma de salud. A partir de ahora podras agendar tus citas, ver tu historial clinico y recibir tus resultados en linea.</p>
                      <table style="width: 100%%; border-collapse: collapse; margin: 20px 0; background-color: #fafafa; border-radius: 6px; padding: 15px;">
                        <tr>
                          <td style="padding: 10px; font-weight: bold; color: #555555;">DNI de acceso:</td>
                          <td style="padding: 10px; color: #333333;">%s</td>
                        </tr>
                        <tr>
                          <td style="padding: 10px; font-weight: bold; color: #555555;">Correo registrado:</td>
                          <td style="padding: 10px; color: #333333;">%s</td>
                        </tr>
                      </table>
                      <p style="font-size: 13px; color: #777777;">Si no has solicitado este registro, por favor ponte en contacto con nuestro equipo de TI de inmediato.</p>
                      <hr style="border: none; border-top: 1px solid #e0e0e0; margin: 25px 0;">
                      <p style="font-size: 11px; color: #999999;">Clinica Medica SIHCE · Av. Salud 123 · Tel: (01) 456-7890</p>
                    </div>
                  </body>
                </html>
                """.formatted(req.nombre(), req.apellidos(), req.dni(), req.email());
    }

    private String buildAppointmentHtml(AppointmentNotificationRequest req) {
        boolean esCreacion = "CREAR".equalsIgnoreCase(req.action());
        String color = esCreacion ? "#0d8390" : "#d9534f";
        String titulo = esCreacion ? "🏥 ¡Tu cita ha sido programada!" : "❌ Tu cita ha sido cancelada";
        String introduccion = esCreacion
                ? "Confirmamos la programacion de tu cita medica con los siguientes detalles:"
                : "Te informamos que tu cita medica programada ha sido cancelada:";

        return """
                <!doctype html>
                <html>
                  <body style="font-family: Arial, sans-serif; background-color: #f4f7f6; padding: 20px; color: #33;">
                    <div style="max-width: 600px; margin: auto; background: #ffffff; border-radius: 8px; padding: 30px; box-shadow: 0 4px 8px rgba(0,0,0,0.05);">
                      <h2 style="color: %s; margin-top: 0;">%s</h2>
                      <p>Estimado/a <b>%s</b>,</p>
                      <p>%s</p>
                      <table style="width: 100%%; border-collapse: collapse; margin: 20px 0;">
                        <tr>
                          <td style="padding: 8px 0; font-weight: bold; color: #555555;">Especialidad:</td>
                          <td style="padding: 8px 0; color: #333333;">%s</td>
                        </tr>
                        <tr>
                          <td style="padding: 8px 0; font-weight: bold; color: #555555;">Medico:</td>
                          <td style="padding: 8px 0; color: #333333;">%s</td>
                        </tr>
                        <tr>
                          <td style="padding: 8px 0; font-weight: bold; color: #555555;">Fecha:</td>
                          <td style="padding: 8px 0; color: #333333;">%s</td>
                        </tr>
                        <tr>
                          <td style="padding: 8px 0; font-weight: bold; color: #555555;">Hora:</td>
                          <td style="padding: 8px 0; color: #333333;">%s</td>
                        </tr>
                      </table>
                      %s
                      <hr style="border: none; border-top: 1px solid #e0e0e0; margin: 25px 0;">
                      <p style="font-size: 11px; color: #999999;">Clinica Medica SIHCE · Av. Salud 123 · Tel: (01) 456-7890</p>
                    </div>
                  </body>
                </html>
                """.formatted(
                        color, titulo, req.patientName(), introduccion,
                        req.specialty(), req.doctorName(), req.date(), req.time(),
                        esCreacion ? "<p style=\"font-size: 13px; color: #777777;\">Si deseas reprogramar o cancelar, por favor contactanos con 24 horas de anticipacion.</p>" : ""
                );
    }
}
