package com.rag.chat.controller;

import org.springframework.web.bind.annotation.*;

import com.rag.chat.dto.CreateSessionRequest;
import com.rag.chat.dto.FavoriteRequest;
import com.rag.chat.dto.RenameSessionRequest;
import com.rag.chat.entity.ChatSession;
import com.rag.chat.service.ChatSessionService;

import java.util.UUID;

/**

 * Developed by Syyed
 */
@RestController
@RequestMapping("/api/v1/sessions")
public class ChatSessionController {
	private final ChatSessionService service;

	public ChatSessionController(ChatSessionService service) {
		this.service = service;
	}

	@PostMapping("/create")
	public ChatSession create(@RequestBody CreateSessionRequest req) {
		return service.create(req.getUserId(), req.getTitle());
	}

	@PutMapping("/{id}/rename")
	public void rename(@PathVariable UUID id, @RequestBody RenameSessionRequest req) {
		service.rename(id, req.getTitle());
	}

	@PatchMapping("/{id}/favorite")
	public void favorite(@PathVariable UUID id, @RequestBody FavoriteRequest req) {
		service.favorite(id, req.isFavorite());
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable UUID id) {
		service.delete(id);
	}
}
