package com.surl.first.domain.surl.surl.dto;

import com.surl.first.domain.member.member.dto.MemberDto;
import com.surl.first.domain.surl.surl.entity.SUrl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SUrlDto {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String origin;
    private String title;
    private String content;
    private MemberDto memberDto;
    private String shortUrl;

    public SUrlDto(SUrl sUrl){
        this.id = sUrl.getId();
        this.createDate = sUrl.getCreateDate();
        this.modifyDate = sUrl.getModifyDate();
        this.origin = sUrl.getOrigin();
        this.title = sUrl.getTitle();
        this.content = sUrl.getContent();
        this.memberDto = new MemberDto(sUrl.getMember());
        this.shortUrl = sUrl.getShortUrl();
    }

}
