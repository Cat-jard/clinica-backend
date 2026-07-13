package com.service.history.historia_clinica_service.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "medical.events";
    public static final String TRIAGE_QUEUE = "history.triage";
    public static final String TRIAGE_ROUTING_KEY = "triage.completed";

    @Bean
    TopicExchange medicalExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Queue triageQueue() {
        return new Queue(TRIAGE_QUEUE);
    }

    @Bean
    Binding triageBinding(Queue triageQueue, TopicExchange medicalExchange) {
        return BindingBuilder.bind(triageQueue).to(medicalExchange).with(TRIAGE_ROUTING_KEY);
    }

    @Bean
    public JacksonJsonMessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
