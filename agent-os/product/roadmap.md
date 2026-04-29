# Product Roadmap

## Phase 1: MVP -- Core AI Services

- Project scaffolding (Spring Boot, Spring AI dependencies, configuration)
- CharacterAnalyzer: structured output with Java records via Spring AI's `BeanOutputConverter`
- TintinExpert: RAG with Spring AI's vector store and document readers over the Tintin PDF + source citations
- TintinTrivia: function calling with Spring AI's `@Tool` / function callbacks
- CoverAnalyzer: image/vision processing with multimodal message support
- AdventureClassifier: few-shot prompting with example messages

## Phase 2: Interactive Features

- AdventureChatBot: WebSocket-based real-time chat as Tintin
- Streaming responses via SSE (Spring AI's `Flux<String>` streaming)
- Chat memory with Spring AI's `ChatMemory` and session-scoped advisors

## Phase 3: Safety & Observability

- Input guardrails (topic filtering via Spring AI advisors)
- Output guardrails (content validation via Spring AI advisors)
- Content moderation with OpenAI moderation API
- Micrometer metrics + Spring Boot Actuator
- OpenTelemetry / Micrometer tracing

## Phase 4: Post-Launch

- MCP server integration demo
- Additional document types in RAG (CSV guide)
- Fault tolerance patterns
- Semantic compression of chat history
- Ollama as an alternative local provider
