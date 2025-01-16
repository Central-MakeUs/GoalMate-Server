package com.goalmate.security.oauth.oidc;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class OIDCPublicKeyResolver {

	private static final String SIGN_ALGORITHM_HEADER_KEY = "alg";
	private static final String KEY_ID_HEADER_KEY = "kid";
	private static final int POSITIVE_SIGN_NUMBER = 1;

	/**
	 * Resolves the appropriate PublicKey based on headers and available OIDC public keys.
	 */
	public PublicKey resolvePublicKey(Map<String, String> headers, OIDCPublicKeys oidcPublicKeys) {
		OIDCPublicKey oidcPublicKey = oidcPublicKeys.getMatchesKey(
			headers.get(SIGN_ALGORITHM_HEADER_KEY),
			headers.get(KEY_ID_HEADER_KEY)
		);
		return convertOIDCKeyToPublicKey(oidcPublicKey);
	}

	/**
	 * Converts an OIDC public key to a Java PublicKey object.
	 */
	private PublicKey convertOIDCKeyToPublicKey(OIDCPublicKey publicKey) {
		byte[] nBytes = Base64.getUrlDecoder().decode(publicKey.getN());
		byte[] eBytes = Base64.getUrlDecoder().decode(publicKey.getE());

		BigInteger n = new BigInteger(POSITIVE_SIGN_NUMBER, nBytes);
		BigInteger e = new BigInteger(POSITIVE_SIGN_NUMBER, eBytes);

		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);

		try {
			KeyFactory keyFactory = KeyFactory.getInstance(publicKey.getKty());
			return keyFactory.generatePublic(publicKeySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
			throw new RuntimeException("Failed to convert OIDC key to PublicKey", exception);
		}
	}
}
