package com.goalmate.api.v2.dto.request;

import com.goalmate.domain.goal.GoalStatus;

public record GoalStatusUpdateRequest(
	GoalStatus status
) {
}
