package com.rag.chat.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_message")
public class ChatMessage {
	
	 @Id
	    @GeneratedValue
	    @Column(name = "id", updatable = false, nullable = false)
	    private UUID id;

	@JdbcTypeCode(SqlTypes.ARRAY)
	@Column(name = "document_ids", columnDefinition = "uuid[]")
	private UUID[] documentIds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private ChatSession session;

    @Enumerated(EnumType.STRING)
    private SenderType sender;

    @Column(columnDefinition = "TEXT")
    private String content;

    
    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String retrievedContext;

    private Instant createdAt = Instant.now();

    public void setSession(ChatSession session) {
        this.session = session;
    }

    public ChatSession getSession() {
        return session;
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

	public SenderType getSender() {
		return sender;
	}

	public void setSender(SenderType sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRetrievedContext() {
		return retrievedContext;
	}

	public void setRetrievedContext(String retrievedContext) {
		this.retrievedContext = retrievedContext;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

  
}
