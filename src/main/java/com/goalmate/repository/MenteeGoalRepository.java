package com.goalmate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.menteeGoal.MenteeGoalEntity;

public interface MenteeGoalRepository extends JpaRepository<MenteeGoalEntity, Long> {
	// @Query("SELECT mg FROM MenteeGoalEntity mg WHERE mg.menteeId = :menteeId")
	List<MenteeGoalEntity> findByMenteeEntityId(Long menteeId);
}
