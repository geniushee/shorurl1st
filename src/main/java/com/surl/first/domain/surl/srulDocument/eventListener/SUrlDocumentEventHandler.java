package com.surl.first.domain.surl.srulDocument.eventListener;

import com.surl.first.domain.surl.srulDocument.service.SUrlDocumentService;
import com.surl.first.global.event.event.SUrlEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SUrlDocumentEventHandler {
    private final SUrlDocumentService service;

    public void handle(SUrlEvent event){
        switch (event.getEventType()){
            case "create" -> service.createSUrl(event.getSUrlDto());
            case "modify" -> service.modify(event.getSUrlDto());
            case "delete" -> service.deleteById(event.getSUrlDto());
        }
    }
}
