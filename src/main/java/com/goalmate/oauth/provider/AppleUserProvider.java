package com.goalmate.oauth.provider;

import java.security.PublicKey;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.goalmate.feign.AppleClient;
import com.goalmate.oauth.oidc.OAuthMember;
import com.goalmate.oauth.oidc.OIDCJwtParser;
import com.goalmate.oauth.oidc.OIDCPublicKeyResolver;
import com.goalmate.oauth.oidc.OIDCPublicKeys;
import com.goalmate.oauth.validator.AppleClaimsValidator;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppleUserProvider {
	private final AppleClient appleClient;
	private final OIDCJwtParser oidcJwtParser;
	private final OIDCPublicKeyResolver oidcPublicKeyResolver;
	private final AppleClaimsValidator appleClaimsValidator;

	public OAuthMember getApplePlatformMember(String identityToken, String nonce) {
		// 헤더 파싱 및 애플 측 Public key 가져옴
		Map<String, String> headers = oidcJwtParser.parseHeaders(identityToken);
		OIDCPublicKeys appleOIDCPublicKeys = appleClient.getApplePublicKeys();
		PublicKey publicKey = oidcPublicKeyResolver.resolvePublicKey(headers, appleOIDCPublicKeys);

		Claims claims = oidcJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey);
		validateClaims(claims, nonce);
		return new OAuthMember(claims.getSubject(), claims.get("email", String.class));
	}

	private void validateClaims(Claims claims, String nonce) {
		if (!appleClaimsValidator.isValid(claims, nonce)) {
			throw new IllegalArgumentException("Apple OAuth Claims 값이 올바르지 않습니다.");
		}
	}
}
