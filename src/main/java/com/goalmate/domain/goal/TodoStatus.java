package com.goalmate.domain.goal;

public enum TodoStatus {
	TODO("TODO"),
	DONE("DONE");

	private final String value;

	TodoStatus(String value) {
		this.value = value;
	}
}
