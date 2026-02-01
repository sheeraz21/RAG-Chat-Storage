package com.rag.chat.repository;

import com.rag.chat.entity.ChatSession;

import java.util.Optional;
import java.util.UUID;

public interface ChatSessionDao {
	
    ChatSession persist(ChatSession session);

    ChatSession merge(ChatSession session);

    Optional<ChatSession> findActiveById(UUID id);
    
    public long count();

}
