package com.goalmate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.api.model.GoalDetailResponse;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.mapper.GoalMapper;
import com.goalmate.repository.GoalRepository;
import com.goalmate.service.dto.GoalSummary;
import com.goalmate.service.dto.PageResponse;
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

	public PageResponse<GoalSummary> getGoals(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		log.info("getGoals pageable: {}", pageable);

		Page<GoalSummary> goals = goalRepository.findAll(pageable).map(GoalSummary::of);
		return PageResponse.of(goals);
	}

	public GoalDetailResponse getGoalDetails(Long goalId) {
		log.info("getGoalDetails goalId: {}", goalId);
		GoalEntity goalEntity = goalRepository.findByIdWithDetails(goalId).orElseThrow(() -> new CoreApiException(
			ErrorType.NOT_FOUND));
		log.info(GoalMapper.toDto(goalEntity).toString());
		return GoalMapper.toDto(goalEntity);
	}
}
