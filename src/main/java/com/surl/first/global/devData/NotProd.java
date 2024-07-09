package com.surl.first.global.devData;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.service.MemberService;
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
	private final MemberService memberService;

	@Bean
	public ApplicationRunner work1(){
		return args -> {
			Member member1 = null;
			try{
				member1 = memberService.findById(1L);
			}catch(Exception e){
				member1 = memberService.register("user1", "1234", "user1@user.com", "user1");
				memberService.register("admin", "admin1234", "admin@admin.com", "admin");
			}

			if(memberService.findAll().size() <3) {
				Member member2 = memberService.register("user2", "1234", "user2@user.com", "user2");
				Member member3 = memberService.register("user3", "1234", "user3@user.com", "user3");
			}

			if(sUrlService.findAll().size() <2) {
				String sUrl1 = sUrlService.createNew("https://www.google.com", member1.getId());
				String sUrl2 = sUrlService.createNew("https://www.naver.com", member1.getId());
				String sUrl3 = sUrlService.createNew("https://www.daum.com", member1.getId());
				String sUrl4 = sUrlService.createNew("https://www.daum.com", member1.getId());
			}

			sUrlService.modify(2L, "네이버", "네이버 이동");
			sUrlService.deleteSUrl(member1.getId(), 4L);
		};
	}
}
