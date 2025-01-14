package com.goalmate.domain.mentee;

import com.goalmate.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "mentee")
public class Mentee extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String socialId;

	@Column(nullable = false)
	private Long freeJoinCount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SocialProvider provider;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MenteeStatus status;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;

	@Builder
	public Mentee(String email, String socialId, SocialProvider provider) {
		this.email = email;
		this.socialId = socialId;
		this.freeJoinCount = 1L;    // 기본 제공 1회
		this.provider = provider;
		this.status = MenteeStatus.PENDING; // 기본 Pending 상태
		this.role = UserRole.USER; // 기본값
	}

	public void updateName(String name) {
		if (name != null) {
			this.name = name;
			this.status = MenteeStatus.ACTIVE;
		}
	}

	public boolean isPending() {
		return status == MenteeStatus.PENDING;
	}
}
