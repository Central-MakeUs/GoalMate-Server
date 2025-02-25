package com.goalmate.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.MentorApi;
import com.goalmate.api.model.GoalCreateRequest;
import com.goalmate.api.model.MenteeGoalSummaryResponse;
import com.goalmate.api.model.MentorLetterRequest;
import com.goalmate.security.user.SecurityUtil;
import com.goalmate.service.GoalService;
import com.goalmate.service.MenteeGoalService;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MentorController implements MentorApi {
	private final GoalService goalService;
	private final MenteeGoalService menteeGoalService;

	@Override
	public ResponseEntity createGoal(GoalCreateRequest goalCreateRequest) {
		if (goalCreateRequest.getMentorId() == null) {
			goalCreateRequest.setMentorId(SecurityUtil.getCurrentUserId());
		}
		Long goalId = goalService.createGoal(goalCreateRequest);
		return ResponseEntity.ok(ApiResponse.success(goalId));
	}

	@Override
	public ResponseEntity updateMentorLetter(Long menteeGoalId,
		MentorLetterRequest mentorLetterRequest) {
		MenteeGoalSummaryResponse response = menteeGoalService.updateMentorLetter(
			menteeGoalId,
			mentorLetterRequest.getMentorLetter()
		);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
