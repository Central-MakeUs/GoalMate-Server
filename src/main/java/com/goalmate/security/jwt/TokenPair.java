package com.goalmate.security.jwt;

import com.goalmate.api.model.LoginResponse;

public record TokenPair(String accessToken, String refreshToken) {
	public LoginResponse toLoginResponse() {
		LoginResponse response = new LoginResponse();
		response.setAccessToken(accessToken);
		response.setRefreshToken(refreshToken);
		return response;
	}
}
