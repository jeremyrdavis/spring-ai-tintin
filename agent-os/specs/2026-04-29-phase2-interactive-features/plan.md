# Phase 2: Interactive Features (Spring AI Tintin Demo)

## Context

Phase 1 implemented core AI services (structured output, RAG, tools, vision, few-shot). Phase 2 adds interactive features -- WebSocket chat, SSE streaming, and chat memory -- porting the Quarkus `chat/` subpackage to idiomatic Spring AI.

## Architecture Decisions

- **ChatClient pattern:** Each controller/handler creates its own `ChatClient` from injected `ChatClient.Builder`
- **Chat memory:** `MessageWindowChatMemory` + `MessageChatMemoryAdvisor` as default advisor
- **SSE streaming:** `chatClient.prompt().stream().content()` returning `Flux<String>` (Spring MVC + Reactor)
- **WebSocket:** `TextWebSocketHandler` with `spring-boot-starter-websocket`
- **Memory scope:** WebSocket uses per-connection memory (session ID); SSE is stateless

## Tasks

1. Save spec documentation
2. Add `spring-boot-starter-websocket` dependency
3. `StreamingChatController` -- SSE endpoint at `/api/tintin/chat/stream`
4. `AdventureChatWebSocketHandler` + `WebSocketConfig` -- WebSocket at `/adventure-chat` with chat memory
5. `chat.html` -- chat UI ported from Quarkus
6. Update `index.html` landing page with Phase 2 feature cards
7. Verify build and test all features

## File Inventory

```
chat/StreamingChatController.java          (new)
chat/AdventureChatWebSocketHandler.java    (new)
chat/WebSocketConfig.java                  (new)
static/chat.html                           (new)
static/index.html                          (updated)
pom.xml                                    (updated)
```
