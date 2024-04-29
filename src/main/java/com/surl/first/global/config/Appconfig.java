package com.surl.first.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
public class Appconfig {


	@Getter
	private static String frontUrl;

	@Value("${custom.dev.frontUrl}")
	public void setFrontUrl(String frontUrl){
		this.frontUrl = frontUrl;
	}
}
