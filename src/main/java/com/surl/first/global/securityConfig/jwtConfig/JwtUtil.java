package com.surl.first.global.securityConfig.jwtConfig;

import com.surl.first.global.config.AppConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.http.ResponseCookie;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
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
                .domain(AppConfig.getFrontDomain())
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .build();
    }

    // todo argument를 바꾸자
    public static ResponseCookie encodeAndSetCookie(String tokenName, long expiredMinutes, Map<String, Object> data) {
        String jws = encode(expiredMinutes,data);
        return setCrossDomainCookie(tokenName, jws);
    }

    public static ResponseCookie removeCrossDomainCookie(HttpServletRequest request,
                                                         HttpServletResponse response,String name) {
        removeCookie(request, response, name);

        return ResponseCookie.from(name)
                .path("/")
                .domain(AppConfig.getFrontDomain())
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .build();
    }

    public static boolean expired(String token){
        return !notExpired(token);
    }

    public static boolean notExpired(String token) {
        try{
            Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
            return true;
        }catch(ExpiredJwtException | SignatureException e){
            return false;
        }
    }

    public static void removeCookie(HttpServletRequest request,
                                    HttpServletResponse response, String tokenName) {
        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(coo -> coo.getName().equals(tokenName)).findFirst().orElse(null);
        if(cookie == null){
            return;
        }

        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
