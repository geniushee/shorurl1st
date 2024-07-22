package com.surl.first.domain.surl.surlChat.entity;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.global.entity.TimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ChatMessage extends TimeEntity {
    private String body;
    @ManyToOne
    private Member writer;
    @ManyToOne
    private SUrlChatRoom room;
}
