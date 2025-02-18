package com.goalmate.security.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.goalmate.domain.mentee.Role;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;

public class SecurityUtil {

	private SecurityUtil() {
	}

	public static CurrentUserContext getCurrentUser() {
		return new CurrentUserContext(getCurrentUserId(), getCurrenUserRole());
	}

	public static Long getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = getUserDetailImpl(authentication);
		return userDetails.getId();
	}

	public static Role getCurrenUserRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = getUserDetailImpl(authentication);
		String authority = userDetails.getAuthorities().stream()
			.findFirst()
			.orElseThrow(() -> new CoreApiException(ErrorType.FORBIDDEN))
			.getAuthority();
		return Role.valueOf(authority.toUpperCase());
	}

	private static UserDetailsImpl getUserDetailImpl(Authentication authentication) {
		// 인증 객체가 null이거나 인증되지 않은 상태일 수 있으므로 예외 처리
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new CoreApiException(ErrorType.UNAUTHORIZED);
		}

		// principal이 우리가 원하는 UserDetails 구현체인지 확인
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof UserDetailsImpl)) {
			throw new CoreApiException(ErrorType.UNAUTHORIZED);
		}
		return (UserDetailsImpl)principal;
	}
}
