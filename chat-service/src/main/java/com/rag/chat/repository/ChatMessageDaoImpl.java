package com.rag.chat.repository;

import com.rag.chat.entity.ChatMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository

public class ChatMessageDaoImpl implements ChatMessageDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<ChatMessage> findBySession(UUID sessionId, Pageable pageable) {

		// 🔹 Data query
		Query dataQuery = entityManager.createNativeQuery("""
				    SELECT *
				    FROM chat_message 
				    WHERE session_id = :sessionId
				    ORDER BY created_at ASC
				""", ChatMessage.class);

		dataQuery.setParameter("sessionId", sessionId);
		dataQuery.setFirstResult((int) pageable.getOffset());
		dataQuery.setMaxResults(pageable.getPageSize());

		@SuppressWarnings("unchecked")
		List<ChatMessage> results = dataQuery.getResultList();

		// 🔹 Count query
		Query countQuery = entityManager.createNativeQuery("""
				    SELECT COUNT(*)
				    FROM chat_message 
				    WHERE session_id = :sessionId
				""");
		countQuery.setParameter("sessionId", sessionId);

		long total = ((Number) countQuery.getSingleResult()).longValue();

		return new PageImpl<>(results, pageable, total);
	}
	
	
	   @Override
	    public ChatMessage persist(ChatMessage message) {
	        entityManager.persist(message);
	        return message; // managed entity
	    }

}
