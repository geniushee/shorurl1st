package com.surl.first.global.config.rabbitmq;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.service.MemberService;
import com.surl.first.global.securityConfig.SecurityUser;
import com.surl.first.global.securityConfig.jwtConfig.JwtUtil;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Websocket 최초 연결 시 serverHttpRequest를 인터셉터하여 Http정보를 사용할 수 있도록 하는 클래스
 * Http에서 쿠키를 가져와서 쿠키내 accessToken을 확인하고 security에 유저정보를 넣기위해 가공.
 */
@Component
@RequiredArgsConstructor
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    private final MemberService memberService;

    /**
     * httpRequest를 가져와서 쿠키를 받아다가 user를 attributes에 추가한다. 추가한 user 정보는 handler에서 받아서 사용.
     * @param request the current request
     * @param response the current response
     * @param wsHandler the target WebSocket handler
     * @param attributes the attributes from the HTTP handshake to associate with the WebSocket
     * session; the provided attributes are copied, the original map is not used.
     * @return true - 사용한다 / false - 미사용
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String[] cookieStrings = request.getHeaders().get("Cookie").getFirst().split("; ");
        System.out.println("***cookieString : " + Arrays.toString(cookieStrings));
        List<Cookie> cookies = Arrays.stream(cookieStrings).map(str -> {
            System.out.println("str : "+str);
            String[] arr = str.split("=", 2);
            System.out.println("arr : "+Arrays.toString(arr));
            return new Cookie(arr[0], arr[1]);
        }).toList();

        String accessToken = cookies.stream().filter(cookie -> cookie.getName().equals("accessToken"))
                .findFirst()
                .orElse(new Cookie("NotHave",""))
                .getValue();

        System.out.println("***accessToken : " + accessToken);
        if(JwtUtil.notExpired(accessToken)){
            Map<String, Object> maps = JwtUtil.getDataFromToken(accessToken);
            Member member = memberService.findById(Long.valueOf((String) maps.get("id")));
            SecurityUser user = new SecurityUser(member.getId(), member.getUsername(), "", member.getName(), member.getAuthorities());
            attributes.put("user", user);
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
