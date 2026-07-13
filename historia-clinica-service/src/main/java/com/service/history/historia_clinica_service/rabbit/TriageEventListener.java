package com.service.history.historia_clinica_service.rabbit;

import com.service.history.historia_clinica_service.dtos.events.TriageCompletedEvent;
import com.service.history.historia_clinica_service.service.HistoryEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TriageEventListener {
    private final HistoryEventService historyEventService;

    @RabbitListener(queues = RabbitConfig.TRIAGE_QUEUE)
    public void recieve(TriageCompletedEvent event) {
        historyEventService.registerTriageEvent(event);
    }
}
