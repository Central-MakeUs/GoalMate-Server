// package com.goalmate;
//
// import java.util.Optional;
//
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.context.request.NativeWebRequest;
//
// import com.goalmate.api.AuthApi;
// import com.goalmate.api.model.LoginRequest;
// import com.goalmate.api.model.LoginWithOauth200Response;
// import com.goalmate.api.model.Logout200Response;
//
// @RestController
// public class AuthController implements AuthApi {
// 	@Override
// 	public Optional<NativeWebRequest> getRequest() {
// 		return AuthApi.super.getRequest();
// 	}
//
// 	@Override
// 	public ResponseEntity<LoginWithOauth200Response> loginWithOauth(LoginRequest loginRequest) throws Exception {
// 		return AuthApi.super.loginWithOauth(loginRequest);
// 	}
//
// 	@Override
// 	public ResponseEntity<Logout200Response> logout() throws Exception {
// 		return AuthApi.super.logout();
// 	}
//
// 	@Override
// 	public ResponseEntity<Void> reissue() throws Exception {
// 		return AuthApi.super.reissue();
// 	}
//
// 	@Override
// 	public ResponseEntity<Void> withdraw() throws Exception {
// 		return AuthApi.super.withdraw();
// 	}
// }
