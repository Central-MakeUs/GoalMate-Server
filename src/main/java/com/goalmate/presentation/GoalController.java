package com.goalmate.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.GoalApi;
import com.goalmate.api.model.ModelApiResponse;

@RestController
public class GoalController implements GoalApi {
	@Override
	public ResponseEntity getGoals(Integer page, Integer size) throws Exception {
		// TODO 구현
		return GoalApi.super.getGoals(page, size);
	}

	@Override
	public ResponseEntity getGoalDetails(Integer goalId) throws Exception {
		// TODO 구현
		return GoalApi.super.getGoalDetails(goalId);
	}

	@Override
	public ResponseEntity<ModelApiResponse> registerMentee(Integer goalId) throws Exception {
		return GoalApi.super.registerMentee(goalId);
	}
}
