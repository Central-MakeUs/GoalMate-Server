package com.goalmate.api.v2.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.domain.menteeGoal.TodoProgress;

public record ParticipationResponse(
	Long id,
	String title,
	String topic,
	String menteeName,
	String mentorName,
	LocalDate startDate,
	LocalDate endDate,
	String mentorLetter,
	Integer todayTodoCount,
	Integer todayCompletedCount,
	Integer todayRemainingCount,
	Integer totalTodoCount,
	Integer totalCompletedCount,
	String participationStatus,
	Long commentRoomId,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static ParticipationResponse of(
		MenteeGoalEntity participation,
		TodoProgress todoProgress
	) {
		return new ParticipationResponse(
			participation.getId(),
			participation.getGoal().getTitle(),
			participation.getGoal().getTopic(),
			participation.getMentee().getName(),
			participation.getGoal().getMentor().getName(),
			participation.getStartDate(),
			participation.getEndDate(),
			participation.getMentorLetter(),
			todoProgress.getTodayCount(),
			todoProgress.getTodayCompletedCount(),
			todoProgress.getTodayCount() - todoProgress.getTodayCompletedCount(),
			todoProgress.getTotalCount(),
			todoProgress.getTotalCompletedCount(),
			participation.getStatus().name(),
			participation.getCommentRoomId(),
			participation.getCreatedAt(),
			participation.getUpdatedAt()
		);
	}
}
