package com.goalmate.domain.menteeGoal;

import java.time.LocalDateTime;

import com.goalmate.domain.BaseEntity;
import com.goalmate.domain.goal.GoalEntity;
import com.goalmate.domain.mentee.MenteeEntity;

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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "mentee_goal")
public class MenteeGoalEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime joinedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MenteeGoalStatus status;

	@Lob
	@Column(nullable = true)
	private String finalComment;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "mentee_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MenteeEntity menteeEntity;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "goal_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private GoalEntity goalEntity;

	@Builder
	public MenteeGoalEntity(LocalDateTime joinedAt, MenteeEntity menteeEntity, GoalEntity goalEntity) {
		this.joinedAt = joinedAt;
		this.status = MenteeGoalStatus.IN_PROGRESS;
		this.finalComment = null;
		this.menteeEntity = menteeEntity;
		this.goalEntity = goalEntity;
	}
}
