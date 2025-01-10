package com.goalmate.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.GoalApi;
import com.goalmate.api.model.GetGoalDetails200Response;
import com.goalmate.api.model.GetGoals200Response;
import com.goalmate.api.model.ModelApiResponse;

@RestController
public class GoalController implements GoalApi {
	@Override
	public ResponseEntity<GetGoalDetails200Response> getGoalDetails(Integer goalId) throws Exception {
		return GoalApi.super.getGoalDetails(goalId);
	}

	@Override
	public ResponseEntity<GetGoals200Response> getGoals(Integer page, Integer size) throws Exception {
		return GoalApi.super.getGoals(page, size);
	}

	@Override
	public ResponseEntity<ModelApiResponse> joinGoal(Integer goalId) throws Exception {
		return GoalApi.super.joinGoal(goalId);
	}
}
