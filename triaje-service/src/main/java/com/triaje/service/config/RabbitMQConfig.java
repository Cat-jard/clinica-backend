package com.triaje.service.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String CLINIC_EXCHANGE = "clinic.platform.topic";

    // RPC Routing Keys (Others)
    public static final String PACIENTE_REQUEST_ROUTING_KEY = "request.paciente.detail";
    public static final String COLA_TRIAJE_REQUEST_ROUTING_KEY = "request.cola.triaje";
    public static final String COLA_TRIAJE_COMMAND_ROUTING_KEY = "command.cola.actualizar-estado";
    
    public static final String CITAS_PACIENTE_REQUEST_ROUTING_KEY = "request.citas.paciente";

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
