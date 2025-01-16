package com.goalmate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.goalmate.domain.goal.GoalEntity;

public interface GoalRepository extends JpaRepository<GoalEntity, Long> {
	@Query("SELECT g FROM GoalEntity g " +
		"LEFT JOIN FETCH g.dailyTodos " +
		"WHERE g.id = :id")
	Optional<GoalEntity> findByIdWithDetails(@Param("id") Long id);
}
