package com.rag.chat.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rag.chat.dto.ChatMessageResponse;
import com.rag.chat.dto.CreateMessageRequest;
import com.rag.chat.entity.ChatMessage;
import com.rag.chat.service.ChatMessageService;

/**

 * Developed by Syyed
 */
@RestController
@RequestMapping("/api/v1/sessions/{sessionId}/messages")
public class ChatMessageController {
	private final ChatMessageService service;

	public ChatMessageController(ChatMessageService service) {
		this.service = service;
	}

	@PostMapping("/add")
	public ChatMessage add(@PathVariable UUID sessionId, @RequestBody CreateMessageRequest req) {
		return service.add(sessionId, req);
	}

	@GetMapping("/list")
	public Page<ChatMessageResponse>list(@PathVariable UUID sessionId, @RequestParam int page, @RequestParam int size) {
		return service.list(sessionId, PageRequest.of(page, size));
	}
}
