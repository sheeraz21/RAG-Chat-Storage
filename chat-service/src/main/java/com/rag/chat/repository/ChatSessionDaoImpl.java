package com.rag.chat.repository;

import com.rag.chat.entity.ChatSession;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ChatSessionDaoImpl implements ChatSessionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ChatSession persist(ChatSession session) {
        entityManager.persist(session);
        return session;
    }

    @Override
    public ChatSession merge(ChatSession session) {
        return entityManager.merge(session);
    }

    @Override
    public Optional<ChatSession> findActiveById(UUID id) {
        return entityManager
                .createQuery("""
                    SELECT s
                    FROM ChatSession s
                    WHERE s.id = :id
                      AND s.deleted = false
                """, ChatSession.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }
    
    @Override
    public long count() {
        return entityManager
                .createQuery("select count(s) from ChatSession s", Long.class)
                .getSingleResult();
    }
    
}
