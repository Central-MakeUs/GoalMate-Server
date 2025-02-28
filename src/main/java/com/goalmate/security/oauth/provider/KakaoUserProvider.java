package com.goalmate.security.oauth.provider;

import java.security.PublicKey;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.goalmate.infra.feign.KakaoAuthClient;
import com.goalmate.infra.feign.KakaoUserClient;
import com.goalmate.security.oauth.oidc.OAuthMember;
import com.goalmate.security.oauth.oidc.OIDCJwtParser;
import com.goalmate.security.oauth.oidc.OIDCPublicKeyResolver;
import com.goalmate.security.oauth.oidc.OIDCPublicKeys;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoUserProvider {
	private final OIDCJwtParser oidcJwtParser;
	private final OIDCPublicKeyResolver oidcPublicKeyResolver;
	private final KakaoAuthClient kakaoAuthClient;
	private final KakaoUserClient kakaoUserClient;

	@Value("${oauth.kakao.admin-key}")
	private String adminKey;

	@Value("${oauth.kakao.target-id-type}")
	private String targetIdType;

	public OAuthMember getKaKaoPlatformMember(String identityToken, String nonce) {
		Map<String, String> headers = oidcJwtParser.parseHeaders(identityToken);
		OIDCPublicKeys OIDCPublicKeys = kakaoAuthClient.getPublicKeys();

		// 알맞은 퍼블릭키 찾아와서 identityToken 파싱
		PublicKey publicKey = oidcPublicKeyResolver.resolvePublicKey(headers, OIDCPublicKeys);
		Claims claims = oidcJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey);
		return new OAuthMember(claims.getSubject(), claims.get("email", String.class));
	}

	public void unlinkUser(String targetId) {
		kakaoUserClient.unlinkUser(adminKey, targetIdType, Long.valueOf(targetId));
	}
}
