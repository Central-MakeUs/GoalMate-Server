package com.goalmate.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.api.model.GoalDetailResponse;
import com.goalmate.api.model.GoalSummaryPagingResponse;
import com.goalmate.api.model.GoalSummaryResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.goal.GoalStatus;
import com.goalmate.domain.mentee.MenteeEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalDailyTodoEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.mapper.GoalResponseMapper;
import com.goalmate.mapper.PageResponseMapper;
import com.goalmate.repository.GoalRepository;
import com.goalmate.repository.MenteeGoalDailyTodoRepository;
import com.goalmate.repository.MenteeGoalRepository;
import com.goalmate.support.error.CoreApiException;
import com.goalmate.support.error.ErrorType;
import com.goalmate.util.PageRequestUtil;

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
		Pageable pageable = PageRequestUtil.createPageRequest(page, size);
		Page<GoalEntity> goals = goalRepository.findAll(pageable);

		List<GoalSummaryResponse> summaries = goals.getContent().stream()
			.map(GoalResponseMapper::mapToSummaryResponse).toList();
		PageResponse pageResponse = PageResponseMapper.mapToPageResponse(goals);

		return GoalResponseMapper.mapToSummaryPagingResponse(summaries, pageResponse);
	}

	@Transactional(readOnly = true)
	public GoalDetailResponse getGoalDetails(Long goalId) {
		GoalEntity goal = goalRepository.findByIdWithDetails(goalId).orElseThrow(()
			-> new CoreApiException(ErrorType.NOT_FOUND));
		log.info(GoalResponseMapper.mapToDetailResponse(goal).toString());
		return GoalResponseMapper.mapToDetailResponse(goal);
	}

	public void participateInGoal(Long currentUserId, Long goalId) {
		MenteeEntity mentee = menteeService.getMenteeById(currentUserId);
		GoalEntity goal = getGoalById(goalId);

		validateGoalParticipation(goal, mentee);

		// MenteeGoalEntity 생성, 저장
		MenteeGoalEntity menteeGoal = MenteeGoalEntity.builder()
			.joinedAt(LocalDateTime.now())
			.menteeEntity(mentee)
			.goalEntity(goal)
			.build();
		menteeGoalRepository.save(menteeGoal);

		// GoalEntity로부터 DailyTodoEntity를 모두 가져와서 MenteeDailyTodoEntity로 변환하여 저장
		List<MenteeGoalDailyTodoEntity> menteeDailyTodos = goal.getDailyTodos().stream()
			.map(dailyTodo -> MenteeGoalDailyTodoEntity.builder()
				.todoDate(dailyTodo.getTodoDate())
				.description(dailyTodo.getDescription())
				.menteeGoalEntity(menteeGoal)
				.build())
			.toList();

		// TODO: Bulk insert로 개선 필요
		dailyTodoRepository.saveAll(menteeDailyTodos);
		mentee.decreaseFreeParticipationCount(); // 무료 참여 횟수 차감
		goal.increaseCurrentParticipants(); // 현재 참여자 수 증가
	}

	public GoalEntity getGoalById(Long goalId) {
		return goalRepository.findById(goalId).orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND));
	}

	private void validateGoalParticipation(GoalEntity goalEntity, MenteeEntity menteeEntity) {
		if (!menteeEntity.isFreeParticipationAvailable()) {
			throw new CoreApiException(ErrorType.FORBIDDEN, "No free participation available.");
		}
		if (goalEntity.getGoalStatus().equals(GoalStatus.CLOSED)) {
			throw new CoreApiException(ErrorType.FORBIDDEN, "Goal is closed.");
		}
		menteeGoalRepository.findByMenteeIdAndGoalId(menteeEntity.getId(), goalEntity.getId())
			.ifPresent(menteeGoalEntity -> {
				throw new CoreApiException(ErrorType.FORBIDDEN, "Already participated.");
			});
	}
}
