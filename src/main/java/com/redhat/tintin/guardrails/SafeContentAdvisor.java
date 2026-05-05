package com.redhat.tintin.guardrails;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.core.Ordered;

public class SafeContentAdvisor implements CallAdvisor {

    private final int maxRetries;

    public SafeContentAdvisor(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public String getName() {
        return "SafeContentAdvisor";
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        ChatClientResponse response = null;
        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            response = chain.nextCall(request);
            String text = extractText(response);
            if (text != null && !text.isBlank() && text.length() <= 2000) {
                return response;
            }
        }
        return response;
    }

    private String extractText(ChatClientResponse response) {
        if (response.chatResponse() == null || response.chatResponse().getResult() == null) {
            return null;
        }
        return response.chatResponse().getResult().getOutput().getText();
    }
}
