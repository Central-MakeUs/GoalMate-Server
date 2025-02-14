package com.goalmate.security.util;

import com.goalmate.domain.mentee.Role;

public record CurrentUserContext(
	Long userId,
	Role userRole
) {
}
