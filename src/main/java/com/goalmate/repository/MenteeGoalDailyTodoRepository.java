package com.goalmate.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goalmate.domain.menteeGoal.MenteeGoalDailyTodoEntity;

public interface MenteeGoalDailyTodoRepository extends JpaRepository<MenteeGoalDailyTodoEntity, Long> {

	@Query("SELECT COUNT(m) > 0 FROM MenteeGoalDailyTodoEntity m " +
		"WHERE m.menteeGoalEntity.menteeEntity.id = :menteeId " +
		"AND m.todoDate = :date " +
		"AND m.status != 'COMPLETED'")
	boolean existsByMenteeIdAndDateAndIsNotCompleted(Long menteeId, LocalDate date);

	@Query("SELECT m FROM MenteeGoalDailyTodoEntity m WHERE m.menteeGoalEntity.id = :menteeGoalId")
	List<MenteeGoalDailyTodoEntity> findByMenteeGoalId(Long menteeId);

	@Query("SELECT m FROM MenteeGoalDailyTodoEntity m WHERE m.menteeGoalEntity.id = :menteeGoalId AND m.todoDate = :date")
	List<MenteeGoalDailyTodoEntity> findByMenteeGoalIdAndDate(Long menteeGoalId, LocalDate date);
}
