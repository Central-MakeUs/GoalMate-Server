package com.goalmate.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.goalmate.api.model.MenteeGoalSummaryResponse;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.goal.GoalStatus;
import com.goalmate.domain.mentee.MenteeEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.domain.mentor.MentorEntity;
import com.goalmate.repository.MenteeGoalDailyTodoRepository;
import com.goalmate.repository.MenteeGoalRepository;

@SpringBootTest
class MenteeGoalServiceTest {

	@MockitoBean
	private MenteeGoalRepository menteeGoalRepository;

	@MockitoBean
	private MenteeGoalDailyTodoRepository menteeGoalDailyTodoRepository;

	@Autowired
	private MenteeGoalService menteeGoalService;

	private MenteeGoalEntity menteeGoal;

	@BeforeEach
	void setUp() {
		MenteeEntity mentee = MenteeEntity.builder()
			.email("mentee@goalmate.com")
			.build();
		mentee.updateName("mentee");
		MentorEntity mentor = new MentorEntity("mentor", "mentor@goalmate.com", "profile");

		GoalEntity realGoal = GoalEntity.builder()
			.title("Test Goal")
			.topic("Test Topic")
			.description("Test Description")
			.period(30)
			.dailyDuration(30)
			.participantsLimit(10)
			.goalStatus(GoalStatus.OPEN)
			.mentor(mentor)
			.build();

		GoalEntity goal = spy(realGoal);

		doReturn(LocalDateTime.now()).when(goal).getCreatedAt();
		doReturn(LocalDateTime.now()).when(goal).getUpdatedAt();

		MenteeGoalEntity realMenteeGoal = MenteeGoalEntity.builder()
			.startDate(LocalDate.now())
			.endDate(LocalDate.now().plusDays(30))
			.goal(goal)
			.mentee(mentee)
			.build();

		menteeGoal = spy(realMenteeGoal);

		doReturn(LocalDateTime.now()).when(menteeGoal).getCreatedAt();
		doReturn(LocalDateTime.now()).when(menteeGoal).getUpdatedAt();
	}

	@Test
	void updateMentorLetterTest() {
		// Given
		Long menteeGoalId = 1L;
		String mentorLetter = "New Mentor Letter";

		when(menteeGoalRepository.findById(menteeGoalId)).thenReturn(Optional.of(menteeGoal));
		when(menteeGoalDailyTodoRepository.findByMenteeGoalId(menteeGoalId)).thenReturn(Collections.emptyList());

		// When
		MenteeGoalSummaryResponse result = menteeGoalService.updateMentorLetter(menteeGoalId, mentorLetter);

		// Then
		verify(menteeGoal).updateMentorLetter(mentorLetter); // mentorLetter 업데이트 확인
		assertEquals(mentorLetter, result.getMentorLetter()); // 반환값 검증
	}

}
