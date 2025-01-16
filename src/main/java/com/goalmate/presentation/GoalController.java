package com.goalmate.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.GoalApi;
import com.goalmate.api.model.GoalDetailResponse;
import com.goalmate.api.model.ModelApiResponse;
import com.goalmate.service.GoalService;
import com.goalmate.service.dto.GoalSummary;
import com.goalmate.service.dto.PageResponse;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class GoalController implements GoalApi {
	private final GoalService goalService;

	@Override
	public ResponseEntity getGoals(Integer page, Integer size) throws Exception {
		PageResponse<GoalSummary> goals = goalService.getGoals(page, size);
		return ResponseEntity.ok(ApiResponse.success(goals));
	}

	@Override
	public ResponseEntity getGoalDetails(Integer goalId) throws Exception {
		GoalDetailResponse response = goalService.getGoalDetails(goalId.longValue());
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity<ModelApiResponse> registerMentee(Integer goalId) throws Exception {
		return GoalApi.super.registerMentee(goalId);
	}
}
