package com.goalmate.domain.goal;

import com.goalmate.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "goal")
public class Goal extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String topic;

	@Column(nullable = false)
	private Integer period;

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private Integer discount_price;

	@Column(nullable = false)
	private Integer participants_limit;

	@Column(nullable = false)
	private Integer free_participants_limit;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GoalStatus goalStatus;

	@Builder
	public Goal(String name, String topic, Integer period, Integer price, Integer discount_price,
		Integer participants_limit, Integer free_participants_limit, GoalStatus goalStatus) {
		this.name = name;
		this.topic = topic;
		this.period = period;
		this.price = price;
		this.discount_price = discount_price;
		this.participants_limit = participants_limit;
		this.free_participants_limit = free_participants_limit;
		this.goalStatus = goalStatus;
	}
}
