package com.clinica.usuario.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String CLINIC_EXCHANGE = "clinic.platform.topic";
    public static final String WELCOME_ROUTING_KEY = "event.email.welcome";
    
    public static final String MEDICO_REQUEST_QUEUE = "clinic.request.medico.queue";
    public static final String MEDICO_REQUEST_ROUTING_KEY = "request.medico.detail";

    @Bean
    public TopicExchange clinicExchange() {
        return new TopicExchange(CLINIC_EXCHANGE, true, false);
    }

    @Bean
    public Queue medicoRequestQueue() {
        return new Queue(MEDICO_REQUEST_QUEUE, true, false, false);
    }

    @Bean
    public Binding medicoRequestBinding() {
        return BindingBuilder.bind(medicoRequestQueue()).to(clinicExchange()).with(MEDICO_REQUEST_ROUTING_KEY);
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
