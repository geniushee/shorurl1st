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
import org.springframework.http.ResponseCookie;
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
        if(!request.getRequestURI().startsWith("/api")){
            filterChain.doFilter(request,response);
            return;
        }

        if(List.of("/api/v1/members/login", "/api/v1/members/register").contains(request.getRequestURI())){
            filterChain.doFilter(request,response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String accessToken = Arrays.stream(cookies)
                    .filter(cookie ->
                            cookie.getName()
                                    .equals("accessToken"))
                    .findFirst().orElse(new Cookie("accessToken","")).getValue();
            System.out.println(accessToken);
            String refreshToken = Arrays.stream(cookies)
                    .filter(cookie ->
                            cookie.getName()
                                    .equals("refreshToken"))
                    .findFirst().orElse(new Cookie("refreshToken","")).getValue();
            Claims claims;

            if(accessToken.isBlank() && refreshToken.isBlank()){
                filterChain.doFilter(request,response);
                return;
            }

            if(JwtUtil.expired(refreshToken)){
                throw new RuntimeException("다시 로그인 해주세요.");
            } else if(JwtUtil.expired(accessToken) && JwtUtil.notExpired(refreshToken)){
                System.out.println("어세스토큰 갱신!!!");
                Member member = memberService.findByRefreshToken(refreshToken);
                Map data = Map.of(
                        "id", member.getId(),
                        "username", member.getUsername(),
                        "authorities", member.getAuthorities());
                ResponseCookie token = JwtUtil.encodeAndSetCookie("accessToken",10,data);
                response.addHeader("Set-Cookie", token.toString());
                claims = JwtUtil.decode(token.toString());
            } else{
                claims = JwtUtil.decode(accessToken);
            }

            Map data = claims.get("data", Map.class);
            Long id = Long.valueOf(data.get("id").toString());

            System.out.println(id);
            Member member = memberService.findById(id);
            SecurityUser user = new SecurityUser(member.getId(), member.getUsername(), member.getPassword(), member.getName(), member.getAuthorities());
            Authentication auth = new UsernamePasswordAuthenticationToken(user, member.getPassword(), member.getAuthorities());

            // Debugging: Log the authentication object
            System.out.println("Setting authentication for user: " + user.getUsername());
            System.out.println("Authentication object: " + auth);

            SecurityContextHolder.getContext().setAuthentication(auth);

            // Debugging: Check if authentication is set in the security context
            System.out.println("SecurityContextHolder Authentication: " + SecurityContextHolder.getContext().getAuthentication());
        }
        filterChain.doFilter(request, response);

    }
}
