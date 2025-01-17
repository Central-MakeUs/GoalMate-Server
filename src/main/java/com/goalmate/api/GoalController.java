package com.goalmate.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.model.GoalDetailResponse;
import com.goalmate.api.model.GoalSummaryPagingResponse;
import com.goalmate.security.SecurityUtil;
import com.goalmate.service.GoalService;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class GoalController implements GoalApi {
	private final GoalService goalService;

	@Override
	public ResponseEntity getGoals(Integer page, Integer size) throws Exception {
		GoalSummaryPagingResponse response = goalService.getGoals(page, size);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity getGoalDetails(Integer goalId) throws Exception {
		GoalDetailResponse response = goalService.getGoalDetails(goalId.longValue());
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity participateInGoal(Integer goalId) {
		Long currentUserId = SecurityUtil.getCurrentUserId();
		goalService.participateInGoal(currentUserId, goalId.longValue());
		return ResponseEntity.ok(ApiResponse.success());
	}
}
