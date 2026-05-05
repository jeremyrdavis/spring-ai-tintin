package com.redhat.tintin.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatClient.Builder chatClientBuilder;
    private final ChatMemory chatMemory;

    public WebSocketConfig(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        this.chatClientBuilder = chatClientBuilder;
        this.chatMemory = chatMemory;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(adventureChatHandler(), "/adventure-chat")
                .setAllowedOrigins("*");
    }

    @Bean
    public AdventureChatWebSocketHandler adventureChatHandler() {
        return new AdventureChatWebSocketHandler(chatClientBuilder, chatMemory);
    }
}
