package com.goalmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.goal.MidObjectiveEntity;

public interface MidObjectiveRepository extends JpaRepository<MidObjectiveEntity, Long> {
}
