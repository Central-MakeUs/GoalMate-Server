package com.goalmate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(
				"http://localhost:5173",
				"https://goalmate-web-beta.vercel.app",
				"https://api.goalmate.site",
				"https://manage.goalmate.site")
			.allowedHeaders("*")
			.exposedHeaders("Authorization")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
			.allowCredentials(true);
	}
}
