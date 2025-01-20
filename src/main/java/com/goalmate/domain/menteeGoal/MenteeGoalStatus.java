package com.goalmate.domain.menteeGoal;

public enum MenteeGoalStatus {
	IN_PROGRESS("IN_PROGRESS"),
	COMPLETED("COMPLETED"),
	FAILED("FAILED"),
	CANCELLED("CANCELLED");

	private final String value;

	MenteeGoalStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
