package com.surl.first.global.securityConfig.jwtConfig;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.service.MemberService;
import com.surl.first.global.securityConfig.SecurityUser;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String accessToken = Arrays.stream(cookies)
                    .filter(cookie ->
                            cookie.getName()
                                    .equals("accessToken"))
                    .findFirst().orElseThrow().getValue();
            System.out.println(accessToken);
            Claims claims = JwtUtil.decode(accessToken);
            Map data = claims.get("data", Map.class);
            Long id = Long.valueOf(data.get("id").toString());

            System.out.println(id);
            Member member = memberService.findById(id);
            SecurityUser user = new SecurityUser(member.getId(), member.getUsername(), member.getPassword(), member.getAuthorities());
            Authentication auth = new UsernamePasswordAuthenticationToken(user, member.getPassword(), member.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);

    }
}
