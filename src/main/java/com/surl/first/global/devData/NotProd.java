package com.surl.first.global.devData;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.surl.first.domain.surl.surl.service.SUrlService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@Profile("!prod")
public class NotProd {

	private final SUrlService sUrlService;

	@Bean
	public ApplicationRunner work1(){
		return args -> {
			String sUrl1 = sUrlService.createNew("https://www.google.com");
			String sUrl2 = sUrlService.createNew("https://www.naver.com");
			String sUrl3 = sUrlService.createNew("https://www.daum.com");
		};
	}
}
