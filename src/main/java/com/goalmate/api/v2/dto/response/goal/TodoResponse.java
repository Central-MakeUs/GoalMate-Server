package com.goalmate.api.v2.dto.response.goal;

import com.goalmate.domain.goal.DailyTodoEntity;

public record TodoResponse(
	Long id,
	String description,
	String mentorTip,
	Integer dayNumber,
	Integer estimatedMinutes
) {
	public static TodoResponse from(DailyTodoEntity todo) {
		return new TodoResponse(
			todo.getId(),
			todo.getDescription(),
			todo.getMentorTip(),
			todo.getDayNumber(),
			todo.getEstimatedMinutes()
		);
	}
}
