package com.goalmate.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.*;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.goalmate.security.jwt.JwtProvider;
import com.goalmate.security.jwt.filter.JwtAccessDeniedHandler;
import com.goalmate.security.jwt.filter.JwtAuthenticationEntryPoint;
import com.goalmate.security.jwt.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final JwtProvider jwtProvider;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChainPermitAll(HttpSecurity http) throws Exception {
		configureDefaultSecurity(http);
		http.securityMatchers(matchers -> matchers
				.requestMatchers(requestPermitAll()))
			.authorizeHttpRequests(authorize -> authorize
				.anyRequest().permitAll());
		return http.build();
	}

	private RequestMatcher[] requestPermitAll() {
		List<RequestMatcher> requestMatchers = List.of(
			antMatcher("/actuator/**"),
			antMatcher("/swagger-ui/**"),
			antMatcher("/v3/api-docs/**"),
			antMatcher("/auth/login/**"),
			antMatcher("/auth/reissue"),
			antMatcher(HttpMethod.GET, "/goals"),
			antMatcher("/uploads/**"),
			antMatcher("/mentors/**"),
			antMatcher("/api/v2/admin/**")
		);
		return requestMatchers.toArray(RequestMatcher[]::new);
	}

	// permitAll 이지만, 필터는 거쳐야 하는 경우
	@Bean
	public SecurityFilterChain securityFilterChainPermitAllButAuthenticate(HttpSecurity http) throws Exception {
		configureDefaultSecurity(http);
		http.securityMatchers(matchers -> matchers
				.requestMatchers(antMatcher("/goals/{goalId}")))
			.authorizeHttpRequests(authorize -> authorize
				.anyRequest().permitAll())
			.addFilterAfter(new JwtAuthenticationFilter(jwtProvider), ExceptionTranslationFilter.class);
		return http.build();
	}

	@Bean
	public SecurityFilterChain securityFilterChainAuthenticate(HttpSecurity http) throws Exception {
		configureDefaultSecurity(http);
		http.securityMatchers(AbstractRequestMatcherRegistry::anyRequest)
			.authorizeHttpRequests(authorize -> authorize
				.anyRequest().hasAnyRole("MENTEE", "MENTOR", "ADMIN"))
			.exceptionHandling(exception -> {
				exception.authenticationEntryPoint(jwtAuthenticationEntryPoint);
				exception.accessDeniedHandler(jwtAccessDeniedHandler);
			})
			.addFilterAfter(new JwtAuthenticationFilter(jwtProvider), ExceptionTranslationFilter.class);
		return http.build();
	}

	private void configureDefaultSecurity(HttpSecurity http) throws Exception {
		http
			.cors(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.anonymous(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.rememberMe(AbstractHttpConfigurer::disable)
			.headers(headers -> headers
				.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
			)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	}
}

