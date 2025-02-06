package com.goalmate.security.oauth.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppleClaimsValidator {

	private static final String NONCE_KEY = "nonce";

	private final String iss;
	private final String clientId;

	public AppleClaimsValidator(
		@Value("${oauth.apple.iss}") String iss,
		@Value("${oauth.apple.client-id}") String clientId
	) {
		this.iss = iss;
		this.clientId = clientId;
	}

	public boolean isValid(Claims claims, String nonce) {
		log.info(
			claims.getIssuer().contains(iss) + " " + claims.getAudience().contains(clientId) + " " + claims.get(
				NONCE_KEY, String.class).equals(nonce));
		return claims.getIssuer().contains(iss) &&
			claims.getAudience().stream().findFirst().equals(clientId) &&
			claims.get(NONCE_KEY, String.class).equals(nonce);
	}
}
