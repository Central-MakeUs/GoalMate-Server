package com.goalmate.domain.menteeGoal;

public enum TodoStatus {
	TODO("TODO"),
	COMPLETED("COMPLETED");

	private final String value;

	TodoStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
