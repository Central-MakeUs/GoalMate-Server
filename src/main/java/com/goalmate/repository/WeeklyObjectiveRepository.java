package com.goalmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.goal.MidObjectiveEntity;
import com.goalmate.domain.goal.WeeklyObjectiveEntity;

public interface WeeklyObjectiveRepository extends JpaRepository<WeeklyObjectiveEntity, Long> {
}
