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

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
        SUrl sUrlOb;
        try {
            sUrl = URLDecoder.decode(sUrl, StandardCharsets.UTF_8);
            sUrlOb = sUrlService.findSUrl(sUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        res = ResponseEntity.ok(sUrlOb.getOrigin());
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
    public ResponseEntity<?> getSUrlList(@AuthenticationPrincipal SecurityUser user) {
        Member member = memberService.findById(user.getId());
        List<SUrl> list = sUrlService.findAllByMember(member);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/mylist/{id}")
    public ResponseEntity<?> getSUrlInfo(@AuthenticationPrincipal SecurityUser user,
                                         @PathVariable(name = "id") Long id) {
        SUrl SUrl = sUrlService.findSUrlByIdWithMember(id, user.getId());
        return ResponseEntity.ok(SUrl);
    }

    public record SUrlModifyRequestBody(Long id, String title, String content) {
    }

    @PutMapping("/mylist/{id}")
    public ResponseEntity<?> modifySUrl(@AuthenticationPrincipal SecurityUser user,
                                        @PathVariable(name = "id") Long id,
                                        @RequestBody SUrlModifyRequestBody dto) {
        try {
            SUrl sUrl = sUrlService.modify(id, dto.title, dto.content);
            return ResponseEntity.ok(sUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("변경에 실패했습니다.");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSUrl(@AuthenticationPrincipal SecurityUser user,
                                        @PathVariable(name = "id") Long id) {
        try {
            sUrlService.deleteSUrl(user.getId(), id);
            return ResponseEntity.ok("삭제했습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
