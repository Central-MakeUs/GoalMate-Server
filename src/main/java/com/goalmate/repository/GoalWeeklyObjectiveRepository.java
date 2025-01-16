package com.goalmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.goal.MidObjectiveEntity;
import com.goalmate.domain.goal.WeeklyObjectiveEntity;

public interface GoalWeeklyObjectiveRepository extends JpaRepository<WeeklyObjectiveEntity, Long> {
	MidObjectiveEntity findByGoalId(Long goalId);
}
