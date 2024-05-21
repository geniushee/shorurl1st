package com.surl.first.domain.surl.surl.controller;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.service.MemberService;
import com.surl.first.domain.surl.surl.entity.SUrl;
import com.surl.first.global.securityConfig.SecurityUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.surl.first.domain.surl.surl.service.SUrlService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/surls")
public class SurlController {

    private final SUrlService sUrlService;
    private final MemberService memberService;

    public record SUrlRequestDto(@NotBlank String sUrl) {
    }

    @GetMapping("")
    public ResponseEntity<?> getSUrl(@RequestParam(name = "sUrl") String sUrl) {
        ResponseEntity res;
        SUrl surl;
        try {
            surl = sUrlService.findSUrl(sUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        res = ResponseEntity.ok(surl.getOrigin());
        return res;
    }

    public record SUrlCreateRequestDto(@NotBlank String origin) {

    }

    public record SUrlResponseDto(String SUrl) {

    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewSUrl(@Valid @RequestBody SurlController.SUrlCreateRequestDto dto,
                                           @AuthenticationPrincipal SecurityUser user) {
        ResponseEntity<?> res;
        try {
            String sUrl = sUrlService.createNew(dto.origin(), user.getId());
            res = ResponseEntity.ok(sUrl);
        } catch (Exception e) {
            res = ResponseEntity.badRequest().body(e.getMessage());
        }
        return res;
    }

    @GetMapping("/mylist")
    public ResponseEntity<?> getSUrlList(@AuthenticationPrincipal SecurityUser user){
        Member member = memberService.findById(user.getId());
        List<SUrl> list = sUrlService.findAllByMember(member);
        return ResponseEntity.ok(list);
    }
//
//    public record SUrlModifyRequestBody(String origin, String title, String content){}
//
//    @PutMapping("/modify")
//    public ResponseEntity<?> modifySUrl(@Valid @RequestBody SUrlModifyRequestBody dto,
//                                        @AuthenticationPrincipal SecurityUser user){
//        SUrl sUrl = sUrlService.modify(dto.origin ,dto.title, dto.content, user.getId());
//
//    }

}
