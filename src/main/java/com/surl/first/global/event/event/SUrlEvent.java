package com.surl.first.global.event.event;

import com.surl.first.domain.surl.surl.dto.SUrlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SUrlEvent extends Event {
    private String eventType;
    private SUrlDto surlDto;
}
