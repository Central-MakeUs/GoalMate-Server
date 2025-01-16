package com.goalmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.goal.ContentImageEntity;
import com.goalmate.domain.goal.MidObjectiveEntity;

public interface GoalContentImageRepository extends JpaRepository<ContentImageEntity, Long> {
	MidObjectiveEntity findByGoalId(Long goalId);
}
