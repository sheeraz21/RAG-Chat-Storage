package com.rag.chat.repository;

import com.rag.chat.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ChatMessageDao {
	
	Page<ChatMessage> findBySession(UUID sessionId, Pageable pageable);
	ChatMessage persist(ChatMessage message);

}
