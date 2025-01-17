package com.goalmate.domain.mentee;

public enum Role {
	ROLE_ADMIN("ADMIN"),
	ROLE_MENTEE("ROLE_MENTEE"),
	ROLE_MENTOR("ROLE_MENTOR");

	private final String value;

	Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
