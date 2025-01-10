package com.goalmate.oauth.apple;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplePublicKey {

	private String kty;
	private String kid;
	private String use;
	private String alg;
	private String n;
	private String e;

	@Override
	public String toString() {
		return "ApplePublicKeyDTO{" +
			"kty='" + kty + '\'' +
			", kid='" + kid + '\'' +
			", use='" + use + '\'' +
			", alg='" + alg + '\'' +
			", n='" + n + '\'' +
			", e='" + e + '\'' +
			'}';
	}
}
