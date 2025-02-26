package com.goalmate.domain.comment;

import com.goalmate.domain.BaseEntity;
import com.goalmate.domain.mentee.Role;

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
	private Long writerId;

	@Column(nullable = false)
	private String writerName;

	@Column(nullable = false)
	private Role writerRole;

	@Column(nullable = false)
	private boolean isRead = false;

	@ManyToOne(optional = false)
	@JoinColumn(name = "comment_room_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
	private CommentRoomEntity commentRoom;

	@Builder
	public CommentEntity(
		String comment,
		Long writerId,
		String writerName,
		Role writerRole,
		CommentRoomEntity commentRoom) {
		this.comment = comment;
		this.writerId = writerId;
		this.writerName = writerName;
		this.writerRole = writerRole;
		this.commentRoom = commentRoom;
		this.isRead = false;
	}

	public void updateComment(String comment) {
		this.comment = comment;
		this.isRead = false;
	}

	public void markAsRead(Role readerRole) {
		if (!readerRole.equals(this.writerRole) && !readerRole.equals(Role.ROLE_ADMIN)) {
			this.isRead = true;
		}
	}

	public boolean isWriter(Long writerId, Role role) {
		return this.writerId.equals(writerId) && this.writerRole.equals(role);
	}

}
