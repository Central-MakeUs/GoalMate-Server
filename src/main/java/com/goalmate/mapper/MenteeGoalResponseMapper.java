package com.goalmate.mapper;

import java.util.List;

import com.goalmate.api.model.GoalSummaryResponse;
import com.goalmate.api.model.MenteeGoalProgressResponse;
import com.goalmate.api.model.MenteeGoalStatusEnum;
import com.goalmate.api.model.MenteeGoalSummaryPagingResponse;
import com.goalmate.api.model.MenteeGoalSummaryResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;

public class MenteeGoalResponseMapper {

	public static MenteeGoalSummaryResponse mapToSummaryResponse(GoalEntity goal, MenteeGoalEntity menteeGoal,
		MenteeGoalProgressResponse progressResponse) {
		GoalSummaryResponse goalSummary = GoalResponseMapper.mapToSummaryResponse(goal);

		MenteeGoalSummaryResponse response = new MenteeGoalSummaryResponse();
		response.setId(menteeGoal.getId().intValue());
		response.setTitle(goalSummary.getTitle());
		response.setTopic(goalSummary.getTopic());
		response.setDescription(goalSummary.getDescription());
		response.setPeriod(goalSummary.getPeriod());
		response.setPrice(goalSummary.getPrice());
		response.setDiscountPrice(goalSummary.getDiscountPrice());
		response.setParticipantsLimit(goalSummary.getParticipantsLimit());
		response.setCurrentParticipants(goalSummary.getCurrentParticipants());
		response.setGoalStatus(goalSummary.getGoalStatus());
		response.setMentorName(goalSummary.getMentorName());
		response.setCreatedAt(goalSummary.getCreatedAt());
		response.setUpdatedAt(goalSummary.getUpdatedAt());
		response.setMainImage(goalSummary.getMainImage());
		response.setTotalTodos(progressResponse.getTotalTodos());
		response.setCompletedTodos(progressResponse.getCompletedTodos());
		response.setMenteeGoalStatus(progressResponse.getMenteeGoalStatus());
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

	public static MenteeGoalProgressResponse mapToProgressResponse(int completedCount, int totalCount,
		String goalStatus) {
		MenteeGoalProgressResponse response = new MenteeGoalProgressResponse();
		response.setTotalTodos(totalCount);
		response.setCompletedTodos(completedCount);
		response.setMenteeGoalStatus(MenteeGoalStatusEnum.valueOf(goalStatus));
		return response;
	}
}
