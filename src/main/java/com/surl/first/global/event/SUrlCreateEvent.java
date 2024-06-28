package com.surl.first.global.event;

import com.surl.first.domain.surl.surl.entity.SUrl;
import lombok.Getter;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationEvent;

public class SUrlCreateEvent extends ApplicationEvent {
    @Getter
    private SUrl sUrl;

    public SUrlCreateEvent(Object source, SUrl sUrl) {
        super(source);
        this.sUrl = sUrl;
    }
}
