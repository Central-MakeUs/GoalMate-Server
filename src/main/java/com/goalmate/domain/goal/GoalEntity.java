package com.goalmate.domain.goal;

import java.time.LocalDate;
import java.util.List;

import com.goalmate.domain.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
	private String name;

	@Column(nullable = false)
	private String topic;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Integer period;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private Integer discountPrice;

	@Column(nullable = false)
	private Integer participantsLimit;

	@Column(nullable = false)
	private Integer freeParticipantsLimit;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GoalStatus goalStatus;

	@OneToMany(mappedBy = "goalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WeeklyObjectiveEntity> weeklyObjective;

	@OneToMany(mappedBy = "goalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MidObjectiveEntity> midObjective;

	@OneToMany(mappedBy = "goalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ThumbnailImageEntity> thumbnailImages;

	@OneToMany(mappedBy = "goalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ContentImageEntity> contentImages;

	@OneToMany(mappedBy = "goalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DailyTodoEntity> dailyTodos;

	public GoalEntity(
		String name,
		String topic,
		String description,
		Integer period,
		LocalDate startDate,
		LocalDate endDate,
		Integer price,
		Integer discountPrice,
		Integer participantsLimit,
		Integer freeParticipantsLimit,
		GoalStatus goalStatus) {
		this.name = name;
		this.topic = topic;
		this.description = description;
		this.period = period;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.discountPrice = discountPrice;
		this.participantsLimit = participantsLimit;
		this.freeParticipantsLimit = freeParticipantsLimit;
		this.goalStatus = goalStatus;
	}

}
