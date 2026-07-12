package com.triaje.service.rabbit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "medical.events";
    public static final String TRIAGE_ROUTING_KEY = "triage.completed";

    @Bean
    TopicExchange medicalExchange() {
        return new TopicExchange(EXCHANGE);
    }
}
