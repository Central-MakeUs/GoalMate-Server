package com.goalmate.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.goalmate.security.jwt.UserDetailsImpl;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;

public class SecurityUtil {

	private SecurityUtil() {
		// Utility 클래스이므로 인스턴스화 방지
	}

	public static Long getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 인증 객체가 null이거나 인증되지 않은 상태일 수 있으므로 예외 처리
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new CoreApiException(ErrorType.UNAUTHORIZED);
		}

		// principal이 우리가 원하는 UserDetails 구현체인지 확인
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof UserDetailsImpl)) {
			throw new CoreApiException(ErrorType.UNAUTHORIZED);
		}

		// UserDetailsImpl로 캐스팅 후 ID 반환
		UserDetailsImpl userDetails = (UserDetailsImpl)principal;
		return userDetails.getId();
	}
}
