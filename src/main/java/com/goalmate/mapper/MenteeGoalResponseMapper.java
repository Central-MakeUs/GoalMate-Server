package com.goalmate.mapper;

import java.util.List;

import com.goalmate.api.model.GoalSummaryResponse;
import com.goalmate.api.model.MenteeGoalStatusEnum;
import com.goalmate.api.model.MenteeGoalSummaryPagingResponse;
import com.goalmate.api.model.MenteeGoalSummaryResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.domain.menteeGoal.TodoProgress;

public class MenteeGoalResponseMapper {

	public static MenteeGoalSummaryResponse mapToSummaryResponse(
		MenteeGoalEntity menteeGoal,
		TodoProgress progress,
		boolean hasNewComments) {
		GoalEntity goal = menteeGoal.getGoalEntity();
		GoalSummaryResponse goalSummary = GoalResponseMapper.mapToSummaryResponse(goal);

		MenteeGoalSummaryResponse response = new MenteeGoalSummaryResponse();
		response.setId(menteeGoal.getId().intValue());

		response.setId(menteeGoal.getId().intValue()); // 멘티 목표 ID
		response.setTitle(goalSummary.getTitle());
		response.setTopic(goalSummary.getTopic());
		response.setMentorName(goalSummary.getMentorName());
		response.setMainImage(goalSummary.getMainImage());

		response.setStartDate(menteeGoal.getStartDate());
		response.setEndDate(menteeGoal.getEndDate());

		response.setTodayTodoCount(progress.getTodayCount());
		response.setTodayCompletedCount(progress.getTodayCompletedCount());
		response.setDailyRemainingCount(progress.getTodayCount() - progress.getTodayCompletedCount());
		response.setTotalTodoCount(progress.getTotalCount());
		response.setTotalCompletedCount(progress.getTotalCompletedCount());
		response.setHasNewComments(hasNewComments);

		response.setMenteeGoalStatus(MenteeGoalStatusEnum.valueOf(menteeGoal.getStatus().getValue()));

		response.setCreatedAt(menteeGoal.getCreatedAt().atOffset(java.time.ZoneOffset.UTC));
		response.setUpdatedAt(menteeGoal.getUpdatedAt().atOffset(java.time.ZoneOffset.UTC));
		return response;
	}

	public static MenteeGoalSummaryPagingResponse mapToSummaryPagingResponse(
		List<MenteeGoalSummaryResponse> menteeGoals,
		PageResponse pageResponse) {
		MenteeGoalSummaryPagingResponse response = new MenteeGoalSummaryPagingResponse();
		response.setMenteeGoals(menteeGoals);
		response.setPage(pageResponse);
		return response;
	}
}
