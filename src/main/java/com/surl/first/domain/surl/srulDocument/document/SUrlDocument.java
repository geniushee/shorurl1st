package com.surl.first.domain.surl.srulDocument.document;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.surl.surl.dto.SUrlDto;
import com.surl.first.domain.surl.surl.entity.SUrl;
import com.surl.first.global.config.AppConfig;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
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
    private Member member;
    @NotNull
    private String shortUrl;

    public SUrlDocument(SUrl sUrl){
        this.id = sUrl.getId();
        this.createDate = sUrl.getCreateDate();
        this.modifyDate = sUrl.getModifyDate();
        this.origin = sUrl.getOrigin();
        this.title = sUrl.getTitle();
        this.content = sUrl.getContent();
        this.member = sUrl.getMember();
        this.shortUrl = sUrl.getShortUrl();
    }

    public SUrlDocument(SUrlDto sUrlDto) {
        this.id = sUrlDto.getId();
        this.createDate = sUrlDto.getCreateDate();
        this.modifyDate = sUrlDto.getModifyDate();
        this.origin = sUrlDto.getOrigin();
        this.title = sUrlDto.getTitle();
        this.content = sUrlDto.getContent();
        this.member = sUrlDto.getMember();
        this.shortUrl = sUrlDto.getShortUrl();
    }
}
