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
            case "create" -> service.createSUrl(event.getSurlDto());
            case "modify" -> service.modify(event.getSurlDto());
            case "delete" -> service.deleteById(event.getSurlDto());
        }
    }
}
