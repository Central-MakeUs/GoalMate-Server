package com.goalmate.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.AuthApi;
import com.goalmate.api.model.LoginResponse;
import com.goalmate.api.model.OAuthRequest;
import com.goalmate.service.AuthService;
import com.goalmate.service.LoginResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthApi {
	private final AuthService authService;

	// 로직처리는 최대한 서비스에서하고, 컨트롤러에서 조합. 매핑이 복잡하다면 별도 매퍼로 넘기기
	@Override
	public ResponseEntity loginOrSignUp(OAuthRequest oauthRequest) {
		LoginResult result = authService.authenticateWithOauth(oauthRequest.getAuthorizationCode(),
			oauthRequest.getProvider().toString());
		LoginResponse response = new LoginResponse();
		response.setAccessToken(result.accessToken());
		response.setRefreshToken(result.refreshToken());
		if (result.isPending())
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		return ResponseEntity.status(HttpStatus.OK).body(response);
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
