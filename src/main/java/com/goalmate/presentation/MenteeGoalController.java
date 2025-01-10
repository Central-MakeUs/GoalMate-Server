package com.goalmate.presentation;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.goalmate.api.MenteeGoalApi;

@RestController
public class MenteeGoalController implements MenteeGoalApi {
	@Override
	public ResponseEntity<Void> getMenteeGoalComments(Integer goalId) throws Exception {
		return MenteeGoalApi.super.getMenteeGoalComments(goalId);
	}

	@Override
	public ResponseEntity<Void> getMenteeGoalDetails(Integer goalId, LocalDate date) throws Exception {
		return MenteeGoalApi.super.getMenteeGoalDetails(goalId, date);
	}

	@Override
	public ResponseEntity<Void> getMenteeGoals() throws Exception {
		return MenteeGoalApi.super.getMenteeGoals();
	}

	@Override
	public ResponseEntity<Void> updateMenteeGoalTodoStatus(Integer goalId, Integer todoId) throws Exception {
		return MenteeGoalApi.super.updateMenteeGoalTodoStatus(goalId, todoId);
	}
}
