package com.goalmate.domain.comment;

import com.goalmate.domain.BaseEntity;
import com.goalmate.domain.mentee.MenteeEntity;
import com.goalmate.domain.menteeGoal.MenteeGoalEntity;
import com.goalmate.domain.mentor.MentorEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment_room")
public class CommentRoomEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private boolean forceUpdate = false;

	@ManyToOne
	@JoinColumn(name = "mentor_id", nullable = false)
	private MentorEntity mentor;

	@ManyToOne
	@JoinColumn(name = "mentee_id", nullable = false)
	private MenteeEntity mentee;

	@OneToOne
	@JoinColumn(name = "mentee_goal_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MenteeGoalEntity menteeGoal;

	public CommentRoomEntity(MentorEntity mentor, MenteeEntity mentee, MenteeGoalEntity menteeGoal) {
		this.mentor = mentor;
		this.mentee = mentee;
		this.menteeGoal = menteeGoal;
	}

	public void triggerUpdate() {
		this.forceUpdate = !this.forceUpdate; // 더티 체킹을 위한 변경
	}
}
