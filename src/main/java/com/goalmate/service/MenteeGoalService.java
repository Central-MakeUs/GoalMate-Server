package com.goalmate.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.api.model.MenteeGoalSummaryPagingResponse;
import com.goalmate.api.model.MenteeGoalSummaryResponse;
import com.goalmate.api.model.MenteeGoalTodoResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.domain.menteeGoal.MenteeGoalDailyTodoEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.domain.menteeGoal.TodoProgress;
import com.goalmate.mapper.MenteeGoalResponseMapper;
import com.goalmate.mapper.PageResponseMapper;
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
public class MenteeGoalService {
	private final GoalService goalService;
	private final CommentService commentService;
	private final MenteeGoalRepository menteeGoalRepository;
	private final MenteeGoalDailyTodoRepository dailyTodoRepository;

	public MenteeGoalSummaryPagingResponse getMenteeGoals(Long menteeId, Integer page, Integer size) {
		Pageable pageable = PageRequestUtil.createPageRequest(page, size);
		Page<MenteeGoalEntity> menteeGoals = menteeGoalRepository.findByMenteeId(menteeId, pageable);
		List<MenteeGoalSummaryResponse> summaries = menteeGoals.stream()
			.map(menteeGoal -> MenteeGoalResponseMapper
				.mapToSummaryResponse(
					menteeGoal,
					getTodoProgress(menteeGoal),
					commentService.hasNewComment(menteeGoal.getId())))
			.toList();

		// 페이징 정보를 담은 객체 생성
		PageResponse pageResponse = PageResponseMapper.mapToPageResponse(menteeGoals);
		// 목표 정보, 달성율, 페이징 정보를 담은 객체 반환
		return MenteeGoalResponseMapper.mapToSummaryPagingResponse(summaries, pageResponse);
	}

	public boolean hasRemainingTodosToday(Long menteeId) {
		return dailyTodoRepository.existsByMenteeIdAndDateAndIsNotCompleted(menteeId, LocalDate.now());
	}

	private TodoProgress getTodoProgress(MenteeGoalEntity menteeGoal) {
		// 전체 투두를 조회하며, 완료된 투두개수, 전체 투두개수, 오늘 할 투두 개수, 오늘 완료한 투두 개수를 구한다.
		List<MenteeGoalDailyTodoEntity> dailyTodos = dailyTodoRepository.findByMenteeGoalId(menteeGoal.getId());
		final LocalDate today = LocalDate.now();
		int totalCount = dailyTodos.size();
		int totalCompletedCount = 0;
		int todayCount = 0;
		int todayCompletedCount = 0;
		for (MenteeGoalDailyTodoEntity dailyTodo : dailyTodos) {
			if (dailyTodo.getTodoDate().isEqual(today)) {
				todayCount++;
				if (dailyTodo.isCompleted()) {
					todayCompletedCount++;
				}
			}
			if (dailyTodo.isCompleted()) {
				totalCompletedCount++;
			}
		}

		return TodoProgress.builder()
			.totalCount(totalCount)
			.totalCompletedCount(totalCompletedCount)
			.todayCount(todayCount)
			.todayCompletedCount(todayCompletedCount)
			.build();
	}

	public Object getMenteeGoalDailyDetails(Long menteeGoalId, LocalDate date) {
		MenteeGoalEntity menteeGoal = getMenteeGoal(menteeGoalId);
		MenteeGoalSummaryResponse summary = MenteeGoalResponseMapper
			.mapToSummaryResponse(
				menteeGoal,
				getTodoProgress(menteeGoal),
				commentService.hasNewComment(menteeGoal.getId()));
		List<MenteeGoalTodoResponse> todos = dailyTodoRepository.findByMenteeGoalIdAndDate(menteeGoalId, date)
			.stream()
			.map(MenteeGoalResponseMapper::mapToTodoResponse)
			.toList();
		return MenteeGoalResponseMapper.mapToDailyDetailResponse(summary, date, todos);
	}

	public MenteeGoalTodoResponse updateTodoStatus(Long menteeGoalId, Long todoId) {
		MenteeGoalDailyTodoEntity todo = dailyTodoRepository.findById(todoId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "Todo not found"));
		if (!todo.getTodoDate().isEqual(LocalDate.now())) {
			throw new CoreApiException(ErrorType.BAD_REQUEST, "Cannot update past todo");
		}
		todo.toggleStatus();
		return MenteeGoalResponseMapper.mapToTodoResponse(todo);
	}

	private MenteeGoalEntity getMenteeGoal(Long menteeGoalId) {
		return menteeGoalRepository.findById(menteeGoalId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "MenteeGoal not found"));
	}
}
