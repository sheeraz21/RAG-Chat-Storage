package com.rag.chat.service;


import com.rag.chat.entity.ChatSession;
import com.rag.chat.exception.NotFoundException;
import com.rag.chat.repository.ChatSessionDao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ChatSessionServiceTest {
	
    @Mock
    private ChatSessionDao chatSessionDao;

    @InjectMocks
    private ChatSessionService chatSessionService;
    
    @Test
    void create_shouldPersistNewSession() {
        when(chatSessionDao.persist(any(ChatSession.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ChatSession session = chatSessionService.create("user1", "My Chat");

        assertNotNull(session);
        assertEquals("user1", session.getUserId());
        assertEquals("My Chat", session.getTitle());

        verify(chatSessionDao).persist(any(ChatSession.class));
    }

    
    @Test
    void rename_shouldUpdateTitle() {
        UUID id = UUID.randomUUID();

        ChatSession session = new ChatSession();
        session.setId(id);
        session.setTitle("Old");

        when(chatSessionDao.findActiveById(id))
                .thenReturn(Optional.of(session));
        when(chatSessionDao.persist(any(ChatSession.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ChatSession updated = chatSessionService.rename(id, "New");

        assertEquals("New", updated.getTitle());
    }

    
    @Test
    void favorite_shouldUpdateFavoriteFlag() {
        UUID id = UUID.randomUUID();

        ChatSession session = new ChatSession();
        session.setId(id);
        session.setFavorite(false);

        when(chatSessionDao.findActiveById(id))
                .thenReturn(Optional.of(session));
        when(chatSessionDao.persist(any(ChatSession.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ChatSession updated = chatSessionService.favorite(id, true);

        assertTrue(updated.isFavorite());
    }

    
    @Test
    void delete_shouldMarkSessionAsDeleted() {
        UUID id = UUID.randomUUID();

        ChatSession session = new ChatSession();
        session.setId(id);
        session.setDeleted(false);

        when(chatSessionDao.findActiveById(id))
                .thenReturn(Optional.of(session));
        when(chatSessionDao.persist(any(ChatSession.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        chatSessionService.delete(id);

        assertTrue(session.isDeleted());
    }

    @Test
    void get_shouldReturnSessionWhenExists() {
        UUID id = UUID.randomUUID();

        ChatSession session = new ChatSession();
        session.setId(id);

        when(chatSessionDao.findActiveById(id))
                .thenReturn(Optional.of(session));

        ChatSession result = chatSessionService.get(id);

        assertEquals(id, result.getId());
    }

    
    @Test
    void get_shouldThrowExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();

        when(chatSessionDao.findActiveById(id))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> chatSessionService.get(id));
    }

    @Test
    void count_shouldReturnTotalSessions() {
        when(chatSessionDao.count()).thenReturn(5L);

        long count = chatSessionService.count();

        assertEquals(5L, count);
    }
  

}
