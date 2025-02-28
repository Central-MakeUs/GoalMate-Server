package com.goalmate.domain.admin;

import com.goalmate.domain.BaseEntity;
import com.goalmate.domain.mentee.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "admin")
public class AdminEntity extends BaseEntity {
	@Column(nullable = false)
	private String loginId;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.ROLE_ADMIN;
}
