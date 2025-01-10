package com.goalmate.oauth.apple;

import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class AppleJwtParser {

	private static final String IDENTITY_TOKEN_VALUE_DELIMITER = "\\.";
	private static final int HEADER_INDEX = 0;

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public Map<String, String> parseHeaders(String identityToken) {
		try {
			String encodedHeader = identityToken.split(IDENTITY_TOKEN_VALUE_DELIMITER)[HEADER_INDEX];
			String decodedHeader = new String(Base64.getUrlDecoder().decode(encodedHeader));
			return OBJECT_MAPPER.readValue(decodedHeader, Map.class);
		} catch (JsonProcessingException | ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Apple OAuth Identity Token 형식이 올바르지 않습니다.");
		}
	}

	public Claims parsePublicKeyAndGetClaims(String idToken, PublicKey publicKey) {
		try {
			return Jwts.parser()
				.setSigningKey(publicKey)
				.parseClaimsJws(idToken)
				.getBody();
		} catch (ExpiredJwtException e) {
			throw new IllegalArgumentException("Apple OAuth 로그인 중 Identity Token 유효기간이 만료됐습니다.");
		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			throw new IllegalArgumentException("Apple OAuth Identity Token 값이 올바르지 않습니다.");
		}
	}
}
