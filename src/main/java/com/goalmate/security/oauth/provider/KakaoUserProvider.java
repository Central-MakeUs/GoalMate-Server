package com.goalmate.security.oauth.provider;

import java.security.PublicKey;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.goalmate.infra.feign.KakaoClient;
import com.goalmate.security.oauth.oidc.OAuthMember;
import com.goalmate.security.oauth.oidc.OIDCJwtParser;
import com.goalmate.security.oauth.oidc.OIDCPublicKeyResolver;
import com.goalmate.security.oauth.oidc.OIDCPublicKeys;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class KakaoUserProvider {
	private final KakaoClient kakaoClient;
	private final OIDCJwtParser oidcJwtParser;
	private final OIDCPublicKeyResolver oidcPublicKeyResolver;

	public OAuthMember getKaKaoPlatformMember(String identityToken, String nonce) {
		Map<String, String> headers = oidcJwtParser.parseHeaders(identityToken);
		OIDCPublicKeys OIDCPublicKeys = kakaoClient.getPublicKeys();

		// 알맞은 퍼블릭키 찾아와서 identityToken 파싱
		PublicKey publicKey = oidcPublicKeyResolver.resolvePublicKey(headers, OIDCPublicKeys);
		Claims claims = oidcJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey);
		return new OAuthMember(claims.getSubject(), claims.get("email", String.class));
	}
}
