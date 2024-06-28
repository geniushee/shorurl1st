package com.surl.first.domain.surl.surl.dto;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.surl.surl.entity.SUrl;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Data
public class SUrlDto {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String origin;
    private String title;
    private String content;
    private Member member;
    private String shortUrl;

    public SUrlDto(SUrl sUrl){
        this.id = sUrl.getId();
        this.createDate = sUrl.getCreateDate();
        this.modifyDate = sUrl.getModifyDate();
        this.origin = sUrl.getOrigin();
        this.title = sUrl.getTitle();
        this.content = sUrl.getContent();
        this.member = sUrl.getMember();
        this.shortUrl = sUrl.getShortUrl();
    }

}
