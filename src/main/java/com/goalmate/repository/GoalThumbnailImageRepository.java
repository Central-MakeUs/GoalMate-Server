package com.goalmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.goal.MidObjectiveEntity;
import com.goalmate.domain.goal.ThumbnailImageEntity;

public interface GoalThumbnailImageRepository extends JpaRepository<ThumbnailImageEntity, Long> {
	MidObjectiveEntity findByGoalId(Long goalId);
}
