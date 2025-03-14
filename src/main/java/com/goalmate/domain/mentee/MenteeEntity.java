package com.goalmate.domain.mentee;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.goalmate.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "mentee")
public class MenteeEntity extends BaseEntity {
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false, unique = true)
	private String socialId;

	@Column(nullable = false)
	private Integer freeParticipationCount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SocialProvider provider;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MenteeStatus status;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Builder
	public MenteeEntity(String name, String email, String socialId, SocialProvider provider) {
		this.name = name;
		this.email = email;
		this.socialId = socialId;
		this.freeParticipationCount = 1;    // 기본 제공 1회
		this.provider = provider;
		this.status = MenteeStatus.PENDING; // 기본 Pending 상태
		this.role = Role.ROLE_MENTEE; // 기본값
	}

	public void updateName(String name) {
		if (name != null) {
			this.name = name;
			updateToActive();
		}
	}

	public boolean isPending() {
		return status == MenteeStatus.PENDING;
	}

	public boolean isDeleted() {
		return status == MenteeStatus.DELETED;
	}

	public boolean hasFreeCount() {
		return freeParticipationCount > 0;
	}

	public void decreaseFreeParticipationCount() {
		freeParticipationCount--;
	}

	private void updateToActive() {
		this.status = MenteeStatus.ACTIVE;
	}

	public void delete() {
		this.name = "Unknown";
		this.status = MenteeStatus.DELETED;
		String prefix = "Deleted-" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) + "-";
		this.socialId = prefix + socialId;
	}
}
