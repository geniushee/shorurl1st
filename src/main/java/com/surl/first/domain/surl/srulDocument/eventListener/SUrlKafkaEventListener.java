package com.surl.first.domain.surl.srulDocument.eventListener;

import com.surl.first.global.event.event.SUrlEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Profile("kafka")
@Component
@RequiredArgsConstructor
public class SUrlKafkaEventListener {
    private final SUrlDocumentEventHandler handler;

    // Json을 SUrlEvent로 변환하는 과정에서 에러 발생. Json이 SUrlEvent의 각 속성에 제대로 맵핑이 되지 않아서 생기는 문제였음.
    // Json을 다룰 때, 이러한 문제가 매우 빈번히 발생하므로 주의해야한다.
    // 결국 문제는 Memeber class에 있었던 것으로 생각되며, MemberDto를 추가하여 필요한 데이터만 사용하도록 변경.
    @KafkaListener(topics = "SUrlEvent", groupId = "1")
    public void consumeSUrlEvent(SUrlEvent event){
        handler.handle(event);
    }

    @KafkaListener(topics = "SUrlEvent.DLT", groupId = "1")
    public void consumeSUrlEventDTL(byte[] in){
        String message = new String(in);
        System.out.println("SUrlEvent DLT - failed message : " + message);
    }
}
