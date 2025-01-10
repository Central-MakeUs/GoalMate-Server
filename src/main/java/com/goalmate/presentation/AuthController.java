package com.goalmate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.AuthApi;
import com.goalmate.api.model.LoginRequest;
import com.goalmate.api.model.LoginWithOauth200Response;
import com.goalmate.api.model.Logout200Response;
import com.goalmate.oauth.apple.AppleOAuthUserProvider;
import com.goalmate.oauth.apple.ApplePlatformMemberResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthApi {
	private final AppleOAuthUserProvider appleOAuthUserProvider;

	@Override
	public ResponseEntity<LoginWithOauth200Response> loginWithOauth(LoginRequest loginRequest) {
		String authorizationCode = loginRequest.getAuthorizationCode();
		String provider = loginRequest.getProvider().toString();
		ApplePlatformMemberResponse applePlatformMember = appleOAuthUserProvider.getApplePlatformMember(
			authorizationCode);
		return new ResponseEntity(applePlatformMember, HttpStatus.OK);
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
