package com.goalmate.domain.goal;

public enum GoalStatus {
	UPCOMING("UPCOMING"),
	OPEN("OPEN"),
	CLOSED("CLOSED");
	
	private final String value;

	GoalStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
