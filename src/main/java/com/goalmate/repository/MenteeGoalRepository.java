package com.goalmate.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goalmate.domain.menteeGoal.MenteeGoalEntity;

public interface MenteeGoalRepository extends JpaRepository<MenteeGoalEntity, Long> {
	@Query("SELECT m FROM MenteeGoalEntity m WHERE m.mentee.id = :menteeId ORDER BY m.createdAt DESC")
	List<MenteeGoalEntity> findByMenteeId(Long menteeId);

	@Query("SELECT m FROM MenteeGoalEntity m WHERE m.mentee.id = :menteeId ORDER BY m.status DESC, m.endDate ASC")
	Page<MenteeGoalEntity> findByMenteeId(Long menteeId, Pageable pageable);

	@Query("SELECT COUNT(m) > 0 FROM MenteeGoalEntity m WHERE m.mentee.id = :menteeId AND m.goal.id = :goalId")
	boolean existsByMenteeIdAndGoalId(Long menteeId, Long goalId);

	@Query("SELECT m FROM MenteeGoalEntity m WHERE m.goal.id = :goalId ORDER BY m.createdAt DESC")
	Page<MenteeGoalEntity> findByGoalId(Long goalId, Pageable pageable);

	long countByGoalId(Long goalId);
}
