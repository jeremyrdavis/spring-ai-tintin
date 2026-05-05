# References for Phase 2: Interactive Features

## Reference Implementation

### Quarkus chat/ subpackage

- **Location:** `quarkus-langchain4j-tintin/langchain4j-tintin/src/main/java/com/redhat/tintin/chat/`
- **Relevance:** Source of all chat behavior, prompts, and WebSocket/SSE patterns
- **Key patterns to port:**
  - `@RegisterAiService @SessionScoped` interface -> `ChatClient` with `MessageChatMemoryAdvisor`
  - `@WebSocket` + `@OnOpen`/`@OnTextMessage` -> `TextWebSocketHandler`
  - `Multi<String>` SSE -> `Flux<String>` SSE
  - `@SessionScoped` memory -> `ChatMemory.CONVERSATION_ID` param with session ID

### Quarkus chat.html

- **Location:** `quarkus-langchain4j-tintin/langchain4j-tintin/src/main/resources/META-INF/resources/chat.html`
- **Relevance:** Chat UI to port (pure client-side WebSocket code, framework-agnostic)

## Spring AI Documentation

- [Chat Memory](https://docs.spring.io/spring-ai/reference/api/chat-memory.html) -- `MessageWindowChatMemory`, `ChatMemoryRepository`
- [Advisors API](https://docs.spring.io/spring-ai/reference/api/advisors.html) -- `MessageChatMemoryAdvisor`
- [ChatClient API](https://docs.spring.io/spring-ai/reference/api/chatclient.html) -- `.stream().content()` for `Flux<String>`
