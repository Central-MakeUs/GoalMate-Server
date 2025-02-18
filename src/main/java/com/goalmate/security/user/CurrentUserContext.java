package com.goalmate.security.user;

import com.goalmate.domain.mentee.Role;

public record CurrentUserContext(
	Long userId,
	Role userRole
) {
}
