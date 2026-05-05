# Standards for Phase 2: Interactive Features

The following standards apply, adapted for Spring conventions.

---

## Naming Conventions (adapted from global/naming-conventions)

Suffix classes by role:
- REST controllers: `*Controller` (`StreamingChatController`)
- WebSocket handlers: `*WebSocketHandler` (`AdventureChatWebSocketHandler`)
- Configuration: `*Config` (`WebSocketConfig`)

---

## Java Records (from global/java-records)

No new DTOs in Phase 2. WebSocket messages are plain strings. SSE uses query params.

---

## Code Style (from global/code-style)

Demo application -- keep code minimal and readable.
- No Lombok, no abstract base classes
- Use Java text blocks for prompts
- Let exceptions propagate

---

## Configuration (adapted from global/configuration)

All config in `src/main/resources/application.properties`.
- Spring AI auto-configures `ChatMemory` with `InMemoryChatMemoryRepository`
- Default 20-message window matches Quarkus config

---

## Package Structure (from global/package-structure)

Root: `com.redhat.tintin`
- Phase 2 classes go in `chat/` subpackage
- Max one level of nesting
