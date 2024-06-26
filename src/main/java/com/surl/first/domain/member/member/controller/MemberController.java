package com.surl.first.domain.member.member.controller;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.service.MemberService;
import com.surl.first.global.securityConfig.SecurityUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    public record MemberRegisterDto(@NotEmpty String username, @NotEmpty String password, @NotEmpty String email,
                                    @NotEmpty String name) {
    }

    public record MemberLoginDto(@NotEmpty String username, @NotEmpty String password) {
    }


    @PostMapping("/register")
    public ResponseEntity<?> memberRegister(@RequestBody MemberRegisterDto dto) {
        Member member = memberService.register(dto.username, dto.password, dto.email, dto.name);
        return ResponseEntity.ok(member);
    }

    @PostMapping("/login")
    public ResponseEntity<?> memberLogin(@RequestBody MemberLoginDto dto) {
        ResponseEntity res;

        HttpHeaders headersWithCookie = memberService.checkMemberAndMakeCookie(
                dto.username,
                dto.password
        );

        res = new ResponseEntity<>("로그인에 성공했습니다.",headersWithCookie, HttpStatus.OK);
        return res;
    }

    @GetMapping("/confirm")
        public ResponseEntity<String> confirm(@AuthenticationPrincipal SecurityUser principal){
        System.out.println(principal.toString());
        return ResponseEntity.ok(principal.getUsername());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> memberLogout(){
        HttpHeaders headers = memberService.logout();
        return new ResponseEntity<>("로그아웃에 성공했습니다.", headers, HttpStatus.OK);
    }

    public record UserInfoDto(Long id, String username, String name){}

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal SecurityUser principal){
        UserInfoDto dto = new UserInfoDto(principal.getId(), principal.getUsername(), principal.getName());
        return ResponseEntity.ok(dto);
    }


}
