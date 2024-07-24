package com.surl.first.global.config.rabbitmq;

import com.surl.first.global.securityConfig.SecurityUser;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * Websocket 최소 연결 시 AuthHandshakeInterceptor에서 가져온 http정보를 사용하는 클래스
 */
@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    /**
     * 최소 연결 시 Websocket에 Authentication 정보를 넣는 메소드.
     * @param request the handshake request
     * @param wsHandler the WebSocket handler that will handle messages
     * @param attributes handshake attributes to pass to the WebSocket session
     * @return UsernamePasswordAuthenticationToken을 반환
     */
    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                     WebSocketHandler wsHandler,
                                     Map<String, Object> attributes){
        SecurityUser user = (SecurityUser) attributes.get("user");
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }
}
