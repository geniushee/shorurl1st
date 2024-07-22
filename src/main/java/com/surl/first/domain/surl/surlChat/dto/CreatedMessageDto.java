package com.surl.first.domain.surl.surlChat.dto;

import com.surl.first.domain.member.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatedMessageDto {
    private String body;
    private String writer;
}
