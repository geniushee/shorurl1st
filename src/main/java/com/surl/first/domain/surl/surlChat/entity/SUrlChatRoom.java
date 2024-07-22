package com.surl.first.domain.surl.surlChat.entity;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.surl.surl.entity.SUrl;
import com.surl.first.global.entity.TimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SUrlChatRoom extends TimeEntity {
    @ManyToOne
    private SUrl sUrl;
    private String roomName;
    @ManyToOne
    private Member creater;

}
