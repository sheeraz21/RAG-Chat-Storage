package com.rag.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rag.chat.dto.CreateMessageRequest;
import com.rag.chat.entity.ChatMessage;
import com.rag.chat.entity.ChatSession;
import com.rag.chat.entity.SenderType;
import com.rag.chat.repository.ChatMessageDao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatMessageServiceTest {

	@Mock
	private ChatMessageDao messageDao;

	@Mock
	private ChatSessionService sessionService;

	@InjectMocks
	private ChatMessageService chatMessageService;
	
	
	
	@Test
	void add_shouldCreateAndPersistMessage() {
	    UUID sessionId = UUID.randomUUID();
	    ChatSession session = new ChatSession();
	    session.setId(sessionId);

	    CreateMessageRequest request = new CreateMessageRequest();
	    request.setSender(SenderType.USER);
	    request.setContent("Hello");

	    when(sessionService.get(sessionId)).thenReturn(session);
	    when(messageDao.persist(any(ChatMessage.class)))
	            .thenAnswer(invocation -> invocation.getArgument(0));

	    ChatMessage result = chatMessageService.add(sessionId, request);

	    assertNotNull(result);
	    assertEquals(SenderType.USER, result.getSender());
	    assertEquals("Hello", result.getContent());
	    assertEquals(session, result.getSession());

	    verify(sessionService).get(sessionId);
	    verify(messageDao).persist(any(ChatMessage.class));
	}

	@Test
	void add_shouldStoreRetrievedContextAsString() {
	    UUID sessionId = UUID.randomUUID();
	    ChatSession session = new ChatSession();
	    session.setId(sessionId);

	    CreateMessageRequest request = new CreateMessageRequest();
	    request.setSender(SenderType.ASSISTANT);
	    request.setContent("Answer");
	    request.setRetrievedContext("{\"source\":\"doc1\"}");

	    when(sessionService.get(sessionId)).thenReturn(session);
	    when(messageDao.persist(any(ChatMessage.class)))
	            .thenAnswer(invocation -> invocation.getArgument(0));

	    ChatMessage result = chatMessageService.add(sessionId, request);

	    assertNotNull(result.getRetrievedContext());
	    assertTrue(result.getRetrievedContext().contains("doc1"));
	}

	@Test
	void list_shouldReturnPagedMessageResponses() {
	    UUID sessionId = UUID.randomUUID();

	    ChatMessage message = new ChatMessage();
	    message.setId(UUID.randomUUID());
	    message.setSender(SenderType.USER);
	    message.setContent("Hello");

	    Page<ChatMessage> page =
	            new PageImpl<>(List.of(message), PageRequest.of(0, 10), 1);

	    when(messageDao.findBySession(eq(sessionId), any(Pageable.class)))
	            .thenReturn(page);

	    Page<?> result = chatMessageService.list(sessionId, PageRequest.of(0, 10));

	    assertEquals(1, result.getTotalElements());

	    var dto = result.getContent().get(0);
	    assertEquals(SenderType.USER, ((com.rag.chat.dto.ChatMessageResponse) dto).getSender());
	    verify(messageDao).findBySession(eq(sessionId), any(Pageable.class));
	}

	
	
}
