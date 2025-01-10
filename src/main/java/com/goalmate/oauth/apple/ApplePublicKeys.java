package com.goalmate.oauth.apple;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplePublicKeys {

	private List<ApplePublicKey> keys;

	public ApplePublicKey getMatchesKey(String alg, String kid) {
		return this.keys
			.stream()
			.filter(k -> k.getAlg().equals(alg) && k.getKid().equals(kid))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("apple key not found"));
	}
}
