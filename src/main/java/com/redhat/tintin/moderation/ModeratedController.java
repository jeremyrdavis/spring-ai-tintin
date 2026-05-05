package com.redhat.tintin.moderation;

import com.redhat.tintin.QueryRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.moderation.ModerationModel;
import org.springframework.ai.moderation.ModerationPrompt;
import org.springframework.ai.moderation.ModerationResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tintin/moderated")
public class ModeratedController {

    private static final String SYSTEM_PROMPT = """
            You are a family-friendly Tintin expert for young readers.
            Answer questions about Tintin's adventures in a way that is
            appropriate for children. Keep responses fun, educational, and positive.
            """;

    private final ChatClient chatClient;
    private final ModerationModel moderationModel;

    public ModeratedController(ChatClient.Builder builder, ModerationModel moderationModel) {
        this.chatClient = builder.defaultSystem(SYSTEM_PROMPT).build();
        this.moderationModel = moderationModel;
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String askForKids(@RequestBody QueryRequest request) {
        boolean flagged = moderationModel.call(new ModerationPrompt(request.question()))
                .getResult()
                .getOutput()
                .getResults()
                .stream()
                .anyMatch(ModerationResult::isFlagged);

        if (flagged) {
            return "Great snakes! That content was flagged by our moderation system. " +
                   "Please ask a family-friendly question about Tintin!";
        }

        return chatClient.prompt()
                .user(request.question())
                .call()
                .content();
    }
}
