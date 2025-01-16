package com.goalmate.security.oauth.oidc;

import java.util.List;

public record OIDCPublicKeys(List<OIDCPublicKey> keys) {

	public OIDCPublicKey getMatchesKey(String alg, String kid) {
		return this.keys
			.stream()
			.filter(k -> k.getAlg().equals(alg) && k.getKid().equals(kid))
			.findFirst()
			.orElseThrow(
				() -> new IllegalArgumentException("No matching OIDC public key found for algorithm and key ID"));
	}
}
