package com.surl.first.domain.member.member.dto;

import com.surl.first.domain.member.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
/*
Json을 다루면서 member의 다양한 속성들이 맵핑이 제대로 되지 않는 문제 발생.
원하는 데이터만 사용하여 이러한 문제를 해결하기 위해 DTO 적용.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String username;
    private String email;
    private String name;

    public MemberDto(Member member){
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.name = member.getName();
    }
}
