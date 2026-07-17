package com.clinica.radiologia.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String CLINIC_EXCHANGE = "clinic.platform.topic";
    public static final String CLINIC_DLX = "clinic.platform.dlx";

    public static final String STUDY_QUEUE = "clinic.radiology.study.queue";
    public static final String STUDY_DLQ = "clinic.radiology.study.dlq";

    public static final String STUDY_REQUEST_ROUTING_KEY = "event.radiology.study.request";

    @Bean
    public TopicExchange clinicExchange() {
        return new TopicExchange(CLINIC_EXCHANGE, true, false);
    }

    @Bean
    public TopicExchange clinicDlxExchange() {
        return new TopicExchange(CLINIC_DLX, true, false);
    }

    @Bean
    public Queue studyQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", CLINIC_DLX);
        args.put("x-dead-letter-routing-key", STUDY_REQUEST_ROUTING_KEY);
        return new Queue(STUDY_QUEUE, true, false, false, args);
    }

    @Bean
    public Queue studyDlq() {
        return new Queue(STUDY_DLQ, true, false, false);
    }

    @Bean
    public Binding studyBinding() {
        return BindingBuilder.bind(studyQueue()).to(clinicExchange()).with(STUDY_REQUEST_ROUTING_KEY);
    }

    @Bean
    public Binding studyDlqBinding() {
        return BindingBuilder.bind(studyDlq()).to(clinicDlxExchange()).with(STUDY_REQUEST_ROUTING_KEY);
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
