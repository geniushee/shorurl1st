package com.surl.first.domain.member.member.service;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.surl.surl.service.SUrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberTransactionManageService {
    private final MemberService memberService;
    private final SUrlService sUrlService;

    @Transactional
    public HttpHeaders withdrawalMember(Long userId, String rawPassword,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        Member member = memberService.checkMember(userId, rawPassword);
        sUrlService.deleteAllByMember(member);
        memberService.withdrawal(member);
        return memberService.logout(request, response);
    }
}
