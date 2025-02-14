package com.goalmate.api;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.model.GetMenteeGoalWeeklyProgress200Response;
import com.goalmate.api.model.MenteeGoalSummaryPagingResponse;
import com.goalmate.api.model.RemainingTodosResponse;
import com.goalmate.security.util.SecurityUtil;
import com.goalmate.service.MenteeGoalService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MenteeGoalController implements MenteeGoalApi {
	private final MenteeGoalService menteeGoalService;

	@Override
	public ResponseEntity getMenteeGoals(Integer page, Integer size) {
		final Long menteeId = SecurityUtil.getCurrentUserId();
		final MenteeGoalSummaryPagingResponse response = menteeGoalService.getMenteeGoalSummaries(menteeId, page, size);
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<GetMenteeGoalWeeklyProgress200Response> getMenteeGoalWeeklyProgress(Integer menteeGoalId,
		LocalDate date) throws Exception {
		// 보류
		return MenteeGoalApi.super.getMenteeGoalWeeklyProgress(menteeGoalId, date);
	}

	@Override
	public ResponseEntity hasRemainingTodosToday() {
		final Long menteeId = SecurityUtil.getCurrentUserId();
		final RemainingTodosResponse response = new RemainingTodosResponse();
		response.setHasRemainingTodosToday(menteeGoalService.hasRemainingTodosToday(menteeId));
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity getMenteeGoalDailyDetails(Long menteeGoalId, LocalDate date) {
		return ResponseEntity.ok(menteeGoalService.getMenteeGoalDailyDetails(menteeGoalId, date));
	}

	@Override
	public ResponseEntity updateTodoStatus(Long menteeGoalId, Long todoId) {
		return ResponseEntity.ok(menteeGoalService.updateTodoStatus(menteeGoalId, todoId));
	}

}
