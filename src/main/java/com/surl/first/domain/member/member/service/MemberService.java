package com.surl.first.domain.member.member.service;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.repository.MemberRepository;
import com.surl.first.domain.surl.surl.service.SUrlService;
import com.surl.first.global.securityConfig.jwtConfig.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional
    public HttpHeaders checkMemberAndMakeCookie(String username, String rawPassword) {
        Optional<Member> opMember = memberRepository.findByUsername(username);

        if(opMember.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }

        if(!passwordEncoder.matches(rawPassword, opMember.get().getPassword())){
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

    public HttpHeaders logout(HttpServletRequest request, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        ResponseCookie accessCookie = JwtUtil.removeCrossDomainCookie(request, response,"accessToken");
        ResponseCookie refreshCookie = JwtUtil.removeCrossDomainCookie(request, response,"refreshToken");

        headers.add("Set-Cookie", accessCookie.toString());
        headers.add("Set-Cookie", refreshCookie.toString());
        return headers;
    }

    public Member findByRefreshToken(String refreshToken) {
        return memberRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member whenSocialLogin(String providerTypeCode, String username, String name) {
        //todo optional로 변경할것!
        try{
            Member member = findByUsername(username);
            return member;
        }catch(Exception e){
            return register(username, "", "", name);
        }
    }

    public HttpHeaders makeCookieAsOAuth(Long id) {
        Optional<Member> opMember = memberRepository.findById(id);

        Member member = opMember.get();

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

    public List<Member> findTop10Recently() {
        return memberRepository.findTop10ByOrderByCreateDateDesc();
    }

    public Member checkMember(Long userId, String rawPassword) {
        Member member = findById(userId);
        if(!passwordEncoder.matches(rawPassword,member.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return member;
    }

    @Transactional
    public void withdrawal(Member member) {
        memberRepository.delete(member);
    }
}
