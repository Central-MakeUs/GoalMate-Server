package com.goalmate.domain.menteeGoal;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TodoProgress {
	private int totalCount;
	private int totalCompletedCount;
	private int todayCount;
	private int todayCompletedCount;

	@Builder
	public TodoProgress(int totalCount, int totalCompletedCount, int todayCount, int todayCompletedCount) {
		this.totalCount = totalCount;
		this.totalCompletedCount = totalCompletedCount;
		this.todayCount = todayCount;
		this.todayCompletedCount = todayCompletedCount;
	}
}
