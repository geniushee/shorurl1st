package com.surl.first.domain.member.member.service;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.repository.MemberRepository;
import com.surl.first.global.securityConfig.jwtConfig.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member register(String username, String password, String email, String name) {


        Member newbie = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .name(name)
                .build();

        return memberRepository.save(newbie);
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow(()->new RuntimeException("찾을 수 없음."));
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(()->new RuntimeException("찾을 수 없음."));
    }

    public HttpHeaders checkMemberAndMakeCookie(String username, String password) {
        Optional<Member> opMember = memberRepository.findByUsername(username);

        if(opMember.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }

        if(!passwordEncoder.matches(password, opMember.get().getPassword())){
            throw new IllegalArgumentException("잘못된 회원정보입니다.");
        }

        Member member = opMember.get();
        Long id = member.getId();

        ResponseCookie accessCookie = JwtUtil.encodeAndSetCookie("accessToken",
                10,
                Map.of(
                        "id", id.toString(),
                        "username", member.getUsername(),
                        "authorities", member.getAuthorities()
                )
        );

        ResponseCookie refreshCookie = JwtUtil.encodeAndSetCookie("refreshToken",
                60 * 24 * 7,
                Map.of(
                        "id", member.getId(),
                        "username", member.getUsername()
                )
        );
        member.setRefreshToken(refreshCookie.getValue());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", accessCookie.toString());
        headers.add("Set-Cookie", refreshCookie.toString());
        return headers;
    }

    public HttpHeaders logout() {
        HttpHeaders headers = new HttpHeaders();
        ResponseCookie accessCookie = JwtUtil.removeCrossDomainCookie("accessToken");
        ResponseCookie refreshCookie = JwtUtil.removeCrossDomainCookie("refreshToken");

        headers.add("Set-Cookie", accessCookie.toString());
        headers.add("Set-Cookie", refreshCookie.toString());
        return headers;
    }
}
