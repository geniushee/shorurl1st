package com.surl.first.domain.member.member.controller;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.service.MemberService;
import com.surl.first.domain.member.member.service.MemberTransactionManageService;
import com.surl.first.global.config.AppConfig;
import com.surl.first.global.securityConfig.SecurityUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberTransactionManageService transactionManageService;

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
    public ResponseEntity<?> memberLogout(HttpServletRequest request,
                                          HttpServletResponse response){
        HttpHeaders headers = memberService.logout(request, response);
        return new ResponseEntity<>("로그아웃에 성공했습니다.", headers, HttpStatus.OK);
    }

    public record UserInfoDto(Long id, String username, String name, String email, Collection authorities){}

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal SecurityUser principal){
        Member member = memberService.findById(principal.getId());
        UserInfoDto dto = new UserInfoDto(principal.getId(), principal.getUsername(), principal.getName(), member.getEmail(),principal.getAuthorities());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/socialLogin/{providerTypeCode}")
    public void socialLogin(@PathVariable(name = "providerTypeCode")String providerTypeCode,
                              @RequestParam(name = "redirectUrlAfterSocialLogin")String redirectUrlAfterSocialLogin,
                              HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("redirectUrlAfterSocialLogin", redirectUrlAfterSocialLogin);
        cookie.setMaxAge(60*10);
        cookie.setPath("/");
        response.addCookie(cookie);
        String redirectUrl = AppConfig.getBackUrl() + "/oauth2/authorization/" + providerTypeCode;
        response.sendRedirect(redirectUrl);
    }

    @DeleteMapping("/withdrawal")
    public ResponseEntity<?> withdrawalMember(@AuthenticationPrincipal SecurityUser user,
                                              @RequestBody MemberLoginDto dto,
                                              HttpServletRequest request,
                                              HttpServletResponse response){
        // 하나의 트랜잭션으로 관리하여 surl과 member를 삭제하기 위해서 트랜젝션으로 묶어줄 서비스를 만듬.
        // 순환주입가 발생하여 어쩔 수 없이. 순환주입가 발생하지 않도록 주의 필요
        HttpHeaders headers = transactionManageService.withdrawalMember(user.getId(), dto.password, request, response);

        return new ResponseEntity<>("성공적으로 회원을 탈퇴했습니다.", headers, HttpStatus.OK);
    }

    public record RequestBodyEditProfile(String password, String passwordConfirm, String name, String email){}

    @PutMapping("/editProfile")
    public ResponseEntity<?> editMemberProfile(@AuthenticationPrincipal SecurityUser user,
                                               @RequestBody RequestBodyEditProfile dto){
        Member member = memberService.editProfile(user.getId(), dto.password, dto.passwordConfirm, dto.name, dto.email);
        return ResponseEntity.ok(member);
    }
}
