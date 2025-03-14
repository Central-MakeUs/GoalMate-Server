package com.goalmate.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.GoalApi;
import com.goalmate.api.model.GoalCreateRequest;
import com.goalmate.api.model.GoalDetailResponse;
import com.goalmate.api.model.GoalSummaryPagingResponse;
import com.goalmate.api.model.ParticipateInGoalResponse;
import com.goalmate.security.user.SecurityUtil;
import com.goalmate.service.GoalService;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class GoalController implements GoalApi {
	private final GoalService goalService;

	@Override
	public ResponseEntity createGoal(GoalCreateRequest goalCreateRequest) {
		if (goalCreateRequest.getMentorId() == null) {
			goalCreateRequest.setMentorId(SecurityUtil.getCurrentUserId());
		}
		Long goalId = goalService.createGoal(goalCreateRequest);
		return ResponseEntity.ok(ApiResponse.success(goalId));
	}

	@Override
	public ResponseEntity getGoals(Integer page, Integer size) {
		GoalSummaryPagingResponse response = goalService.getGoals(page, size);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity getGoalDetails(Integer goalId) {
		GoalDetailResponse response = goalService.getGoalDetails(goalId.longValue());
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@Override
	public ResponseEntity participateInGoal(Integer goalId) {
		Long currentUserId = SecurityUtil.getCurrentUserId();
		ParticipateInGoalResponse response = goalService.participateInGoal(currentUserId, goalId.longValue());
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
