package com.recepcion.service.config;

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
    public static final String PACIENTE_SYNC_ROUTING_KEY = "event.data.paciente.sync";

    public static final String PACIENTE_REQUEST_QUEUE = "clinic.request.paciente.queue";
    public static final String PACIENTE_REQUEST_ROUTING_KEY = "request.paciente.detail";

    public static final String COLA_TRIAJE_REQUEST_QUEUE = "clinic.request.colatriaje.queue";
    public static final String COLA_TRIAJE_REQUEST_ROUTING_KEY = "request.cola.triaje";

    public static final String COLA_TRIAJE_COMMAND_QUEUE = "clinic.command.colatriaje.queue";
    public static final String COLA_TRIAJE_COMMAND_ROUTING_KEY = "command.cola.actualizar-estado";

    @Bean
    public TopicExchange clinicExchange() {
        return new TopicExchange(CLINIC_EXCHANGE, true, false);
    }

    @Bean
    public Queue pacienteRequestQueue() {
        return new Queue(PACIENTE_REQUEST_QUEUE, true, false, false);
    }

    @Bean
    public Binding pacienteRequestBinding() {
        return BindingBuilder.bind(pacienteRequestQueue()).to(clinicExchange()).with(PACIENTE_REQUEST_ROUTING_KEY);
    }

    @Bean
    public Queue colaTriajeRequestQueue() {
        return new Queue(COLA_TRIAJE_REQUEST_QUEUE, true, false, false);
    }

    @Bean
    public Binding colaTriajeRequestBinding() {
        return BindingBuilder.bind(colaTriajeRequestQueue()).to(clinicExchange()).with(COLA_TRIAJE_REQUEST_ROUTING_KEY);
    }

    @Bean
    public Queue colaTriajeCommandQueue() {
        return new Queue(COLA_TRIAJE_COMMAND_QUEUE, true, false, false);
    }

    @Bean
    public Binding colaTriajeCommandBinding() {
        return BindingBuilder.bind(colaTriajeCommandQueue()).to(clinicExchange()).with(COLA_TRIAJE_COMMAND_ROUTING_KEY);
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
