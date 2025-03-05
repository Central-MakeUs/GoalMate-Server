package com.goalmate.api.v2.dto.request;

import jakarta.validation.constraints.NotNull;

public record GoalUpdateRequest(
	@NotNull String title,
	@NotNull String topic,
	@NotNull Integer period,
	@NotNull Integer dailyDuration,
	@NotNull Integer participantsLimit,
	@NotNull String description
	// @NotNull @Valid List<MidObjectiveRequest> midObjectives,
	// @NotNull @Valid List<WeeklyObjectiveRequest> weeklyObjectives,
	// @NotNull @Valid List<ImageRequest> thumbnailImages,
	// @NotNull @Valid List<ImageRequest> contentImages,
	// @NotNull @Valid List<TodoRequest> todoList
) {
}
