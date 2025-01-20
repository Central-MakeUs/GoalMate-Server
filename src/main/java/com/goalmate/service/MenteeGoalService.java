package com.goalmate.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.api.model.MenteeGoalProgressResponse;
import com.goalmate.api.model.MenteeGoalSummaryPagingResponse;
import com.goalmate.api.model.MenteeGoalSummaryResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalDailyTodoEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.mapper.MenteeGoalResponseMapper;
import com.goalmate.mapper.PageResponseMapper;
import com.goalmate.repository.MenteeGoalDailyTodoRepository;
import com.goalmate.repository.MenteeGoalRepository;
import com.goalmate.util.PageRequestUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MenteeGoalService {
	// private final GoalService goalService;
	private final MenteeGoalRepository menteeGoalRepository;
	private final MenteeGoalDailyTodoRepository dailyTodoRepository;

	public MenteeGoalSummaryPagingResponse getMenteeGoals(Long menteeId, Integer page, Integer size) {
		// 멘티의 ID 로 멘티의 목표를 조회
		Pageable pageable = PageRequestUtil.createPageRequest(page, size);
		Page<MenteeGoalEntity> menteeGoals = menteeGoalRepository.findByMenteeId(menteeId, pageable);
		// 해당 목표 id 로 달성율 조회
		List<MenteeGoalSummaryResponse> summaries = menteeGoals.stream().map(menteeGoal -> {
			// 1개의 목표에 대한 목표 정보, 달성율을 담은 객체 생성
			GoalEntity goal = menteeGoal.getGoalEntity();
			MenteeGoalProgressResponse progressResponse = getProgressResponse(menteeGoal);
			return MenteeGoalResponseMapper.mapToSummaryResponse(goal, menteeGoal, progressResponse);
		}).toList();

		// 페이징 정보를 담은 객체 생성
		PageResponse pageResponse = PageResponseMapper.mapToPageResponse(menteeGoals);
		// 목표 정보, 달성율, 페이징 정보를 담은 객체 반환
		return MenteeGoalResponseMapper.mapToSummaryPagingResponse(summaries, pageResponse);
	}

	public MenteeGoalProgressResponse getProgressResponse(MenteeGoalEntity menteeGoal) {
		// 전체 투두 조회 후, 완료된 투두의 수를 구하여 달성률을 계산
		int completedCount = 0;
		List<MenteeGoalDailyTodoEntity> todos = dailyTodoRepository.findByMenteeGoalId(menteeGoal.getId());
		for (MenteeGoalDailyTodoEntity todo : todos) {
			if (todo.isCompleted()) {
				completedCount++;
			}
		}
		int totalCount = todos.size();

		return MenteeGoalResponseMapper.mapToProgressResponse(
			completedCount,
			totalCount,
			menteeGoal.getStatus().getValue());

	}
}
