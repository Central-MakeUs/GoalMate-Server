package com.goalmate.domain.goal;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "goal_daily_todo")
public class DailyTodoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer dayNumber;

	@Column(nullable = false)
	private Integer estimatedMinutes;

	@Column(nullable = false)
	private String description;

	@Column
	private String mentorTip;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "goal_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private GoalEntity goal;

	@Builder
	public DailyTodoEntity(
		Integer dayNumber,
		Integer estimatedMinutes,
		String description,
		String mentorTip,
		GoalEntity goal) {
		this.dayNumber = dayNumber;
		this.estimatedMinutes = estimatedMinutes;
		this.description = description;
		this.mentorTip = mentorTip;
		this.goal = goal;
	}
}
