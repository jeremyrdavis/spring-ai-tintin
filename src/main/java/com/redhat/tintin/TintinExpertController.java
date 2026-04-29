package com.redhat.tintin;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tintin/ask")
public class TintinExpertController {

    private static final String SYSTEM_PROMPT = """
            You are a world-renowned expert on "The Adventures of Tintin" by Herge.
            Answer questions using the provided context from Tintin source documents.
            If you don't know the answer from the context, say \
            "Blistering barnacles! I don't have that information in my sources."
            Always stay in character as a knowledgeable Tintin scholar.
            When answering, cite the specific passages from the provided context that support your answer.
            """;

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public TintinExpertController(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder.defaultSystem(SYSTEM_PROMPT).build();
        this.vectorStore = vectorStore;
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String ask(@RequestBody QueryRequest request) {
        return chatClient.prompt()
                .advisors(QuestionAnswerAdvisor.builder(vectorStore)
                        .searchRequest(SearchRequest.builder().topK(5).build())
                        .build())
                .user(request.question())
                .call()
                .content();
    }
}
