package com.goalmate.security.user;

import com.goalmate.domain.mentee.Role;

public record CurrentUserContext(
	Long userId,
	Role userRole
) {
	public boolean isMentee() {
		return userRole == Role.ROLE_MENTEE;
	}

	public boolean isMentor() {
		return userRole == Role.ROLE_MENTOR;
	}

	public boolean isAdmin() {
		return userRole == Role.ROLE_ADMIN;
	}
}
