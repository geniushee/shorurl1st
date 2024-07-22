package com.surl.first.domain.surl.surlChat.dto;

import com.surl.first.domain.member.member.dto.MemberDto;
import com.surl.first.domain.surl.surlChat.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    private Long id;
    private String body;
    private MemberDto writer;
    private SUrlChatRoomDto room;

    public ChatMessageDto(ChatMessage message){
        id = message.getId();
        body = message.getBody();
        writer = new MemberDto(message.getWriter());
        room = new SUrlChatRoomDto(message.getRoom());
    }
}
