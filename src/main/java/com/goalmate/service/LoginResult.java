package com.goalmate.service;

public record LoginResult(
	String accessToken,
	String refreshToken,
	boolean isPending
) {
}
