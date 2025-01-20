package com.goalmate.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.goalmate.api.model.GoalDetailResponse;
import com.goalmate.api.model.GoalStatusEnum;
import com.goalmate.api.model.GoalSummaryPagingResponse;
import com.goalmate.api.model.GoalSummaryResponse;
import com.goalmate.api.model.MidObjectiveResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.api.model.WeeklyObjectiveResponse;
import com.goalmate.domain.goal.ContentImageEntity;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.goal.ThumbnailImageEntity;

public class GoalResponseMapper {
	public static GoalSummaryResponse mapToSummaryResponse(GoalEntity goal) {
		GoalSummaryResponse summary = new GoalSummaryResponse();
		summary.setId(goal.getId().intValue());
		summary.setTitle(goal.getTitle());
		summary.setTopic(goal.getTopic());
		summary.setDescription(goal.getDescription());
		summary.setPeriod(goal.getPeriod());
		summary.setPrice(goal.getPrice());
		summary.setDiscountPrice(goal.getDiscountPrice());
		summary.setParticipantsLimit(goal.getParticipantsLimit());
		summary.setCurrentParticipants(goal.getCurrentParticipants());
		summary.setGoalStatus(GoalStatusEnum.fromValue(goal.getGoalStatus().getValue()));
		summary.setMentorName(goal.getMentorEntity().getName());
		summary.setCreatedAt(goal.getCreatedAt().atOffset(java.time.ZoneOffset.UTC));
		summary.setUpdatedAt(goal.getUpdatedAt().atOffset(java.time.ZoneOffset.UTC));
		summary.setMainImage(goal.getThumbnailImages().stream()
			.findFirst().orElse(null).getImageUrl());
		return summary;
	}

	public static GoalDetailResponse mapToDetailResponse(GoalEntity goal) {
		GoalDetailResponse detail = new GoalDetailResponse();
		detail.setId(goal.getId().intValue());
		detail.setTitle(goal.getTitle());
		detail.setTopic(goal.getTopic());
		detail.setDescription(goal.getDescription());
		detail.setPeriod(goal.getPeriod());
		detail.setPrice(goal.getPrice());
		detail.setDiscountPrice(goal.getDiscountPrice());
		detail.setParticipantsLimit(goal.getParticipantsLimit());
		detail.setCurrentParticipants(goal.getCurrentParticipants());
		detail.setGoalStatus(GoalStatusEnum.fromValue(goal.getGoalStatus().getValue()));
		detail.setMentorName(goal.getMentorEntity().getName());
		detail.setCreatedAt(goal.getCreatedAt().atOffset(java.time.ZoneOffset.UTC));
		detail.setUpdatedAt(goal.getUpdatedAt().atOffset(java.time.ZoneOffset.UTC));
		// ThumbnailImages
		detail.setThumbnailImages(goal.getThumbnailImages().stream()
			.map(ThumbnailImageEntity::getImageUrl)
			.collect(Collectors.toList()));
		// ContentImages
		detail.setContentImages(goal.getContentImages().stream()
			.map(ContentImageEntity::getImageUrl)
			.collect(Collectors.toList()));
		detail.setThumbnailImages(goal.getThumbnailImages().stream()
			.map(ThumbnailImageEntity::getImageUrl)
			.collect(Collectors.toList()));
		// Weekly Objectives
		detail.setWeeklyObjectives(goal.getWeeklyObjective().stream()
			.map(weeklyObjective -> {
				WeeklyObjectiveResponse weeklyObjectiveResponse = new WeeklyObjectiveResponse();
				weeklyObjectiveResponse.setWeekNumber(weeklyObjective.getWeekNumber());
				weeklyObjectiveResponse.setDescription(weeklyObjective.getDescription());
				return weeklyObjectiveResponse;
			})
			.collect(Collectors.toList()));
		// Mid-Objectives
		detail.setMidObjectives(goal.getMidObjective().stream()
			.map(midObjective -> {
				MidObjectiveResponse midObjectiveResponse = new MidObjectiveResponse();
				midObjectiveResponse.setDescription(midObjective.getDescription());
				return midObjectiveResponse;
			})
			.collect(Collectors.toList()));
		// Daily Todos
		detail.setDailyTodos(goal.getDailyTodos().stream()
			.map(dailyTodo -> {
				com.goalmate.api.model.DailyTodoResponse dailyTodoResponse = new com.goalmate.api.model.DailyTodoResponse();
				dailyTodoResponse.setTodoDate(dailyTodo.getTodoDate());
				dailyTodoResponse.setDescription(dailyTodo.getDescription());
				return dailyTodoResponse;
			})
			.collect(Collectors.toList()));
		return detail;
	}

	public static GoalSummaryPagingResponse mapToSummaryPagingResponse(
		List<GoalSummaryResponse> summaries,
		PageResponse pageResponse) {

		GoalSummaryPagingResponse response = new GoalSummaryPagingResponse();
		response.setGoals(summaries);
		response.setPage(pageResponse);
		return response;
	}
}
