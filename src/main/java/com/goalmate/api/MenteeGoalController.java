package com.goalmate.api;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.model.GetMenteeGoalDetails200Response;
import com.goalmate.api.model.GetMenteeGoalWeeklyProgress200Response;
import com.goalmate.api.model.HasTodayTodos200Response;
import com.goalmate.api.model.MenteeGoalSummaryPagingResponse;
import com.goalmate.api.model.UpdateMenteeGoalTodoStatus200Response;
import com.goalmate.security.SecurityUtil;
import com.goalmate.service.MenteeGoalService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MenteeGoalController implements MenteeGoalApi {
	private final MenteeGoalService menteeGoalService;

	@Override
	public ResponseEntity getMenteeGoals(Integer page, Integer size) {
		Long menteeId = SecurityUtil.getCurrentUserId();
		MenteeGoalSummaryPagingResponse response = menteeGoalService.getMenteeGoals(menteeId, page, size);
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<GetMenteeGoalWeeklyProgress200Response> getMenteeGoalWeeklyProgress(Integer menteeGoalId,
		LocalDate date) throws Exception {
		return MenteeGoalApi.super.getMenteeGoalWeeklyProgress(menteeGoalId, date);
	}

	@Override
	public ResponseEntity<HasTodayTodos200Response> hasTodayTodos() throws Exception {
		return MenteeGoalApi.super.hasTodayTodos();
	}

	@Override
	public ResponseEntity<GetMenteeGoalDetails200Response> getMenteeGoalDetails(Integer menteeGoalId,
		LocalDate date) throws Exception {
		return MenteeGoalApi.super.getMenteeGoalDetails(menteeGoalId, date);
	}

	@Override
	public ResponseEntity<UpdateMenteeGoalTodoStatus200Response> updateMenteeGoalTodoStatus(Integer goalId,
		Integer todoId) throws Exception {
		return MenteeGoalApi.super.updateMenteeGoalTodoStatus(goalId, todoId);
	}
}
