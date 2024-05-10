package com.surl.first.global.securityConfig.jwtConfig;

import com.surl.first.global.config.AppConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.http.ResponseCookie;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = AppConfig.getJwtSecretKey();


    private static SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String encode(long expiredMinutes, Map<String, Object> data){
        Claims claims = Jwts.claims()
                .subject("surl")
                .add("type", "access_token")
                .add("data", data)
                .build();

        //만료시간을 추가
        Instant nowIn = Instant.now(); //현재시간을 설정
        Date now = Date.from(nowIn);
        Duration duration = Duration.ofMinutes(expiredMinutes); // 10분간
        Date expiration = Date.from(nowIn.plus(duration)); // 현재시간에서 + 10분간

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSecretKey())
                .compact();
    }

    public static Claims decode(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public static ResponseCookie setCrossDomainCookie(String name, String value) {
        return ResponseCookie.from(name, value)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .build();
    }

    public static ResponseCookie encodeAndSetCookie(String tokenName, long expiredMinutes, Map<String, Object> data) {
        String jws = encode(expiredMinutes,data);
        return setCrossDomainCookie(tokenName, jws);
    }

    public static ResponseCookie removeCrossDomainCookie(String name) {
        return ResponseCookie.from(name)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .build();
    }
}
