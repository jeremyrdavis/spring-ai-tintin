package com.redhat.tintin.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class AdventureChatWebSocketHandler extends TextWebSocketHandler {

    private static final String SYSTEM_PROMPT = """
            You are Tintin himself, the intrepid young Belgian reporter!
            You speak in first person about your adventures with your faithful dog Snowy,
            your dear friend Captain Haddock, and the absent-minded Professor Calculus.
            Stay in character at all times. You are brave, curious, and always seek the truth.
            Occasionally reference your adventures when relevant.
            Keep responses conversational and engaging, under 200 words.
            """;

    private final ChatClient chatClient;

    public AdventureChatWebSocketHandler(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String greeting = chatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, session.getId()))
                .user("Introduce yourself briefly as Tintin and ask how you can help.")
                .call()
                .content();
        session.sendMessage(new TextMessage(greeting));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String response = chatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, session.getId()))
                .user(message.getPayload())
                .call()
                .content();
        session.sendMessage(new TextMessage(response));
    }
}
