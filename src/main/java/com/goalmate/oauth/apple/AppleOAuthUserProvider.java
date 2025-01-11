package com.goalmate.oauth.apple;

import java.security.PublicKey;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.goalmate.feign.AppleClient;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppleOAuthUserProvider {

	private final AppleJwtParser appleJwtParser;
	private final AppleClient appleClient;
	private final PublicKeyGenerator publicKeyGenerator;
	private final AppleClaimsValidator appleClaimsValidator;

	public ApplePlatformMember getApplePlatformMember(String identityToken) {
		Map<String, String> headers = appleJwtParser.parseHeaders(identityToken);
		ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();

		PublicKey publicKey = publicKeyGenerator.generatePublicKey(headers, applePublicKeys);

		Claims claims = appleJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey);
		validateClaims(claims);
		return new ApplePlatformMember(claims.getSubject(), claims.get("email", String.class));
	}

	private void validateClaims(Claims claims) {
		if (!appleClaimsValidator.isValid(claims)) {
			throw new IllegalArgumentException("Apple OAuth Claims 값이 올바르지 않습니다.");
		}
	}
}
