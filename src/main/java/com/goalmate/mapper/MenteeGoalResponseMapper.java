package com.goalmate.mapper;

import java.time.LocalDate;
import java.util.List;

import com.goalmate.api.model.GoalSummaryResponse;
import com.goalmate.api.model.MenteeGoalDailyDetailResponse;
import com.goalmate.api.model.MenteeGoalDailyProgressResponse;
import com.goalmate.api.model.MenteeGoalStatusEnum;
import com.goalmate.api.model.MenteeGoalSummaryPagingResponse;
import com.goalmate.api.model.MenteeGoalSummaryResponse;
import com.goalmate.api.model.MenteeGoalTodoResponse;
import com.goalmate.api.model.MenteeGoalWeeklyProgressResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalDailyTodoEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.domain.menteeGoal.TodoProgress;

public class MenteeGoalResponseMapper {

	public static MenteeGoalSummaryResponse mapToSummaryResponse(
		MenteeGoalEntity menteeGoal,
		TodoProgress progress) {
		GoalEntity goal = menteeGoal.getGoal();
		GoalSummaryResponse goalSummary = GoalResponseMapper.mapToSummaryResponse(goal);

		MenteeGoalSummaryResponse response = new MenteeGoalSummaryResponse();
		response.setId(menteeGoal.getId());
		response.setGoalId(goal.getId());
		response.setTitle(goalSummary.getTitle());
		response.setTopic(goalSummary.getTopic());
		response.setMentorName(goalSummary.getMentorName());
		response.setMainImage(goalSummary.getMainImage());

		response.setStartDate(menteeGoal.getStartDate());
		response.setEndDate(menteeGoal.getEndDate());

		response.setTodayTodoCount(progress.getTodayCount());
		response.setTodayCompletedCount(progress.getTodayCompletedCount());
		response.setTodayRemainingCount(progress.getTodayCount() - progress.getTodayCompletedCount());

		response.setTotalTodoCount(progress.getTotalCount());
		response.setTotalCompletedCount(progress.getTotalCompletedCount());
		response.setMentorLetter(menteeGoal.getMentorLetter());

		response.setCommentRoomId(menteeGoal.getCommentRoomId());
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

	public static MenteeGoalDailyDetailResponse mapToDailyDetailResponse(
		MenteeGoalSummaryResponse summary,
		LocalDate date,
		List<MenteeGoalTodoResponse> todos) {
		MenteeGoalDailyDetailResponse response = new MenteeGoalDailyDetailResponse();
		response.setMenteeGoal(summary);
		response.setDate(date);
		response.setTodos(todos);
		return response;
	}

	public static MenteeGoalTodoResponse mapToTodoResponse(MenteeGoalDailyTodoEntity todo) {
		MenteeGoalTodoResponse response = new MenteeGoalTodoResponse();
		response.setId(todo.getId());
		response.setDescription(todo.getDescription());
		response.setEstimatedMinutes(todo.getEstimatedMinutes());
		response.setTodoDate(todo.getTodoDate());
		response.setMentorTip(todo.getMentorTip());
		response.setTodoStatus(todo.getStatus().getValue());
		return response;
	}

	public static MenteeGoalDailyProgressResponse mapToDailyProgressResponse(
		LocalDate date,
		int dailyTodoCount,
		int completedDailyTodoCount,
		boolean isValid
	) {
		MenteeGoalDailyProgressResponse response = new MenteeGoalDailyProgressResponse();
		response.setDate(date);
		response.dayOfWeek(date.getDayOfWeek().toString());
		response.setDailyTodoCount(dailyTodoCount);
		response.setCompletedDailyTodoCount(completedDailyTodoCount);
		response.setIsValid(isValid);
		return response;
	}

	public static MenteeGoalWeeklyProgressResponse mapToWeeklyProgressResponse(
		boolean hasLastWeek,
		boolean hasNextWeek,
		List<MenteeGoalDailyProgressResponse> progress) {
		MenteeGoalWeeklyProgressResponse response = new MenteeGoalWeeklyProgressResponse();
		response.setHasLastWeek(hasLastWeek);
		response.setHasNextWeek(hasNextWeek);
		response.setProgress(progress);
		return response;
	}
}
