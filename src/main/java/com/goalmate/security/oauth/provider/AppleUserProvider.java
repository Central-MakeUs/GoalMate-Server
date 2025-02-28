package com.goalmate.security.oauth.provider;

import java.security.PublicKey;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.goalmate.infra.feign.AppleClient;
import com.goalmate.security.oauth.oidc.OAuthMember;
import com.goalmate.security.oauth.oidc.OIDCJwtParser;
import com.goalmate.security.oauth.oidc.OIDCPublicKeyResolver;
import com.goalmate.security.oauth.oidc.OIDCPublicKeys;
import com.goalmate.security.oauth.validator.AppleClaimsValidator;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;

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
			throw new CoreApiException(ErrorType.UNAUTHORIZED, "Apple OAuth Claims 값이 올바르지 않습니다.");
		}
	}
}
