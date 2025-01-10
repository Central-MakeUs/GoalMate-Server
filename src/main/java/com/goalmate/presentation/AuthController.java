package com.goalmate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.AuthApi;
import com.goalmate.api.model.LoginOrSignUp200Response;
import com.goalmate.api.model.Logout200Response;
import com.goalmate.api.model.OAuthRequest;
import com.goalmate.oauth.apple.AppleOAuthUserProvider;
import com.goalmate.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthApi {
	private final AuthService authService;
	private final AppleOAuthUserProvider appleOAuthUserProvider;

	@Override
	public ResponseEntity<LoginOrSignUp200Response> loginOrSignUp(OAuthRequest oauthRequest) throws Exception {
		return AuthApi.super.loginOrSignUp(oauthRequest);
	}

	@Override
	public ResponseEntity<Logout200Response> logout() throws Exception {
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
