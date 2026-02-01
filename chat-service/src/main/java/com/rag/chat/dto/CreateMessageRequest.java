package com.rag.chat.dto;

import com.rag.chat.entity.SenderType;

public class CreateMessageRequest {

	   private SenderType sender;
	    private String content;

	    // JSON string for RAG retrieved context
	    private String retrievedContext;

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
	    
	    
	    
}
