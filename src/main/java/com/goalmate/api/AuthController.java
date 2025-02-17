package com.goalmate.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.model.LoginResponse;
import com.goalmate.api.model.OAuthRequest;
import com.goalmate.service.AuthService;
import com.goalmate.service.LoginResult;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthApi {
	private final AuthService authService;

	@Override
	public ResponseEntity loginOrSignUp(OAuthRequest oauthRequest) {
		LoginResult result = authService.authenticateWithOauth(
			oauthRequest.getIdentityToken(),
			oauthRequest.getNonce(),
			oauthRequest.getProvider().toString());
		LoginResponse response = new LoginResponse();
		response.setAccessToken(result.accessToken());
		response.setRefreshToken(result.refreshToken());
		// 회원가입: 201
		if (result.isPending())
			return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED, response));
		// 로그인: 200
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity logout() throws Exception {
		return AuthApi.super.logout();
	}

	@Override
	public ResponseEntity<Void> reissue() throws Exception {
		return AuthApi.super.reissue();
	}

	@Override
	public ResponseEntity<Void> withdraw() throws Exception {
		return AuthApi.super.withdraw();
	}

}
