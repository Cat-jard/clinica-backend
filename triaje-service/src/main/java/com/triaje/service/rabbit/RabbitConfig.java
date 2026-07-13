package com.triaje.service.rabbit;

import ch.qos.logback.classic.pattern.MessageConverter;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "medical.events";
    public static final String TRIAGE_ROUTING_KEY = "triage.completed";

    @Bean
    public JacksonJsonMessageConverter messageConver() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    TopicExchange medicalExchange() {
        return new TopicExchange(EXCHANGE);
    }
}
