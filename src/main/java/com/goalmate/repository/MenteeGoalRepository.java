package com.goalmate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goalmate.domain.menteeGoal.MenteeGoalEntity;

public interface MenteeGoalRepository extends JpaRepository<MenteeGoalEntity, Long> {
	@Query("SELECT m FROM MenteeGoalEntity m WHERE m.menteeEntity.id = :menteeId ORDER BY m.createdAt DESC")
	List<MenteeGoalEntity> findByMenteeId(Long menteeId);

	@Query("SELECT m FROM MenteeGoalEntity m WHERE m.menteeEntity.id = :menteeId ORDER BY m.createdAt DESC")
	Page<MenteeGoalEntity> findByMenteeId(Long menteeId, Pageable pageable);

	@Query("SELECT m FROM MenteeGoalEntity m WHERE m.menteeEntity.id = :menteeId AND m.goalEntity.id = :goalId")
	Optional<MenteeGoalEntity> findByMenteeIdAndGoalId(Long menteeId, Long goalId);
}
