package com.goalmate.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.goal.GoalStatus;

import lombok.Builder;

@Builder
public record GoalSummary(
	Long id,
	String name,
	String topic,
	String description,
	Integer period,
	LocalDate startDate,
	LocalDate endDate,
	Integer price,
	Integer discountPrice,
	Integer participantsLimit,
	Integer freeParticipantsLimit,
	GoalStatus goalStatus,
	String mainImage,
	LocalDateTime createdAt,
	LocalDateTime updatedAt) {
	public static GoalSummary of(GoalEntity goalEntity) {
		return GoalSummary.builder()
			.id(goalEntity.getId())
			.name(goalEntity.getName())
			.topic(goalEntity.getTopic())
			.description(goalEntity.getDescription())
			.period(goalEntity.getPeriod())
			.startDate(goalEntity.getStartDate())
			.endDate(goalEntity.getEndDate())
			.price(goalEntity.getPrice())
			.discountPrice(goalEntity.getDiscountPrice())
			.participantsLimit(goalEntity.getParticipantsLimit())
			.freeParticipantsLimit(goalEntity.getFreeParticipantsLimit())
			.goalStatus(goalEntity.getGoalStatus())
			.mainImage("imageurl")
			.createdAt(goalEntity.getCreatedAt())
			.updatedAt(goalEntity.getUpdatedAt())
			.build();
	}
}
