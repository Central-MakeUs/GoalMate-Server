package com.goalmate.infra.feign.config;

import org.springframework.context.annotation.Bean;

import com.goalmate.infra.feign.exception.FeignClientExceptionErrorDecoder;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;

public class KakaoFeignConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return template ->
			template.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new FeignClientExceptionErrorDecoder();
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
