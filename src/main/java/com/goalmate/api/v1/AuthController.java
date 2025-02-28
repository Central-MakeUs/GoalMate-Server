package com.goalmate.api.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.AuthApi;
import com.goalmate.api.model.LoginAdminRequest;
import com.goalmate.api.model.LoginMentorRequest;
import com.goalmate.api.model.LoginResponse;
import com.goalmate.api.model.OAuthRequest;
import com.goalmate.api.model.ReissueRequest;
import com.goalmate.security.jwt.LoginResult;
import com.goalmate.security.jwt.TokenPair;
import com.goalmate.security.user.CurrentUserContext;
import com.goalmate.security.user.SecurityUtil;
import com.goalmate.service.AuthService;
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
	public ResponseEntity loginAdmin(LoginAdminRequest loginAdminRequest) {
		TokenPair tokenPair = authService.loginAdmin(loginAdminRequest);
		return ResponseEntity.ok(ApiResponse.success(tokenPair.toLoginResponse()));
	}

	@Override
	public ResponseEntity loginMentor(LoginMentorRequest loginMentorRequest) {
		TokenPair tokenPair = authService.loginMentor(loginMentorRequest);
		return ResponseEntity.ok(ApiResponse.success(tokenPair.toLoginResponse()));
	}

	@Override
	public ResponseEntity logout() {
		return ResponseEntity.ok(ApiResponse.success());
	}

	@Override
	public ResponseEntity reissue(ReissueRequest reissueRequest) {
		TokenPair tokenPair = authService.reissue(reissueRequest.getRefreshToken());
		return ResponseEntity.ok(ApiResponse.success(tokenPair.toLoginResponse()));
	}

	@Override
	public ResponseEntity validateToken() {
		return ResponseEntity.ok(ApiResponse.success());
	}

	@Override
	public ResponseEntity withdraw() {
		CurrentUserContext user = SecurityUtil.getCurrentUser();
		authService.deleteUser(user);
		return ResponseEntity.ok(ApiResponse.success());
	}

	// for test
	@PostMapping("/auth/login/test")
	public ResponseEntity test() {
		return ResponseEntity.ok(ApiResponse.success(authService.getTestUser()));
	}
}
