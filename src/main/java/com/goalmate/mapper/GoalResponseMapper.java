package com.goalmate.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.goalmate.api.model.GoalDetailResponse;
import com.goalmate.api.model.GoalSummaryPagingResponse;
import com.goalmate.api.model.GoalSummaryResponse;
import com.goalmate.api.model.MidObjectiveResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.api.model.WeeklyObjectiveResponse;
import com.goalmate.domain.goal.ContentImageEntity;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.goal.ThumbnailImageEntity;

public class GoalResponseMapper {
	public static GoalSummaryResponse mapToSummaryResponse(GoalEntity goalEntity) {
		if (goalEntity == null) {
			return null;
		}

		GoalSummaryResponse response = new GoalSummaryResponse();
		response.setId(goalEntity.getId().intValue());
		response.setName(goalEntity.getName());
		response.setTopic(goalEntity.getTopic());
		response.setDescription(goalEntity.getDescription());
		response.setPeriod(goalEntity.getPeriod());
		response.setStartDate(goalEntity.getStartDate());
		response.setEndDate(goalEntity.getEndDate());
		response.setPrice(goalEntity.getPrice());
		response.setDiscountPrice(goalEntity.getDiscountPrice());
		response.setParticipantsLimit(goalEntity.getParticipantsLimit());
		response.setFreeParticipantsLimit(goalEntity.getFreeParticipantsLimit());
		response.setGoalStatus(goalEntity.getGoalStatus().name());
		response.setMainImage(goalEntity.getThumbnailImages().stream()
			.findFirst().orElse(null).getImageUrl());
		response.setCreatedAt(goalEntity.getCreatedAt().atOffset(java.time.ZoneOffset.UTC));
		response.setUpdatedAt(goalEntity.getUpdatedAt().atOffset(java.time.ZoneOffset.UTC));

		return response;
	}

	public static GoalSummaryPagingResponse mapToSummaryPagingResponse(Page<GoalEntity> goals) {
		if (goals == null) {
			return null;
		}

		List<GoalSummaryResponse> content = goals.getContent().stream()
			.map(GoalResponseMapper::mapToSummaryResponse)
			.collect(Collectors.toList());

		PageResponse page = PageResponseMapper.mapToPageResponse(goals);

		GoalSummaryPagingResponse response = new GoalSummaryPagingResponse();
		response.setGoals(content);
		response.setPage(page);
		return response;
	}

	public static GoalDetailResponse mapToDetailResponse(GoalEntity goalEntity) {
		if (goalEntity == null) {
			return null;
		}

		GoalDetailResponse response = new GoalDetailResponse();
		response.setId(goalEntity.getId().intValue());
		response.setName(goalEntity.getName());
		response.setTopic(goalEntity.getTopic());
		response.setDescription(goalEntity.getDescription());
		response.setPeriod(goalEntity.getPeriod());
		response.setStartDate(goalEntity.getStartDate());
		response.setEndDate(goalEntity.getEndDate());
		response.setPrice(goalEntity.getPrice());
		response.setDiscountPrice(goalEntity.getDiscountPrice());
		response.setParticipantsLimit(goalEntity.getParticipantsLimit());
		response.setFreeParticipantsLimit(goalEntity.getFreeParticipantsLimit());
		response.setGoalStatus(goalEntity.getGoalStatus().name());
		response.setThumbnailImages(goalEntity.getThumbnailImages().stream()
			.map(ThumbnailImageEntity::getImageUrl)
			.collect(Collectors.toList()));
		// Images
		response.setContentImages(goalEntity.getContentImages().stream()
			.map(ContentImageEntity::getImageUrl)
			.collect(Collectors.toList()));
		response.setThumbnailImages(goalEntity.getThumbnailImages().stream()
			.map(ThumbnailImageEntity::getImageUrl)
			.collect(Collectors.toList()));
		// Objectives
		response.setWeeklyObjectives(goalEntity.getWeeklyObjective().stream()
			.map(weeklyObjective -> {
				WeeklyObjectiveResponse weeklyObjectiveResponse = new WeeklyObjectiveResponse();
				weeklyObjectiveResponse.setWeekNumber(weeklyObjective.getWeekNumber());
				weeklyObjectiveResponse.setDescription(weeklyObjective.getDescription());
				return weeklyObjectiveResponse;
			})
			.collect(Collectors.toList()));
		response.setMidObjectives(goalEntity.getMidObjective().stream()
			.map(midObjective -> {
				MidObjectiveResponse midObjectiveResponse = new MidObjectiveResponse();
				midObjectiveResponse.setDescription(midObjective.getDescription());
				return midObjectiveResponse;
			})
			.collect(Collectors.toList()));
		// Daily Todos
		response.setDailyTodos(goalEntity.getDailyTodos().stream()
			.map(dailyTodo -> {
				com.goalmate.api.model.DailyTodoResponse dailyTodoResponse = new com.goalmate.api.model.DailyTodoResponse();
				dailyTodoResponse.setTodoDate(dailyTodo.getTodoDate());
				dailyTodoResponse.setDescription(dailyTodo.getDescription());
				return dailyTodoResponse;
			})
			.collect(Collectors.toList()));
		response.setCreatedAt(goalEntity.getCreatedAt().atOffset(java.time.ZoneOffset.UTC));
		response.setUpdatedAt(goalEntity.getUpdatedAt().atOffset(java.time.ZoneOffset.UTC));

		return response;
	}

}
