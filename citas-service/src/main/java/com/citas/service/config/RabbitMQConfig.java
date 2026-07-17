package com.citas.service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String CLINIC_EXCHANGE = "clinic.platform.topic";
    
    // Publish Routing Keys
    public static final String APPOINTMENT_EMAIL_ROUTING_KEY = "event.email.appointment";
    public static final String APPOINTMENT_ALERT_ROUTING_KEY = "event.alert.appointment";

    // RPC Request Queues & Routing Keys (Own)
    public static final String CITA_REQUEST_QUEUE = "clinic.request.cita.queue";
    public static final String CITA_REQUEST_ROUTING_KEY = "request.cita.detail";

    public static final String CITAS_PACIENTE_REQUEST_QUEUE = "clinic.request.citas-paciente.queue";
    public static final String CITAS_PACIENTE_REQUEST_ROUTING_KEY = "request.citas.paciente";

    // RPC Request Routing Keys (Others)
    public static final String PACIENTE_REQUEST_ROUTING_KEY = "request.paciente.detail";
    public static final String MEDICO_REQUEST_ROUTING_KEY = "request.medico.detail";

    @Bean
    public TopicExchange clinicExchange() {
        return new TopicExchange(CLINIC_EXCHANGE, true, false);
    }

    @Bean
    public Queue citaRequestQueue() {
        return new Queue(CITA_REQUEST_QUEUE, true, false, false);
    }

    @Bean
    public Binding citaRequestBinding() {
        return BindingBuilder.bind(citaRequestQueue()).to(clinicExchange()).with(CITA_REQUEST_ROUTING_KEY);
    }

    @Bean
    public Queue citasPacienteRequestQueue() {
        return new Queue(CITAS_PACIENTE_REQUEST_QUEUE, true, false, false);
    }

    @Bean
    public Binding citasPacienteRequestBinding() {
        return BindingBuilder.bind(citasPacienteRequestQueue()).to(clinicExchange()).with(CITAS_PACIENTE_REQUEST_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
