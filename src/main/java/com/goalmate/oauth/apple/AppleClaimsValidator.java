package com.goalmate.oauth.apple;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

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
		return claims.getIssuer().contains(iss) &&
			claims.getAudience().equals(clientId) &&
			claims.get(NONCE_KEY, String.class).equals(nonce);
	}
}
