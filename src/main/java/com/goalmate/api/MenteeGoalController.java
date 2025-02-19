package com.goalmate.api;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.model.MenteeGoalDailyDetailResponse;
import com.goalmate.api.model.MenteeGoalSummaryPagingResponse;
import com.goalmate.api.model.MenteeGoalTodoResponse;
import com.goalmate.api.model.MenteeGoalWeeklyProgressResponse;
import com.goalmate.api.model.RemainingTodosResponse;
import com.goalmate.api.model.UpdateTodoStatusRequest;
import com.goalmate.security.user.SecurityUtil;
import com.goalmate.service.MenteeGoalService;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MenteeGoalController implements MenteeGoalApi {
	private final MenteeGoalService menteeGoalService;

	@Override
	public ResponseEntity getMenteeGoals(Integer page, Integer size) {
		final Long menteeId = SecurityUtil.getCurrentUserId();
		final MenteeGoalSummaryPagingResponse response = menteeGoalService.getMenteeGoalSummaries(menteeId, page, size);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity getMenteeGoalWeeklyProgress(
		Long menteeGoalId,
		LocalDate date
	) {
		if (Optional.ofNullable(date).isEmpty()) {
			date = LocalDate.now();
		}
		final MenteeGoalWeeklyProgressResponse response = menteeGoalService.getWeeklyProgress(menteeGoalId, date);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity hasRemainingTodosToday() {
		final Long menteeId = SecurityUtil.getCurrentUserId();
		final RemainingTodosResponse response = new RemainingTodosResponse();
		response.setHasRemainingTodosToday(menteeGoalService.hasRemainingTodosToday(menteeId));
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity getMenteeGoalDailyDetails(Long menteeGoalId, LocalDate date) {
		if (Optional.ofNullable(date).isEmpty()) {
			date = LocalDate.now();
		}
		MenteeGoalDailyDetailResponse response = menteeGoalService.getMenteeGoalDailyDetails(
			menteeGoalId,
			date
		);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity updateTodoStatus(
		Long menteeGoalId,
		Long todoId,
		UpdateTodoStatusRequest request
	) {
		MenteeGoalTodoResponse response = menteeGoalService.updateTodoStatus(
			menteeGoalId,
			todoId,
			request.getTodoStatus().getValue()
		);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
