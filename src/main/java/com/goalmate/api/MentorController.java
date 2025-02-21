package com.goalmate.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.model.GoalCreateRequest;
import com.goalmate.api.model.MentorLetterRequest;
import com.goalmate.api.model.UpdateMentorLetter200Response;
import com.goalmate.service.GoalService;
import com.goalmate.support.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MentorController implements MentorApi {
	private final GoalService goalService;

	@Override
	public ResponseEntity createGoal(GoalCreateRequest goalCreateRequest) {
		Long goalId = goalService.createGoal(goalCreateRequest);
		return ResponseEntity.ok(ApiResponse.success(goalId));
	}

	@Override
	public ResponseEntity<UpdateMentorLetter200Response> updateMentorLetter(Long menteeGoalId,
		MentorLetterRequest mentorLetterRequest) throws Exception {
		return MentorApi.super.updateMentorLetter(menteeGoalId, mentorLetterRequest);
	}
}
