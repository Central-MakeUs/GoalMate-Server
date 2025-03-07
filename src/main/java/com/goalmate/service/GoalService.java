package com.goalmate.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goalmate.api.model.GoalCreateRequest;
import com.goalmate.api.model.GoalDetailResponse;
import com.goalmate.api.model.GoalSummaryPagingResponse;
import com.goalmate.api.model.GoalSummaryResponse;
import com.goalmate.api.model.ImageRequest;
import com.goalmate.api.model.MidObjectiveRequest;
import com.goalmate.api.model.PageResponse;
import com.goalmate.api.model.ParticipateInGoalResponse;
import com.goalmate.api.model.TodoRequest;
import com.goalmate.api.model.WeeklyObjectiveRequest;
import com.goalmate.domain.comment.CommentRoomEntity;
import com.goalmate.domain.goal.ContentImageEntity;
import com.goalmate.domain.goal.DailyTodoEntity;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.goal.GoalStatus;
import com.goalmate.domain.goal.MidObjectiveEntity;
import com.goalmate.domain.goal.ThumbnailImageEntity;
import com.goalmate.domain.goal.WeeklyObjectiveEntity;
import com.goalmate.domain.mentee.MenteeEntity;
import com.goalmate.domain.mentee.Role;
import com.goalmate.domain.menteeGoal.MenteeGoalDailyTodoEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.domain.mentor.MentorEntity;
import com.goalmate.mapper.GoalResponseMapper;
import com.goalmate.mapper.PageResponseMapper;
import com.goalmate.repository.CommentRoomRepository;
import com.goalmate.repository.ContentImageRepository;
import com.goalmate.repository.DailyTodoRepository;
import com.goalmate.repository.GoalRepository;
import com.goalmate.repository.MenteeGoalDailyTodoRepository;
import com.goalmate.repository.MenteeGoalRepository;
import com.goalmate.repository.MidObjectiveRepository;
import com.goalmate.repository.ThumbnailImageRepository;
import com.goalmate.repository.WeeklyObjectiveRepository;
import com.goalmate.security.user.SecurityUtil;
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
	private final MentorService mentorService;

	private final GoalRepository goalRepository;
	private final MenteeGoalRepository menteeGoalRepository;
	private final CommentRoomRepository commentRoomRepository;
	private final DailyTodoRepository dailyTodoRepository;
	private final MenteeGoalDailyTodoRepository menteeGoalDailyTodoRepository;
	private final MidObjectiveRepository midObjectiveRepository;
	private final WeeklyObjectiveRepository weeklyObjectiveRepository;
	private final ThumbnailImageRepository thumbnailImageRepository;
	private final ContentImageRepository contentImageRepository;

	public Long createGoal(GoalCreateRequest request) {
		MentorEntity mentor = mentorService.getMentorById(request.getMentorId());
		GoalEntity goal = GoalEntity.builder()
			.title(request.getTitle())
			.topic(request.getTopic())
			.description(request.getDescription())
			.period(request.getPeriod())
			.dailyDuration(request.getDailyDuration())
			.participantsLimit(request.getParticipantsLimit())
			.goalStatus(GoalStatus.OPEN)
			.mentor(mentor)
			.build();
		goalRepository.save(goal);

		saveAllMidObjectives(request.getMidObjectives(), goal);
		saveAllWeeklyObjectives(request.getWeeklyObjectives(), goal);
		saveAllThumbnailImages(request.getThumbnailImages(), goal);
		saveAllContentImages(request.getContentImages(), goal);
		saveAllTodos(request.getTodoList(), goal);
		return goal.getId();
	}

	@Transactional(readOnly = true)
	public GoalSummaryPagingResponse getGoals(Integer page, Integer size) {
		Pageable pageable = PageRequestUtil.createPageRequest(page, size);

		Page<GoalEntity> goals;
		if (SecurityUtil.isUserLoggedIn() && SecurityUtil.getCurrenUserRole().equals(Role.ROLE_ADMIN)) {
			goals = goalRepository.findAll(pageable);
		} else {
			goals = goalRepository.findAllExcludingStatus(GoalStatus.DISABLED, pageable);
		}
		List<GoalSummaryResponse> summaries = goals.getContent().stream()
			.map(GoalResponseMapper::mapToSummaryResponse).toList();
		PageResponse pageResponse = PageResponseMapper.mapToPageResponse(goals);

		return GoalResponseMapper.mapToSummaryPagingResponse(summaries, pageResponse);
	}

	@Transactional(readOnly = true)
	public GoalDetailResponse getGoalDetails(Long goalId) {
		GoalEntity goal = goalRepository.findByIdWithDetails(goalId).orElseThrow(()
			-> new CoreApiException(ErrorType.NOT_FOUND));
		GoalDetailResponse response = GoalResponseMapper.mapToDetailResponse(goal);
		if (SecurityUtil.isUserLoggedIn()) {
			log.info(">>>>>> User is logged in");
			Long currentUserId = SecurityUtil.getCurrentUserId();
			response = GoalResponseMapper.setParticipationStatus(response, isParticipated(currentUserId, goalId));
		}
		return response;
	}

	public ParticipateInGoalResponse participateInGoal(Long currentUserId, Long goalId) {
		MenteeEntity mentee = menteeService.getMenteeById(currentUserId);
		GoalEntity goal = getGoalById(goalId);

		validateParticipation(goal, mentee);
		mentee.decreaseFreeParticipationCount(); // 무료 참여 횟수 차감
		goal.increaseCurrentParticipants(); // 현재 참여자 수 증가

		MenteeGoalEntity menteeGoal = createMenteeGoal(goal, mentee);
		CommentRoomEntity commentRoom = createCommentRoom(goal.getMentor(), mentee, menteeGoal);

		// DailyTodo 복사
		copyAndSaveDailyTodos(goal, menteeGoal);

		ParticipateInGoalResponse response = new ParticipateInGoalResponse();
		response.setMenteeGoalId(menteeGoal.getId());
		response.setCommentRoomId(commentRoom.getId());
		return response;
	}

	@Transactional(readOnly = true)
	public GoalEntity getGoalById(Long goalId) {
		return goalRepository.findById(goalId).orElseThrow(() ->
			new CoreApiException(ErrorType.NOT_FOUND, "Goal not found"));
	}

	private void validateParticipation(GoalEntity goal, MenteeEntity menteeEntity) {
		if (!goal.isOpen()) {
			throw new CoreApiException(ErrorType.FORBIDDEN, "Goal is not started or closed");
		}
		if (isParticipated(menteeEntity.getId(), goal.getId())) {
			throw new CoreApiException(ErrorType.FORBIDDEN, "Already participated in this goal");
		}
		if (goal.isFull()) {
			throw new CoreApiException(ErrorType.FORBIDDEN, "There is no free participation count");
		}
		// if (!menteeEntity.hasFreeCount()) {
		// 	throw new CoreApiException(ErrorType.FORBIDDEN, "No free participation available.");
		// }
	}

	private boolean isParticipated(Long menteeId, Long goalId) {
		return menteeGoalRepository.existsByMenteeIdAndGoalId(menteeId, goalId);
	}

	private MenteeGoalEntity createMenteeGoal(GoalEntity goal, MenteeEntity mentee) {
		MenteeGoalEntity menteeGoal = MenteeGoalEntity.builder()
			.startDate(LocalDate.now())
			.endDate(LocalDate.now().plusDays(goal.getPeriod() - 1))
			.mentee(mentee)
			.goal(goal)
			.build();
		return menteeGoalRepository.save(menteeGoal);
	}

	private CommentRoomEntity createCommentRoom(MentorEntity mentor, MenteeEntity mentee, MenteeGoalEntity menteeGoal) {
		CommentRoomEntity commentRoom = new CommentRoomEntity(mentor, mentee, menteeGoal);
		commentRoom = commentRoomRepository.save(commentRoom);
		menteeGoal.updateCommentRoomId(commentRoom.getId());
		return commentRoom;
	}

	private void copyAndSaveDailyTodos(GoalEntity goal, MenteeGoalEntity menteeGoal) {
		List<MenteeGoalDailyTodoEntity> dailyTodoSnapshots = goal.getDailyTodos().stream()
			.map(dailyTodo -> new MenteeGoalDailyTodoEntity(dailyTodo, menteeGoal))
			.toList();

		// TODO: Bulk insert로 개선
		menteeGoalDailyTodoRepository.saveAll(dailyTodoSnapshots);
	}

	private void saveAllMidObjectives(List<MidObjectiveRequest> request, GoalEntity goal) {
		List<MidObjectiveEntity> midObjectiveEntities = request.stream()
			.map(midObjective -> new MidObjectiveEntity(
				midObjective.getSequence(),
				midObjective.getDescription(),
				goal))
			.toList();
		midObjectiveRepository.saveAll(midObjectiveEntities);
	}

	private void saveAllWeeklyObjectives(List<WeeklyObjectiveRequest> request, GoalEntity goal) {
		List<WeeklyObjectiveEntity> weeklyObjectiveEntities = request.stream()
			.map(weeklyObjective -> new WeeklyObjectiveEntity(
				weeklyObjective.getWeekNumber(),
				weeklyObjective.getDescription(),
				goal))
			.toList();
		weeklyObjectiveRepository.saveAll(weeklyObjectiveEntities);
	}

	private void saveAllThumbnailImages(List<ImageRequest> images, GoalEntity goal) {
		List<ThumbnailImageEntity> thumbnailImages = images.stream()
			.map(image -> new ThumbnailImageEntity(
				image.getSequence(),
				image.getImageUrl(),
				goal))
			.toList();
		thumbnailImageRepository.saveAll(thumbnailImages);
	}

	private void saveAllContentImages(List<ImageRequest> images, GoalEntity goal) {
		List<ContentImageEntity> contentImages = images.stream()
			.map(image -> new ContentImageEntity(
				image.getSequence(),
				image.getImageUrl(),
				goal))
			.toList();
		contentImageRepository.saveAll(contentImages);
	}

	private void saveAllTodos(List<TodoRequest> todoList, GoalEntity goal) {
		List<DailyTodoEntity> dailyTodos = todoList.stream()
			.map(todo -> DailyTodoEntity.builder()
				.dayNumber(todo.getDayNumber())
				.estimatedMinutes(todo.getEstimatedMinutes())
				.description(todo.getDescription())
				.mentorTip(todo.getMentorTip())
				.goal(goal)
				.build())
			.toList();
		dailyTodoRepository.saveAll(dailyTodos);
	}
}
