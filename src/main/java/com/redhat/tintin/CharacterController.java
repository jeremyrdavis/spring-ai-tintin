package com.redhat.tintin;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tintin/character")
public class CharacterController {

    private static final String SYSTEM_PROMPT =
            "You are an expert on Tintin characters created by Herge. Provide detailed character analysis.";

    private final ChatClient chatClient;

    public CharacterController(ChatClient.Builder builder) {
        this.chatClient = builder.defaultSystem(SYSTEM_PROMPT).build();
    }

    record CharacterRequest(String name) {}

    @PostMapping
    public CharacterInfo analyze(@RequestBody CharacterRequest request) {
        return chatClient.prompt()
                .user("""
                        Analyze the Tintin character named "%s".
                        Provide their full name, a detailed description, their first appearance,
                        notable personality traits, and a list of adventures they appear in.
                        """.formatted(request.name()))
                .call()
                .entity(CharacterInfo.class);
    }
}
