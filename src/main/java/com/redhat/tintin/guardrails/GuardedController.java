package com.redhat.tintin.guardrails;

import com.redhat.tintin.QueryRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tintin/guarded")
public class GuardedController {

    private static final String SYSTEM_PROMPT = """
            You are a Tintin expert. Only answer questions about Tintin,
            his adventures, characters, and the world created by Herge.
            Keep answers concise and informative.
            """;

    private static final List<String> OFF_TOPIC_KEYWORDS = List.of(
            "stock price", "recipe", "weather forecast",
            "cryptocurrency", "medical advice", "bitcoin",
            "cooking", "investment"
    );

    private final ChatClient chatClient;

    public GuardedController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        SafeGuardAdvisor.builder()
                                .sensitiveWords(OFF_TOPIC_KEYWORDS)
                                .failureResponse(
                                        "Billions of blue blistering barnacles! " +
                                        "I can only discuss Tintin and his adventures. " +
                                        "Please ask something related to the world of Tintin.")
                                .build(),
                        new SafeContentAdvisor(3)
                )
                .build();
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String ask(@RequestBody QueryRequest request) {
        return chatClient.prompt()
                .user(request.question())
                .call()
                .content();
    }
}
