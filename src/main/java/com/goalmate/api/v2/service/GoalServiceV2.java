package com.goalmate.api.v2.service;

import org.springframework.stereotype.Service;

import com.goalmate.api.v2.dto.request.GoalStatusUpdateRequest;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.repository.GoalRepository;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class GoalServiceV2 {
	private final GoalRepository goalRepository;

	public void updateGoalStatus(Long goalId, GoalStatusUpdateRequest request) {
		GoalEntity goal = getGoalById(goalId);
		goal.updateStatus(request.status());
	}

	private GoalEntity getGoalById(Long goalId) {
		return goalRepository.findById(goalId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "Goal not found"));
	}
}
