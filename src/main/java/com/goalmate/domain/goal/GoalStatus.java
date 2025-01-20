package com.goalmate.domain.goal;

public enum GoalStatus {
	NOT_STARTED("NOT_STARTED"),  // 곧 시작
	IN_PROGRESS("IN_PROGRESS"),  // 진행 중
	CLOSED("CLOSED");     // 마감

	private final String value;

	GoalStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
