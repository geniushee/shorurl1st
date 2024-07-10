package com.surl.first.global.event.publisher;

import com.surl.first.global.event.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!kafka")
@Component
@RequiredArgsConstructor
public class SimpleEventPublisher implements EventPublisher{
    private final ApplicationEventPublisher publisher;
    @Override
    public void publish(Event event) {
        publisher.publishEvent(event);
    }
}
