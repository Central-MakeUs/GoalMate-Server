package com.goalmate.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.api.model.GoalDetailResponse;
import com.goalmate.api.model.GoalSummaryPagingResponse;
import com.goalmate.api.model.GoalSummaryResponse;
import com.goalmate.api.model.PageResponse;
import com.goalmate.domain.comment.CommentRoomEntity;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.mentee.MenteeEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalDailyTodoEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.mapper.GoalResponseMapper;
import com.goalmate.mapper.PageResponseMapper;
import com.goalmate.repository.CommentRoomRepository;
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
	private final CommentRoomRepository commentRoomRepository;
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

		validateGoal(goal);
		validateParticipator(goal, mentee);

		MenteeGoalEntity menteeGoal = MenteeGoalEntity.builder()
			.startDate(LocalDate.now())
			.endDate(LocalDate.now().plusDays(goal.getPeriod()))
			.menteeEntity(mentee)
			.goalEntity(goal)
			.build();
		menteeGoalRepository.save(menteeGoal);

		CommentRoomEntity commentRoom = new CommentRoomEntity(goal.getMentorEntity(), mentee, menteeGoal);
		commentRoomRepository.save(commentRoom);

		// 할일 복사
		List<MenteeGoalDailyTodoEntity> menteeDailyTodos = goal.getDailyTodos().stream()
			.map(dailyTodo -> MenteeGoalDailyTodoEntity.builder()
				.todoDate(dailyTodo.getTodoDate())
				.estimatedMinutes(dailyTodo.getEstimatedMinutes())
				.description(dailyTodo.getDescription())
				.mentorTip(dailyTodo.getMentorTip())
				.menteeGoalEntity(menteeGoal)
				.build())
			.toList();

		// TODO: Bulk insert로 개선
		dailyTodoRepository.saveAll(menteeDailyTodos);
		mentee.decreaseFreeParticipationCount(); // 무료 참여 횟수 차감
		goal.increaseCurrentParticipants(); // 현재 참여자 수 증가
	}

	public GoalEntity getGoalById(Long goalId) {
		return goalRepository.findById(goalId).orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND));
	}

	private void validateGoal(GoalEntity goal) {
		if (goal.isFull()) {
			throw new CoreApiException(ErrorType.FORBIDDEN, "There is no free participation count");
		}
	}

	private void validateParticipator(GoalEntity goalEntity, MenteeEntity menteeEntity) {
		if (!menteeEntity.hasFreeCount()) {
			throw new CoreApiException(ErrorType.FORBIDDEN, "No free participation available.");
		}
		if (!goalEntity.isOpen()) {
			throw new CoreApiException(ErrorType.FORBIDDEN, "Goal is not started or closed");
		}
		menteeGoalRepository.findByMenteeIdAndGoalId(menteeEntity.getId(), goalEntity.getId())
			.ifPresent(menteeGoalEntity -> {
				throw new CoreApiException(ErrorType.FORBIDDEN, "Already participated.");
			});
	}
}
