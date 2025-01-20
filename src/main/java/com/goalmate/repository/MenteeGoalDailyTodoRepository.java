package com.goalmate.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goalmate.domain.menteeGoal.MenteeGoalDailyTodoEntity;

public interface MenteeGoalDailyTodoRepository extends JpaRepository<MenteeGoalDailyTodoEntity, Long> {

	@Query("SELECT m FROM MenteeGoalDailyTodoEntity m WHERE m.menteeGoalEntity.id = :menteeGoalId")
	List<MenteeGoalDailyTodoEntity> findByMenteeGoalId(Long menteeId);

	@Query("SELECT m FROM MenteeGoalDailyTodoEntity m WHERE m.menteeGoalEntity.id = :menteeGoalId AND m.todoDate = :date")
	Optional<MenteeGoalDailyTodoEntity> findByMenteeGoalIdAndDate(Long menteeGoalId, LocalDate date);
}
