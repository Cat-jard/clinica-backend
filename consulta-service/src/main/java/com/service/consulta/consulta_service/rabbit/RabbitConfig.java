package com.service.consulta.consulta_service.rabbit;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "medical.events";
    public static final String TRIAGE_ROUTING_KEY = "consultation.completed";

    @Bean
    public JacksonJsonMessageConverter messageConver() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    TopicExchange medicalExchange() {
        return new TopicExchange(EXCHANGE);
    }
}
