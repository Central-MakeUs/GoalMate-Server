package com.goalmate.oauth.oidc;

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
public class OIDCPublicKeyProvider {

	private static final String SIGN_ALGORITHM_HEADER_KEY = "alg";
	private static final String KEY_ID_HEADER_KEY = "kid";
	private static final int POSITIVE_SIGN_NUMBER = 1;

	public PublicKey getPublicKeyFromHeaders(Map<String, String> headers, OIDCPublicKeys OIDCPublicKeys) {
		OIDCPublicKey OIDCPublicKey =
			OIDCPublicKeys.getMatchesKey(headers.get(SIGN_ALGORITHM_HEADER_KEY), headers.get(KEY_ID_HEADER_KEY));
		return generatePublicKeyFromOIDCKey(OIDCPublicKey);
	}

	private PublicKey generatePublicKeyFromOIDCKey(OIDCPublicKey publicKey) {
		byte[] nBytes = Base64.getUrlDecoder().decode(publicKey.getN());
		byte[] eBytes = Base64.getUrlDecoder().decode(publicKey.getE());

		BigInteger n = new BigInteger(POSITIVE_SIGN_NUMBER, nBytes);
		BigInteger e = new BigInteger(POSITIVE_SIGN_NUMBER, eBytes);

		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);

		try {
			KeyFactory keyFactory = KeyFactory.getInstance(publicKey.getKty());
			return keyFactory.generatePublic(publicKeySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
			throw new RuntimeException(exception); //test
		}
	}
}
