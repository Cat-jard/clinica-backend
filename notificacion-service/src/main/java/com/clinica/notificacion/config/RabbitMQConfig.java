package com.clinica.notificacion.config;

import org.springframework.amqp.core.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String CLINIC_EXCHANGE = "clinic.platform.topic";
    public static final String CLINIC_DLX = "clinic.platform.dlx";

    // Main Queues
    public static final String EMAIL_QUEUE = "clinic.notification.email.queue";
    public static final String WS_ALERT_QUEUE = "clinic.notification.ws.alert.queue";

    // Dead Letter Queues
    public static final String EMAIL_DLQ = "clinic.notification.email.dlq";
    public static final String WS_ALERT_DLQ = "clinic.notification.ws.alert.dlq";



    @Bean
    public TopicExchange clinicExchange() {
        return new TopicExchange(CLINIC_EXCHANGE, true, false);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(CLINIC_DLX, true, false);
    }

    // Config for Email Queue and its DLQ
    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(EMAIL_QUEUE)
                .withArgument("x-dead-letter-exchange", CLINIC_DLX)
                .withArgument("x-dead-letter-routing-key", EMAIL_DLQ)
                .build();
    }

    @Bean
    public Queue emailDeadLetterQueue() {
        return QueueBuilder.durable(EMAIL_DLQ).build();
    }

    // Config for WebSocket Queue and its DLQ
    @Bean
    public Queue wsAlertQueue() {
        return QueueBuilder.durable(WS_ALERT_QUEUE)
                .withArgument("x-dead-letter-exchange", CLINIC_DLX)
                .withArgument("x-dead-letter-routing-key", WS_ALERT_DLQ)
                .build();
    }

    @Bean
    public Queue wsAlertDeadLetterQueue() {
        return QueueBuilder.durable(WS_ALERT_DLQ).build();
    }

    // Bindings for Main Queues
    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange clinicExchange) {
        return BindingBuilder.bind(emailQueue).to(clinicExchange).with("event.email.#");
    }

    @Bean
    public Binding wsAlertBinding(Queue wsAlertQueue, TopicExchange clinicExchange) {
        return BindingBuilder.bind(wsAlertQueue).to(clinicExchange).with("event.alert.#");
    }

    // Bindings for DLQs
    @Bean
    public Binding emailDlqBinding(Queue emailDeadLetterQueue, TopicExchange deadLetterExchange) {
        return BindingBuilder.bind(emailDeadLetterQueue).to(deadLetterExchange).with(EMAIL_DLQ);
    }

    @Bean
    public Binding wsAlertDlqBinding(Queue wsAlertDeadLetterQueue, TopicExchange deadLetterExchange) {
        return BindingBuilder.bind(wsAlertDeadLetterQueue).to(deadLetterExchange).with(WS_ALERT_DLQ);
    }
}
