package com.goalmate.security.oauth.oidc;

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
public class OIDCJwtParser {
	private static final int HEADER_INDEX = 0;
	private static final String IDENTITY_TOKEN_VALUE_DELIMITER = "\\.";

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public Map<String, String> parseHeaders(String identityToken) {
		try {
			String encodedHeader = identityToken.split(IDENTITY_TOKEN_VALUE_DELIMITER)[HEADER_INDEX];
			String decodedHeader = new String(Base64.getUrlDecoder().decode(encodedHeader));
			return OBJECT_MAPPER.readValue(decodedHeader, Map.class);
		} catch (JsonProcessingException | ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Error parsing identity token: " + identityToken, e);
		}
	}

	public Claims parsePublicKeyAndGetClaims(String identityToken, PublicKey publicKey) {
		try {
			return Jwts.parser()
				.verifyWith(publicKey)
				.build()
				.parseSignedClaims(identityToken)
				.getPayload();
		} catch (ExpiredJwtException e) {
			throw new IllegalArgumentException("Expired or invalid JWT token");
		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid JWT token");
		}
	}
}
