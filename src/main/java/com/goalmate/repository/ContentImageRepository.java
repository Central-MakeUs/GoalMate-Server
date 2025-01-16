package com.goalmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.goal.ContentImageEntity;

public interface ContentImageRepository extends JpaRepository<ContentImageEntity, Long> {
}
