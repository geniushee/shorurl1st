package com.surl.first.global.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServlet;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(basic -> basic.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequest -> {
                    authorizeRequest.requestMatchers("/h2-console/**")
                            .permitAll();
                    authorizeRequest.anyRequest().permitAll();
                })
                .headers(
                        headers -> headers.frameOptions(
                                options -> options.sameOrigin()
                        )
                );
        return http.build();
    }
}
