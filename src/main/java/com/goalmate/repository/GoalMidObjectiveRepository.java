package com.goalmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.goal.MidObjectiveEntity;

public interface GoalMidObjectiveRepository extends JpaRepository<MidObjectiveEntity, Long> {
	MidObjectiveEntity findByGoalId(Long goalId);
}
