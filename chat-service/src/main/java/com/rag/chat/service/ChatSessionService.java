package com.rag.chat.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rag.chat.entity.ChatSession;
import com.rag.chat.exception.NotFoundException;
import com.rag.chat.repository.ChatSessionDao;


@Service
@Transactional
public class ChatSessionService {
	
	private final ChatSessionDao chatSessionDao;
	public ChatSessionService(ChatSessionDao chatSessionDao) {
	this.chatSessionDao = chatSessionDao;
	}
	
	public ChatSession create(String userId, String title) {
		ChatSession session = new ChatSession();
		session.setUserId(userId);
		session.setTitle(title);
		
		return chatSessionDao.persist(session);
	
		}
	
	
	  public ChatSession rename(UUID id, String title) {
	        ChatSession session = get(id);
	        session.setTitle(title);
	        return chatSessionDao.persist(session);
	    }
	

	  
	   public ChatSession favorite(UUID id, boolean favorite) {
	        ChatSession session = get(id);
	        session.setFavorite(favorite);
	        return chatSessionDao.persist(session);
	    }
	  
	

	   
	   public void delete(UUID id) {
	        ChatSession session = get(id);
	        session.setDeleted(true);
	        chatSessionDao.persist(session);
	    }
	   

	   
	   public ChatSession get(UUID id) {
	        return chatSessionDao.findActiveById(id)
	                .orElseThrow(() -> new NotFoundException("Session not found"));
	    }
	   
	   public long count() {
		   return chatSessionDao.count();
		   
	   }
	   
}
