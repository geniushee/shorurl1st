package com.surl.first.domain.surl.surl.service;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.service.MemberService;
import com.surl.first.domain.surl.surl.dto.SUrlDto;
import com.surl.first.global.event.event.SUrlEvent;
import com.surl.first.global.event.publisher.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final EventPublisher publisher;

    @Transactional
    public String createNew(String origin, Long memberId) {
        Member member = memberService.findById(memberId);
        SUrl sUrl = SUrl.builder()
                .member(member)
                .origin(origin)
                .build();

        sUrl = sUrlRepository.save(sUrl);
        publisher.publish(new SUrlEvent("create", new SUrlDto(sUrl)));
        System.out.println(publisher.getClass().getSimpleName());
        return sUrl.getShortUrl();
    }

    public SUrl findSUrl(String sUrl) throws Exception {
        try {
            return sUrlRepository.findById(Long.valueOf(sUrl)).orElseThrow(() -> new RuntimeException("찾는 URL이 없습니다."));
        } catch (NumberFormatException e) {
            return sUrlRepository.findByTitle(sUrl).orElseThrow(() -> new RuntimeException("찾는 URL이 없습니다."));
        }
    }

    public List<SUrl> findAllByMember(Member member) {
        return sUrlRepository.findAllByMember(member);
    }

    public SUrl findSUrlByIdWithMember(Long sUrlId, Long memberId) {
        SUrl sUrl = sUrlRepository.findById(sUrlId).orElseThrow(() -> new IllegalArgumentException("잘못된 정보입니다."));
        Member member = memberService.findById(memberId);
        if (!sUrl.getMember().equals(member)) {
            throw new RuntimeException("올바른 접근이 아닙니다.");
        }
        return sUrl;
    }

    @Transactional
    public SUrl modify(Long id, String title, String content) {
        SUrl sUrl = findById(id);
        sUrl.setTitle(title);
        sUrl.setContent(content);
        System.out.println(sUrl.getTitle());
        System.out.println(sUrl.getContent());
        publisher.publish(new SUrlEvent("modify", new SUrlDto(sUrl)));
        return sUrl;
    }

    public SUrl findById(Long id) {
        return sUrlRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("잘못된 정보입니다."));
    }

    public List<SUrl> findAll() {
        return sUrlRepository.findAll();
    }

    @Transactional
    public void deleteSUrl(Long userId, Long sUrlId) {
        Member member = memberService.findById(userId);
        SUrl sUrl = findById(sUrlId);
        if (sUrl.getMember().equals(member)) {
            publisher.publish(new SUrlEvent("delete", new SUrlDto(sUrl)));
            sUrlRepository.delete(sUrl);
        } else {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

    }

    public List<SUrl> findTop10Recently() {
        return sUrlRepository.findTop10ByOrderByCreateDateDesc();
    }

    @Transactional
    public void deleteAllByMember(Member member) {
        sUrlRepository.deleteAllByMember(member);
    }
}
