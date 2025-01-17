package com.goalmate.domain.menteeGoal;

import java.time.LocalDate;

import com.goalmate.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "mentee_goal_daily_comment")
public class MenteeGoalDailyCommentEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(nullable = false)
	private String comment;

	@Column(nullable = false)
	private LocalDate commentDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "mentee_goal_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
	private MenteeGoalEntity menteeGoalEntity;
}
