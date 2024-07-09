package com.surl.first.domain.surl.srulDocument.eventListener;

import com.surl.first.domain.surl.srulDocument.service.SUrlDocumentService;
import com.surl.first.global.event.event.SUrlEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SUrlEventListener {
    private final SUrlDocumentService sUrlDocumentService;

    @EventListener
    @Async
    public void listen(SUrlEvent event){
        sUrlDocumentService.createSUrl(event.getSUrlDto());
    }
}
