package com.goalmate.domain.menteeGoal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.goalmate.domain.BaseEntity;
import com.goalmate.domain.goal.DailyTodoEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "mentee_goal_daily_todo")
public class MenteeGoalDailyTodoEntity extends BaseEntity {
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

	public MenteeGoalDailyTodoEntity(DailyTodoEntity dailyTodo, MenteeGoalEntity menteeGoal) {
		this.todoDate = LocalDate.now().plusDays(dailyTodo.getDayNumber() - 1); // 1일차 -> 오늘, 2일차 -> 내일
		this.estimatedMinutes = dailyTodo.getEstimatedMinutes();
		this.description = dailyTodo.getDescription();
		this.mentorTip = dailyTodo.getMentorTip();
		this.status = TodoStatus.TODO; // 초기상태
		this.menteeGoalEntity = menteeGoal;
	}

	public boolean isCompleted() {
		return status == TodoStatus.COMPLETED;
	}

	public void updateStatus(TodoStatus status) {
		this.status = status;
		if (status == TodoStatus.COMPLETED) {
			completedAt = LocalDateTime.now();
		} else {
			completedAt = null;
		}
	}
}
