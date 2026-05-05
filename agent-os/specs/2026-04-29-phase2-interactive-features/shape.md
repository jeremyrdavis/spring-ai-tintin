# Phase 2: Interactive Features -- Shaping Notes

## Scope

Port the Quarkus `chat/` subpackage (WebSocket chat, SSE streaming, chat memory) to Spring AI. Three new Java classes in `chat/` subpackage, one new HTML page, landing page updates.

## Decisions

- SSE streaming is stateless (no memory) -- demonstrates streaming as a standalone concept
- WebSocket has full chat memory -- demonstrates conversation continuity
- No `spring-boot-starter-webflux` needed -- Spring MVC supports `Flux` return types when Reactor is on classpath (transitive from Spring AI)
- Use Spring AI's auto-configured `ChatMemory` bean rather than defining our own
- WebSocket path `/adventure-chat` matches Quarkus for frontend compatibility

## Context

- **Visuals:** None -- port Quarkus chat.html styling as-is
- **References:** Quarkus `chat/` subpackage, Spring AI ChatMemory and streaming docs
- **Product alignment:** Matches Phase 2 of the migration roadmap

## Standards Applied

- naming-conventions -- Controller suffix, WebSocketHandler suffix
- package-structure -- `chat/` subpackage per convention
- code-style -- minimal, no logging, text blocks for prompts
- java-records -- not heavily used in Phase 2 (no new DTOs)
- configuration -- application.properties for any memory config
