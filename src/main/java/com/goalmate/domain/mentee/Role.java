package com.goalmate.domain.mentee;

public enum Role {
	ROLE_ADMIN("ADMIN"),
	ROLE_MENTEE("MENTEE"),
	ROLE_MENTOR("MENTOR");

	private final String value;

	Role(String value) {
		this.value = value;
	}

	public String getValue() {
		// DTO 에서만 사용
		return value;
	}
}
