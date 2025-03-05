package com.goalmate.api.v2.dto.response.goal;

import java.util.List;

import com.goalmate.domain.goal.ContentImageEntity;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.goal.GoalStatus;
import com.goalmate.domain.goal.ThumbnailImageEntity;

public record GoalDetailResponse(
	Long id,
	String title,
	String description,
	String topic,
	Integer period,
	Integer dailyDuration,
	Integer participantsLimit,
	Integer currentParticipants,
	boolean isClosingSoon,
	GoalStatus goalStatus,
	Long mentorId,
	String mentorName,
	List<MidObjectiveResponse> midObjectives,
	List<WeeklyObjectiveResponse> weeklyObjectives,
	List<String> thumbnailImageUrls,
	List<String> contentImageUrls
) {
	public static GoalDetailResponse from(GoalEntity goal) {
		return new GoalDetailResponse(
			goal.getId(),
			goal.getTitle(),
			goal.getDescription(),
			goal.getTopic(),
			goal.getPeriod(),
			goal.getDailyDuration(),
			goal.getParticipantsLimit(),
			goal.getCurrentParticipants(),
			goal.isClosingSoon(),
			goal.getGoalStatus(),
			goal.getMentor().getId(),
			goal.getMentor().getName(),
			goal.getMidObjective().stream().map(MidObjectiveResponse::from).toList(),
			goal.getWeeklyObjective().stream().map(WeeklyObjectiveResponse::from).toList(),
			goal.getThumbnailImages().stream().map(ThumbnailImageEntity::getImageUrl).toList(),
			goal.getContentImages().stream().map(ContentImageEntity::getImageUrl).toList()
		);
	}
}
