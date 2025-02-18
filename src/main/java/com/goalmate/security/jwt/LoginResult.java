package com.goalmate.security.jwt;

public record LoginResult(
	String accessToken,
	String refreshToken,
	boolean isPending
) {
}
