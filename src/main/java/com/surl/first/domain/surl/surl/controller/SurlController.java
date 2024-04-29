package com.surl.first.domain.surl.surl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.surl.first.domain.surl.surl.service.SUrlService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/surls")
public class SurlController {

	private final SUrlService sUrlService;

	public record SUrlRequestDto(@NotBlank String origin){

	}

	public record SUrlResponseDto(String SUrl){

	}

	@PostMapping("/create")
	public ResponseEntity<?> createNewSUrl(@Valid @RequestBody SUrlRequestDto dto){
		ResponseEntity<?> res;
		try{
			String sUrl = sUrlService.createNew(dto.origin());
			res = ResponseEntity.ok(sUrl);
		} catch(Exception e){
			res = ResponseEntity.badRequest().body(e.getMessage());
		}
		return res;
	}

}
