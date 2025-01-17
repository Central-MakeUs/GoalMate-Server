package com.goalmate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.api.model.GoalDetailResponse;
import com.goalmate.api.model.GoalSummaryPagingResponse;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.mapper.GoalResponseMapper;
import com.goalmate.repository.GoalRepository;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class GoalService {
	private final GoalRepository goalRepository;

	public GoalSummaryPagingResponse getGoals(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<GoalEntity> goals = goalRepository.findAll(pageable);

		log.info("getGoals goals: {}", goals);
		return GoalResponseMapper.mapToSummaryPagingResponse(goals);
	}

	public GoalDetailResponse getGoalDetails(Long goalId) {
		log.info("getGoalDetails goalId: {}", goalId);
		GoalEntity goalEntity = goalRepository.findByIdWithDetails(goalId).orElseThrow(() -> new CoreApiException(
			ErrorType.NOT_FOUND));
		log.info(GoalResponseMapper.mapToDetailResponse(goalEntity).toString());
		return GoalResponseMapper.mapToDetailResponse(goalEntity);
	}
}
