package com.triaje.service.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Publisher {
    private final RabbitTemplate rabbitTemplate;

    public void publish(TriageCompletedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.TRIAGE_ROUTING_KEY,
                event
        );
    }
}
