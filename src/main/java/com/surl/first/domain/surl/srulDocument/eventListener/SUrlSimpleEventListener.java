package com.surl.first.domain.surl.srulDocument.eventListener;

import com.surl.first.global.event.event.SUrlEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Profile("!kafka")
@Component
@RequiredArgsConstructor
public class SUrlSimpleEventListener {
    private final SUrlDocumentEventHandler handler;

    @EventListener
    @Async
    public void listen(SUrlEvent event){
        handler.handle(event);
    }
}
