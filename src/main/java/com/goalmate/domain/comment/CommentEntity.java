package com.goalmate.domain.comment;

import java.time.LocalDate;

import com.goalmate.domain.BaseEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(nullable = false)
	private String comment;

	@Column(nullable = false)
	private LocalDate commentDate;

	@Column(nullable = false)
	private String writer;

	@Column(nullable = false)
	private boolean isRead = false;

	@ManyToOne(optional = false)
	@JoinColumn(name = "mentee_goal_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
	private MenteeGoalEntity menteeGoalEntity;

	@Builder
	public CommentEntity(String comment, LocalDate commentDate, String writer, MenteeGoalEntity menteeGoalEntity) {
		this.comment = comment;
		this.commentDate = commentDate;
		this.writer = writer;
		this.menteeGoalEntity = menteeGoalEntity;
		this.isRead = false;
	}

	public void markAsRead() {
		this.isRead = true;
	}
}
