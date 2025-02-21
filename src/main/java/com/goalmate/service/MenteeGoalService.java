package com.goalmate.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.api.model.MenteeGoalDailyDetailResponse;
import com.goalmate.api.model.MenteeGoalDailyProgressResponse;
import com.goalmate.api.model.MenteeGoalSummaryPagingResponse;
import com.goalmate.api.model.MenteeGoalSummaryResponse;
import com.goalmate.api.model.MenteeGoalTodoResponse;
import com.goalmate.api.model.MenteeGoalWeeklyProgressResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.domain.menteeGoal.MenteeGoalDailyTodoEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.domain.menteeGoal.TodoProgress;
import com.goalmate.domain.menteeGoal.TodoStatus;
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
	private final MenteeGoalRepository menteeGoalRepository;
	private final MenteeGoalDailyTodoRepository dailyTodoRepository;

	public MenteeGoalSummaryPagingResponse getMenteeGoalSummaries(Long menteeId, Integer page, Integer size) {
		Pageable pageable = PageRequestUtil.createPageRequest(page, size);
		Page<MenteeGoalEntity> menteeGoals = menteeGoalRepository.findByMenteeId(menteeId, pageable);
		List<MenteeGoalSummaryResponse> summaries = menteeGoals.stream()
			.map(menteeGoal -> MenteeGoalResponseMapper
				.mapToSummaryResponse(
					menteeGoal,
					getTodoProgress(menteeGoal)))
			.toList();

		// 페이징 정보를 담은 객체 생성
		PageResponse pageResponse = PageResponseMapper.mapToPageResponse(menteeGoals);
		// 목표 정보, 달성율, 페이징 정보를 담은 객체 반환
		return MenteeGoalResponseMapper.mapToSummaryPagingResponse(summaries, pageResponse);
	}

	public boolean hasRemainingTodosToday(Long menteeId) {
		return dailyTodoRepository.existsByMenteeIdAndDateAndIsNotCompleted(menteeId, LocalDate.now());
	}

	public MenteeGoalDailyDetailResponse getMenteeGoalDailyDetails(Long menteeGoalId, LocalDate date) {
		MenteeGoalEntity menteeGoal = getMenteeGoal(menteeGoalId);
		MenteeGoalSummaryResponse summary = MenteeGoalResponseMapper
			.mapToSummaryResponse(
				menteeGoal,
				getTodoProgress(menteeGoal));
		List<MenteeGoalTodoResponse> todos = dailyTodoRepository.findByMenteeGoalIdAndDate(menteeGoalId, date)
			.stream()
			.map(MenteeGoalResponseMapper::mapToTodoResponse)
			.toList();
		return MenteeGoalResponseMapper.mapToDailyDetailResponse(summary, date, todos);
	}

	public MenteeGoalTodoResponse updateTodoStatus(Long menteeGoalId, Long todoId, String status) {
		MenteeGoalDailyTodoEntity todo = dailyTodoRepository.findById(todoId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "Todo not found"));
		if (!todo.getTodoDate().isEqual(LocalDate.now())) {
			throw new CoreApiException(ErrorType.BAD_REQUEST, "Cannot update past todo");
		}
		todo.updateStatus(TodoStatus.valueOf(status));
		return MenteeGoalResponseMapper.mapToTodoResponse(todo);
	}

	public MenteeGoalEntity getMenteeGoal(Long menteeGoalId) {
		return menteeGoalRepository.findById(menteeGoalId)
			.orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND, "MenteeGoal not found"));
	}

	public MenteeGoalWeeklyProgressResponse getWeeklyProgress(Long menteeGoalId, LocalDate date) {
		// 해당 주의 일요일 구하기 (해당 날짜가 일요일이면 그대로, 아니면 이전 일요일)
		LocalDate sunday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
		// 해당 주의 토요일 구하기 (해당 날짜가 토요일이면 그대로, 아니면 다음 토요일)
		LocalDate saturday = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
		boolean hasLastWeek = dailyTodoRepository.existsByMenteeGoalIdAndDate(menteeGoalId, sunday.minusDays(1));
		boolean hasNextWeek = dailyTodoRepository.existsByMenteeGoalIdAndDate(menteeGoalId, saturday.plusDays(1));
		List<MenteeGoalDailyTodoEntity> todos = dailyTodoRepository.findByMenteeGoalIdAndDateBetween(menteeGoalId,
			sunday, saturday);

		// todos를 날짜별로 그룹화합니다.
		Map<LocalDate, List<MenteeGoalDailyTodoEntity>> todosByDate = todos.stream()
			.collect(Collectors.groupingBy(MenteeGoalDailyTodoEntity::getTodoDate));

		List<MenteeGoalDailyProgressResponse> dailyProgressList = new ArrayList<>();

		// 일요일부터 토요일까지 순회하며 각 날짜의 progress를 계산합니다.
		for (LocalDate currentDate = sunday; !currentDate.isAfter(saturday); currentDate = currentDate.plusDays(1)) {
			List<MenteeGoalDailyTodoEntity> dailyTodos = todosByDate.getOrDefault(currentDate, Collections.emptyList());

			int dailyTodoCount = dailyTodos.size();
			int completedDailyTodoCount = (int)dailyTodos.stream()
				.filter(MenteeGoalDailyTodoEntity::isCompleted)
				.count();

			// isValid의 기준은 비즈니스 로직에 따라 설정합니다. 여기서는 todo가 하나라도 있으면 유효하다고 가정.
			boolean isValid = dailyTodoCount > 0;

			// 주어진 날짜의 progress 객체를 생성
			MenteeGoalDailyProgressResponse dailyProgress =
				MenteeGoalResponseMapper.mapToDailyProgressResponse(
					currentDate,
					dailyTodoCount,
					completedDailyTodoCount,
					isValid
				);

			dailyProgressList.add(dailyProgress);
		}
		return MenteeGoalResponseMapper.mapToWeeklyProgressResponse(hasLastWeek, hasNextWeek, dailyProgressList);
	}

	public void updateMentorLetter(Long menteeGoalId, String mentorLetter) {
		MenteeGoalEntity menteeGoal = getMenteeGoal(menteeGoalId);
		menteeGoal.updateMentorLetter(mentorLetter);
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
}
