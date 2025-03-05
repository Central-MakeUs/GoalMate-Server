package com.goalmate.api.v2.dto.response;

import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.goal.GoalStatus;
import com.goalmate.domain.goal.ThumbnailImageEntity;

public record GoalSummaryResponse(
	Long id,
	String title,
	String topic,
	String description,
	Integer period,
	Integer dailyDuration,
	Integer price,
	Integer discountPrice,
	Integer participantsLimit,
	Integer currentParticipants,
	GoalStatus goalStatus,
	Long mentorId,
	String mentorName,
	String thumbnailImageUrl
) {
	public static GoalSummaryResponse from(GoalEntity goal) {
		return new GoalSummaryResponse(
			goal.getId(),
			goal.getTitle(),
			goal.getTopic(),
			goal.getDescription(),
			goal.getPeriod(),
			goal.getDailyDuration(),
			goal.getPrice(),
			goal.getDiscountPrice(),
			goal.getParticipantsLimit(),
			goal.getCurrentParticipants(),
			goal.getGoalStatus(),
			goal.getMentor().getId(),
			goal.getMentor().getName(),
			goal.getThumbnailImages().stream().findAny().map(ThumbnailImageEntity::getImageUrl).orElse(null)
		);
	}
}
