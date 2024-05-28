package com.surl.first.domain.member.member.entity;

import com.surl.first.domain.surl.surl.entity.SUrl;
import com.surl.first.global.entity.TimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Member", uniqueConstraints = {
        @UniqueConstraint(
                name = "USERNAME_EMAIL_UNIQUE",
                columnNames = {"username", "email"}
        )
})
public class Member extends TimeEntity {

    private String username;
    private String password;
    private String email;
    private String name;
    private String refreshToken;


    @SuppressWarnings("JpaAttributeTypeInspction")
    public List<? extends GrantedAuthority> getAuthorities() {
        return getAuthoritiesAsStrList()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @SuppressWarnings("JpaAttributeTypeInspction")
    private List<String> getAuthoritiesAsStrList() {
        return List.of("ROLE_MEMBER");
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
