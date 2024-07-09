package com.surl.first.global.event.publisher;

import com.surl.first.global.event.event.Event;
import org.springframework.stereotype.Component;


public interface EventPublisher {
    void publish(Event event);
}
