package com.surl.first.global.event.publisher;

import com.surl.first.global.event.event.Event;
import com.surl.first.global.event.event.SUrlEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Profile("kafka")
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisher {
    private final KafkaTemplate<Object, Object> template;

    @Override
    public void publish(Event event){
        template.send(event.getName(), event);
    }
}
