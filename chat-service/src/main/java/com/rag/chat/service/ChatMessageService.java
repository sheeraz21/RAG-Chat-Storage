package com.rag.chat.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rag.chat.dto.ChatMessageResponse;
import com.rag.chat.dto.CreateMessageRequest;
import com.rag.chat.entity.ChatMessage;
import com.rag.chat.entity.ChatSession;
import com.rag.chat.repository.ChatMessageDao;


@Service
@Transactional
public class ChatMessageService {
	
	

	private final ChatMessageDao messageDao;
	private final ChatSessionService sessionService;
	
	
	
	public ChatMessageService(ChatMessageDao messageDao,
	ChatSessionService sessionService) {
	this.messageDao = messageDao;
	this.sessionService = sessionService;

	}
	
	public ChatMessage add(UUID sessionId, CreateMessageRequest req) {

	    ChatSession session = sessionService.get(sessionId);

	    ChatMessage msg = new ChatMessage();
	    msg.setSession(session);
	    msg.setSender(req.getSender());
	    msg.setContent(req.getContent());

	    // Convert JsonNode -> String (JSON)
	    if (req.getRetrievedContext() != null) {
	        msg.setRetrievedContext(req.getRetrievedContext().toString());
	    }

	    return messageDao.persist(msg);
	}
	
	public Page<ChatMessageResponse> list(UUID sessionId, Pageable pageable) {
		
		Page<ChatMessage> page = messageDao.findBySession(sessionId, pageable);
		
		
	    return page.map(msg -> {
	        ChatMessageResponse dto = new ChatMessageResponse();
	        dto.setId(msg.getId());
	        dto.setSender(msg.getSender());
	        dto.setContent(msg.getContent());
	        dto.setRetrievedContext(msg.getRetrievedContext());
	        dto.setCreatedAt(msg.getCreatedAt());
	        return dto;
	    });

}

}