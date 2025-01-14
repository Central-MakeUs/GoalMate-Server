package com.goalmate.oauth.kakao;

import java.security.PublicKey;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.goalmate.feign.KakaoClient;
import com.goalmate.oauth.oidc.OAuthMember;
import com.goalmate.oauth.oidc.OIDCJwtParser;
import com.goalmate.oauth.oidc.OIDCPublicKeyProvider;
import com.goalmate.oauth.oidc.OIDCPublicKeys;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class KakaoOAuthUserProvider {
	private final KakaoClient kakaoClient;
	private final OIDCJwtParser oidcJwtParser;
	private final OIDCPublicKeyProvider oidcPublicKeyProvider;

	public OAuthMember getKaKaoPlatformMember(String identityToken, String nonce) {
		Map<String, String> headers = oidcJwtParser.parseHeaders(identityToken);
		OIDCPublicKeys oidcPublicKeys = kakaoClient.getPublicKeys();

		// 알맞은 퍼블릭키 찾아와서 identityToken 파싱
		PublicKey publicKey = oidcPublicKeyProvider.getPublicKeyFromHeaders(headers, oidcPublicKeys);
		Claims claims = oidcJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey);
		return new OAuthMember(claims.getSubject(), claims.get("email", String.class));
	}
}
