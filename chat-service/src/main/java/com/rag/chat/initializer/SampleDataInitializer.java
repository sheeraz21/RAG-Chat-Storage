package com.rag.chat.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.rag.chat.dto.CreateMessageRequest;
import com.rag.chat.entity.ChatMessage;
import com.rag.chat.entity.ChatSession;
import com.rag.chat.entity.SenderType;
import com.rag.chat.repository.ChatMessageDao;
import com.rag.chat.repository.ChatSessionDao;
import com.rag.chat.service.ChatMessageService;
import com.rag.chat.service.ChatSessionService;



@Component
public class SampleDataInitializer implements CommandLineRunner {

    private final ChatMessageService chatMessageService;
    private final ChatSessionService chatSessionService;

    public SampleDataInitializer(ChatMessageService chatMessageService,
    		ChatSessionService chatSessionService) {
        this.chatMessageService = chatMessageService;
        this.chatSessionService = chatSessionService;
    }

    @Override
    public void run(String... args) {

        if (chatSessionService.count() > 0) {
            return;
        }

        ChatSession session = new ChatSession();
        session.setUserId("user123");
        session.setTitle("Demo Chat Session");
        session.setFavorite(false);
        session.setDeleted(false);
        
        session = chatSessionService.create(session.getUserId(), session.getTitle());

        String retrievedContextJson = """
            {
              "source": "demo",
              "confidence": 0.98,
              "tokens": 128
            }
        """;

        ChatMessage userMessage = new ChatMessage();
        userMessage.setSession(session);
        userMessage.setSender(SenderType.USER);
        userMessage.setContent("Hello, this is the first message!");
        userMessage.setRetrievedContext(retrievedContextJson);

        ChatMessage assistantMessage = new ChatMessage();
        assistantMessage.setSession(session);
        assistantMessage.setSender(SenderType.ASSISTANT);
        assistantMessage.setContent("This is a reply from the bot.");
        assistantMessage.setRetrievedContext(retrievedContextJson);
        
        CreateMessageRequest req = new CreateMessageRequest();
        req.setContent("Hello, this is the first message!");
        req.setRetrievedContext(retrievedContextJson);
        req.setSender(SenderType.USER);
        
        CreateMessageRequest reqAssitant = new CreateMessageRequest();
        reqAssitant.setContent("This is a reply from the bot.");
        reqAssitant.setRetrievedContext(retrievedContextJson);
        reqAssitant.setSender(SenderType.ASSISTANT);
       
        chatMessageService.add(session.getId(),reqAssitant);

        chatMessageService.add(session.getId(), req);

        System.out.println("Sample data inserted");
    }
}
