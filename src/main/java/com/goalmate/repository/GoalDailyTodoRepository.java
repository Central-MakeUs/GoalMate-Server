package com.goalmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.goal.DailyTodoEntity;
import com.goalmate.domain.goal.MidObjectiveEntity;

public interface GoalDailyTodoRepository extends JpaRepository<DailyTodoEntity, Long> {
	MidObjectiveEntity findByGoalId(Long goalId);
}
