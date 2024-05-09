package com.surl.first.domain.surl.surl.controller;

import com.surl.first.domain.surl.surl.entity.SUrl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.surl.first.domain.surl.surl.service.SUrlService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/surls")
public class SurlController {

    private final SUrlService sUrlService;

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
    public ResponseEntity<?> createNewSUrl(@Valid @RequestBody SurlController.SUrlCreateRequestDto dto) {
        ResponseEntity<?> res;
        try {
            String sUrl = sUrlService.createNew(dto.origin());
            res = ResponseEntity.ok(sUrl);
        } catch (Exception e) {
            res = ResponseEntity.badRequest().body(e.getMessage());
        }
        return res;
    }

}
