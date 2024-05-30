package com.surl.first.global.securityConfig.oauth2;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.service.MemberService;
import com.surl.first.global.config.AppConfig;
import com.surl.first.global.securityConfig.SecurityUser;
import com.surl.first.global.securityConfig.jwtConfig.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        Optional<Cookie> opCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("redirectUrlAfterSocialLogin"))
                .findFirst();

        if(opCookie.get().getValue().equals(AppConfig.getFrontDomain())){
            JwtUtil.removeCookie(request,response,"redirectUrlAfterSocialLogin");
            SecurityUser user = (SecurityUser) authentication.getPrincipal();
            Member member = memberService.findById(user.getId());
            // todo 굉장히 불필요한 작업이 이어지고 있다. 개선필요.
            HttpHeaders headers = memberService.checkMemberAndMakeCookie(member.getUsername(), member.getPassword());
            List<String> cookieValues = headers.getValuesAsList("Set-Cookie");
            for(String value : cookieValues) {
                response.addHeader("Set-Cookie", value);
            }
            return;
        }

        super.onAuthenticationSuccess(request,response,authentication);
    }
}
