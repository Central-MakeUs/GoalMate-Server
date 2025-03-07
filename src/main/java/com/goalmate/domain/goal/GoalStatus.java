package com.goalmate.domain.goal;

public enum GoalStatus {
	OPEN("OPEN"),
	CLOSED("CLOSED"),
	DISABLED("DISABLED");
	
	private final String value;

	GoalStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
