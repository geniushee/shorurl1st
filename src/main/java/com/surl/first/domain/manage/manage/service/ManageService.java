package com.surl.first.domain.manage.manage.service;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.service.MemberService;
import com.surl.first.domain.surl.surl.entity.SUrl;
import com.surl.first.domain.surl.surl.service.SUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageService {
    private final MemberService memberService;
    private final SUrlService sUrlService;


    public Long getTotalUsers() {
        return (long) memberService.findAll().size();
    }

    public Long getTotalSUrls() {
        return (long) sUrlService.findAll().size();
    }

    public List<Member> getRecentResister() {
        return memberService.findTop10Recently();
    }

    public List<SUrl> getRecentSUrls() {
        return sUrlService.findTop10Recently();
    }
}
