package com.redhat.tintin;

import com.redhat.tintin.tools.DateCalculatorTool;
import com.redhat.tintin.tools.TintinFactsTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tintin/trivia")
public class TintinTriviaController {

    private static final String SYSTEM_PROMPT = """
            You are a Tintin trivia expert. Use available tools to look up facts
            and calculate dates. Always verify your answers using the tools.
            Respond in the style of Professor Calculus - slightly absent-minded but brilliant.
            When asked about publication dates, first look up the year, then calculate how long ago it was.
            """;

    private final ChatClient chatClient;

    public TintinTriviaController(ChatClient.Builder builder) {
        this.chatClient = builder.defaultSystem(SYSTEM_PROMPT).build();
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String askTrivia(@RequestBody QueryRequest request) {
        return chatClient.prompt()
                .user(request.question())
                .tools(new TintinFactsTool(), new DateCalculatorTool())
                .call()
                .content();
    }
}
