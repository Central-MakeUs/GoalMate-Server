package com.goalmate.domain.menteeGoal;

import java.time.LocalDate;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "mentee_goal")
public class MenteeGoalEntity extends BaseEntity {
	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@Column
	private String mentorLetter;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MenteeGoalStatus status;

	@Column
	private Long commentRoomId;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "mentee_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MenteeEntity mentee;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "goal_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private GoalEntity goal;

	@Builder
	public MenteeGoalEntity(LocalDate startDate, LocalDate endDate, MenteeEntity mentee, GoalEntity goal) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = MenteeGoalStatus.IN_PROGRESS;
		this.mentee = mentee;
		this.goal = goal;
	}

	public void updateStatus(MenteeGoalStatus status) {
		this.status = status;
	}

	public void updateCommentRoomId(Long commentRoomId) {
		this.commentRoomId = commentRoomId;
	}

	public void updateMentorLetter(String mentorLetter) {
		this.mentorLetter = mentorLetter;
	}
}
