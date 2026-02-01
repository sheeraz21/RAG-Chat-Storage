package com.rag.chat.dto;

import java.time.Instant;
import java.util.UUID;

import com.rag.chat.entity.SenderType;

public class ChatMessageResponse {

    private UUID id;
    private SenderType sender;
    private String content;
    private String retrievedContext;
    private Instant createdAt;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
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
