package com.goalmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goalmate.domain.goal.ThumbnailImageEntity;

public interface ThumbnailImageRepository extends JpaRepository<ThumbnailImageEntity, Long> {
}
