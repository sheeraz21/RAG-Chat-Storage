package com.rag.chat.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
@Table(name = "chat_session")
public class ChatSession  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @Id
	    @GeneratedValue
	    @Column(name = "id", updatable = false, nullable = false)
	    private UUID id;
	
	@JdbcTypeCode(SqlTypes.ARRAY)
	@Column(name = "document_ids", columnDefinition = "uuid[]")
	private UUID[] documentIds;
	private String userId;
	private String title;
	private boolean favorite;
	private boolean deleted;
	private Instant createdAt;
	private Instant updatedAt;
	@PrePersist
	void onCreate() {
	createdAt = Instant.now();
	updatedAt = createdAt;
	}
	@PreUpdate
	void onUpdate() {
	updatedAt = Instant.now();
	}

	
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UUID[] getDocumentIds() {
		return documentIds;
	}
	public void setDocumentIds(UUID[] documentIds) {
		this.documentIds = documentIds;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Instant getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	public Instant getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
	

	
	
}
