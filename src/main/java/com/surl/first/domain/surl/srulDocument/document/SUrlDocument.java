package com.surl.first.domain.surl.srulDocument.document;

import com.surl.first.domain.member.member.dto.MemberDto;
import com.surl.first.domain.surl.surl.dto.SUrlDto;
import com.surl.first.domain.surl.surl.entity.SUrl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SUrlDocument {
    @NotNull
    private Long id;
    @NotNull
    private LocalDateTime createDate;
    @NotNull
    private LocalDateTime modifyDate;
    @NotNull
    private String origin;
    private String title;
    private String content;
    @NotNull
    private MemberDto memberDto;
    @NotNull
    private String shortUrl;

    public SUrlDocument(SUrl sUrl){
        this.id = sUrl.getId();
        this.createDate = sUrl.getCreateDate();
        this.modifyDate = sUrl.getModifyDate();
        this.origin = sUrl.getOrigin();
        this.title = sUrl.getTitle();
        this.content = sUrl.getContent();
        this.memberDto = new MemberDto(sUrl.getMember());
        this.shortUrl = sUrl.getShortUrl();
    }

    public SUrlDocument(SUrlDto sUrlDto) {
        this.id = sUrlDto.getId();
        this.createDate = sUrlDto.getCreateDate();
        this.modifyDate = sUrlDto.getModifyDate();
        this.origin = sUrlDto.getOrigin();
        this.title = sUrlDto.getTitle();
        this.content = sUrlDto.getContent();
        this.memberDto = sUrlDto.getMemberDto();
        this.shortUrl = sUrlDto.getShortUrl();
    }
}
