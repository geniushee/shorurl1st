package com.surl.first.domain.manage.manage.controller;

import com.surl.first.domain.manage.manage.service.ManageService;
import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.surl.surl.entity.SUrl;
import com.surl.first.domain.surl.surl.service.SUrlService;
import com.surl.first.global.securityConfig.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manage")
@RequiredArgsConstructor
public class ManageController {

    private final ManageService manageService;

    private void validAdmin(SecurityUser admin){
        if(!admin.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new RuntimeException("관리자가 아닙니다.");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getTotalUsers(@AuthenticationPrincipal SecurityUser admin){
        validAdmin(admin);

        Long userCount = manageService.getTotalUsers();
        return ResponseEntity.ok(userCount);
    }

    @GetMapping("/surls")
    public ResponseEntity<?> getTotalSUrls(@AuthenticationPrincipal SecurityUser admin){
        validAdmin(admin);

        Long sUrlCount = manageService.getTotalSUrls();
        return ResponseEntity.ok(sUrlCount);
    }

    @GetMapping("/recentResister")
    public ResponseEntity<?> getRecentResister(@AuthenticationPrincipal SecurityUser admin){
        validAdmin(admin);

        List<Member> list = manageService.getRecentResister();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/recentSurls")
    public ResponseEntity<?> getRecentSUrls(@AuthenticationPrincipal SecurityUser admin){
        validAdmin(admin);

        List<SUrl> list = manageService.getRecentSUrls();
        return ResponseEntity.ok(list);
    }
}
