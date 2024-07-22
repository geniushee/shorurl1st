package com.surl.first.domain.surl.surlChat.dto;

import com.surl.first.domain.member.member.dto.MemberDto;
import com.surl.first.domain.surl.surlChat.entity.SUrlChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SUrlChatRoomDto {
    private Long roomId;
    private String roomName;
    private MemberDto creater;

    public SUrlChatRoomDto(SUrlChatRoom room){
        roomId = room.getId();
        roomName = room.getRoomName();
        creater = new MemberDto(room.getCreater());
    }
}
