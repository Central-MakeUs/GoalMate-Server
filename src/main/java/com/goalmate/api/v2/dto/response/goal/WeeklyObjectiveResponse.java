package com.goalmate.api.v2.dto.response.goal;

public record WeeklyObjectiveResponse(
	int weekNumber,
	String description
) {
	public static WeeklyObjectiveResponse from(com.goalmate.domain.goal.WeeklyObjectiveEntity weeklyObjective) {
		return new WeeklyObjectiveResponse(
			weeklyObjective.getWeekNumber(),
			weeklyObjective.getDescription()
		);
	}
}
