# Phase 1: MVP Core AI Services -- Shaping Notes

## Scope

Port 5 AI features from the Quarkus Langchain4j Tintin demo to Spring Boot + Spring AI:
1. CharacterAnalyzer -- Structured output (Java records)
2. TintinExpert -- RAG over Tintin PDFs with source citations
3. TintinTrivia -- Function calling with @Tool
4. CoverAnalyzer -- Image/vision with multimodal input
5. AdventureClassifier -- Few-shot prompting + classification

## Decisions

- **No AI service interfaces** -- use ChatClient fluent API directly in @RestController classes (idiomatic Spring AI)
- **ChatClient.Builder injected via constructor** -- each controller builds its own ChatClient with per-feature defaults
- **Tools instantiated per-request** -- stateless, so `new TintinFactsTool()` in `.tools()` is fine
- **SimpleVectorStore for RAG** -- in-memory, zero-infrastructure, suitable for demo
- **Source citations via prompt instruction** -- not a custom advisor; simpler for demo
- **Controller suffix** -- Spring convention replaces Quarkus's Resource suffix

## Context

- **Visuals:** None -- Quarkus version's UI is the reference
- **References:** `quarkus-langchain4j-tintin/langchain4j-tintin/` (all Java source files, prompts, and resources)
- **Product alignment:** Matches Phase 1 of the roadmap in `agent-os/product/roadmap.md`

## Standards Applied

- global/naming-conventions -- adapted for Spring (Controller suffix, same Tool/record patterns)
- global/java-records -- same record patterns for DTOs and structured output
- global/code-style -- minimal demo style, no Lombok, text blocks for prompts
- global/configuration -- application.properties with spring.ai.* properties
- global/package-structure -- same flat root + feature subpackages
