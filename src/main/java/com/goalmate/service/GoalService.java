package com.goalmate.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.api.model.GoalDetailResponse;
import com.goalmate.api.model.GoalSummaryPagingResponse;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.mentee.MenteeEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalDailyTodoEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.mapper.GoalResponseMapper;
import com.goalmate.repository.GoalRepository;
import com.goalmate.repository.MenteeGoalDailyTodoRepository;
import com.goalmate.repository.MenteeGoalRepository;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class GoalService {
	private final MenteeService menteeService;
	private final GoalRepository goalRepository;
	private final MenteeGoalRepository menteeGoalRepository;
	private final MenteeGoalDailyTodoRepository dailyTodoRepository;

	@Transactional(readOnly = true)
	public GoalSummaryPagingResponse getGoals(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page - 1, size); // Input 기본값은 1부터 시작이지만, PageRequest.of()는 0부터 시작
		Page<GoalEntity> goals = goalRepository.findAll(pageable);
		return GoalResponseMapper.mapToSummaryPagingResponse(goals);
	}

	@Transactional(readOnly = true)
	public GoalDetailResponse getGoalDetails(Long goalId) {
		log.info("getGoalDetails goalId: {}", goalId);
		GoalEntity goalEntity = goalRepository.findByIdWithDetails(goalId).orElseThrow(() -> new CoreApiException(
			ErrorType.NOT_FOUND));
		log.info(GoalResponseMapper.mapToDetailResponse(goalEntity).toString());
		return GoalResponseMapper.mapToDetailResponse(goalEntity);
	}

	public void participateInGoal(Long currentUserId, Long goalId) {
		MenteeEntity menteeEntity = menteeService.getMenteeById(currentUserId);
		GoalEntity goalEntity = getGoalById(goalId);

		// MenteeGoalEntity 생성, 저장
		MenteeGoalEntity menteeGoalEntity = MenteeGoalEntity.builder()
			.joinedAt(LocalDateTime.now())
			.menteeEntity(menteeEntity)
			.goalEntity(goalEntity)
			.build();
		menteeGoalRepository.save(menteeGoalEntity);

		// GoalEntity로부터 DailyTodoEntity를 모두 가져와서 MenteeDailyTodoEntity로 변환하여 저장
		List<MenteeGoalDailyTodoEntity> menteeDailyTodoEntities = goalEntity.getDailyTodos().stream()
			.map(dailyTodoEntity -> MenteeGoalDailyTodoEntity.builder()
				.todoDate(dailyTodoEntity.getTodoDate())
				.description(dailyTodoEntity.getDescription())
				.menteeGoalEntity(menteeGoalEntity)
				.build())
			.toList();

		// Bulk insert로 개선 필요
		dailyTodoRepository.saveAll(menteeDailyTodoEntities);

		// GoalEntity의 참여자 수 증가
		// 다시 참여하면 예외처리
		// 마감되었어도 예외처리
	}

	private GoalEntity getGoalById(Long goalId) {
		return goalRepository.findById(goalId).orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND));
	}
}
