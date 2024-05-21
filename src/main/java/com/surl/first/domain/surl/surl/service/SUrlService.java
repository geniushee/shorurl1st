package com.surl.first.domain.surl.surl.service;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.surl.first.domain.surl.surl.controller.SurlController;
import com.surl.first.domain.surl.surl.entity.SUrl;
import com.surl.first.domain.surl.surl.repository.SUrlRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SUrlService {
    private final SUrlRepository sUrlRepository;
    private final MemberService memberService;

    @Transactional
    public String createNew(String origin, Long memberId) {
        Member member = memberService.findById(memberId);
        SUrl sUrl = SUrl.builder()
                .member(member)
                .origin(origin)
                .build();

        sUrl = sUrlRepository.save(sUrl);

        return sUrl.getShortUrl();
    }

    public SUrl findSUrl(String sUrl) throws Exception {
        return sUrlRepository.findById(Long.valueOf(sUrl)).orElseThrow(() -> new RuntimeException("찾는 URL이 없습니다."));
    }

    public List<SUrl> findAllByMember(Member member) {
        return sUrlRepository.findAllByMember(member);
    }
}
