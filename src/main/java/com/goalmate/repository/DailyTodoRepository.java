package com.goalmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.goal.DailyTodoEntity;

public interface DailyTodoRepository extends JpaRepository<DailyTodoEntity, Long> {
}
