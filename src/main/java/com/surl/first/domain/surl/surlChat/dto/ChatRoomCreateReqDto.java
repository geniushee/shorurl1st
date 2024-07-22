package com.surl.first.domain.surl.surlChat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomCreateReqDto {
    private Long surlId;
    private String roomName;
}
