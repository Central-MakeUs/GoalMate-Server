package com.goalmate.api.v2.dto.response.goal;

import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.goal.GoalStatus;
import com.goalmate.domain.goal.ThumbnailImageEntity;

public record GoalSummaryResponse(
	Long id,
	String title,
	String topic,
	Integer period,
	Integer dailyDuration,
	Integer participantsLimit,
	Integer currentParticipants,
	boolean isClosingSoon,
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
			goal.getPeriod(),
			goal.getDailyDuration(),
			goal.getParticipantsLimit(),
			goal.getCurrentParticipants(),
			goal.isClosingSoon(),
			goal.getGoalStatus(),
			goal.getMentor().getId(),
			goal.getMentor().getName(),
			goal.getThumbnailImages().stream().findAny().map(ThumbnailImageEntity::getImageUrl).orElse(null)
		);
	}
}
