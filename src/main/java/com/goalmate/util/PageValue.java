package com.goalmate.util;

public enum PageValue {
	DEFAULT_PAGE(1),
	DEFAULT_SIZE(10);

	private final int value;

	PageValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
