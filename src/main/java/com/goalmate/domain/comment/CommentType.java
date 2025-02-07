package com.goalmate.domain.comment;

public enum CommentType {
	DAILY("DAILY"),
	FINAL("FINAL");

	private final String value;

	CommentType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
