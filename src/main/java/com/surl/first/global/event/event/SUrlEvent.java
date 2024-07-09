package com.surl.first.global.event.event;

import com.surl.first.domain.surl.surl.dto.SUrlDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SUrlEvent extends Event {
    private String eventType;
    private SUrlDto sUrlDto;
}
