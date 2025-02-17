package com.goalmate.domain.goal;

import java.util.List;

import com.goalmate.domain.BaseEntity;
import com.goalmate.domain.mentor.MentorEntity;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Entity
@Table(name = "goal")
public class GoalEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String topic;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Integer period;

	@Column(nullable = false)
	private Integer dailyDuration;

	@Column
	private Integer price;

	@Column
	private Integer discountPrice;

	@Column(nullable = false)
	private Integer participantsLimit;

	@Column(nullable = false)
	private Integer currentParticipants = 0;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GoalStatus goalStatus;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "mentor_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MentorEntity mentor;

	@OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MidObjectiveEntity> midObjective;

	@OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WeeklyObjectiveEntity> weeklyObjective;

	@OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ThumbnailImageEntity> thumbnailImages;

	@OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ContentImageEntity> contentImages;

	@OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DailyTodoEntity> dailyTodos;

	@Builder
	public GoalEntity(
		String title,
		String topic,
		String description,
		Integer period,
		Integer dailyDuration,
		Integer participantsLimit,
		GoalStatus goalStatus,
		MentorEntity mentor) {
		this.title = title;
		this.topic = topic;
		this.description = description;
		this.period = period;
		this.dailyDuration = dailyDuration;
		this.participantsLimit = participantsLimit;
		this.goalStatus = goalStatus;
		this.mentor = mentor;
		this.midObjective = List.of();
		this.weeklyObjective = List.of();
		this.thumbnailImages = List.of();
		this.contentImages = List.of();
		this.dailyTodos = List.of();
	}

	public void increaseCurrentParticipants() {
		this.currentParticipants++;
	}

	public boolean isFull() {
		return this.currentParticipants >= this.participantsLimit;
	}

	public boolean isOpen() {
		return this.goalStatus.equals(GoalStatus.IN_PROGRESS);
	}
}
