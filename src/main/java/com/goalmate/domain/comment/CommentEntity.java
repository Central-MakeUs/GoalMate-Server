package com.goalmate.domain.comment;

import com.goalmate.domain.BaseEntity;
import com.goalmate.domain.mentee.Role;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
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
	@Lob
	@Column(nullable = false)
	private String comment;

	@Column(nullable = false)
	private Long senderId;

	@Column(nullable = false)
	private String senderName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role senderRole;

	@Column(nullable = false)
	private Long receiverId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role receiverRole;

	@Column(nullable = false)
	private boolean isRead = false;

	@ManyToOne(optional = false)
	@JoinColumn(name = "comment_room_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
	private CommentRoomEntity commentRoom;

	@Builder
	public CommentEntity(
		String comment,
		Long senderId,
		String senderName,
		Role senderRole,
		Long receiverId,
		Role receiverRole,
		CommentRoomEntity commentRoom) {
		this.comment = comment;
		this.senderId = senderId;
		this.senderName = senderName;
		this.senderRole = senderRole;
		this.receiverId = receiverId;
		this.receiverRole = receiverRole;
		this.commentRoom = commentRoom;
		this.isRead = false;
	}

	public void updateComment(String comment) {
		this.comment = comment;
		this.isRead = false;
	}

	public void markAsRead(Role senderRole) {
		if (!senderRole.equals(this.senderRole) && !senderRole.equals(Role.ROLE_ADMIN)) {
			this.isRead = true;
		}
	}

	public boolean isSender(Long senderId, Role role) {
		return this.senderId.equals(senderId) && this.senderRole.equals(role);
	}

}
