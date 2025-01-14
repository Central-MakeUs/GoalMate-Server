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
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "goal_weekly_objective")
public class GoalWeeklyObjective {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer weekNumber;

	@Column(nullable = false)
	private String description;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "goal_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Goal goal;

	public GoalWeeklyObjective(Integer weekNumber, String description, Goal goal) {
		this.weekNumber = weekNumber;
		this.description = description;
		this.goal = goal;
	}
}
