package com.goalmate.security;

public record LoginResult(
	String accessToken,
	String refreshToken,
	boolean isPending
) {
}
