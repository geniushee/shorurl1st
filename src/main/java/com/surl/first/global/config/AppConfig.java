package com.surl.first.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

	@Getter
	private static String jwtSecretKey;

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Getter
	private static String frontUrl;

	@Value("${custom.dev.frontUrl}")
	public void setFrontUrl(String frontUrl){
		this.frontUrl = frontUrl;
	}

	@Value("${custom.jwt.secretKey}")
	public void setJwtSecretKey(String jwtSecretKey){
		this.jwtSecretKey = jwtSecretKey;
	}

}
