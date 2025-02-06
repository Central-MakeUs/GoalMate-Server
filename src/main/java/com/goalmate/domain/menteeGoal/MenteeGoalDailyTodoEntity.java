package com.goalmate.domain.menteeGoal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.goalmate.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "mentee_goal_daily_todo")
public class MenteeGoalDailyTodoEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDate todoDate;

	@Column(nullable = false)
	private Integer estimatedMinutes;

	@Column(nullable = false)
	private String description;

	@Column
	private String mentorTip;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TodoStatus status;

	@Column
	private LocalDateTime completedAt;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "mentee_goal_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MenteeGoalEntity menteeGoalEntity;

	@Builder
	public MenteeGoalDailyTodoEntity(LocalDate todoDate, Integer estimatedMinutes, String description,
		String mentorTip, MenteeGoalEntity menteeGoalEntity) {
		this.todoDate = todoDate;
		this.estimatedMinutes = estimatedMinutes;
		this.description = description;
		this.mentorTip = mentorTip;
		this.status = TodoStatus.TODO;
		this.menteeGoalEntity = menteeGoalEntity;
	}

	public boolean isCompleted() {
		return status == TodoStatus.COMPLETED;
	}

	public void toggleStatus() {
		if (status == TodoStatus.TODO) {
			status = TodoStatus.COMPLETED;
			completedAt = LocalDateTime.now();
		} else {
			status = TodoStatus.TODO;
			completedAt = null;
		}
	}
}
