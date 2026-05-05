# Tintin AI Demo — Spring Boot + Spring AI

A demo application showcasing Spring AI features through the lens of Herge's *The Adventures of Tintin*. Demonstrates ChatClient, RAG, structured output, tool calling, vision, streaming, guardrails, content moderation, and observability.

## Prerequisites

- Java 17+
- An [OpenAI API key](https://platform.openai.com/api-keys)

## Quick Start

```bash
export OPENAI_API_KEY=your-key-here
./mvnw spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080) to see the landing page with a "Try It Out" console and feature cards.

## Build

```bash
./mvnw clean compile    # compile only
./mvnw package          # build JAR
./mvnw test             # run tests
```

## Features

### Phase 1 — Core AI Services

| Feature | Endpoint | Description |
|---|---|---|
| **RAG** | `POST /api/tintin/ask` | Question answering over Tintin PDFs using `QuestionAnswerAdvisor` + `SimpleVectorStore` |
| **Structured Output** | `POST /api/tintin/character` | Character analysis returning typed Java records via `.entity()` |
| **Few-Shot Prompting** | `POST /api/tintin/classify` | Adventure classification using examples in the system prompt |
| **Vision** | `POST /api/tintin/cover/describe` | Book cover analysis using multimodal `Media` with image resources |
| **Tool Calling** | `POST /api/tintin/trivia` | Trivia expert using `@Tool`-annotated methods for facts and date calculations |

### Phase 2 — Interactive Features

| Feature | Endpoint | Description |
|---|---|---|
| **WebSocket Chat** | `ws://localhost:8080/adventure-chat` | Real-time chat with Tintin, with per-session memory via `MessageChatMemoryAdvisor` |
| **SSE Streaming** | `GET /api/tintin/chat/stream?message=...` | Token-by-token streaming via Server-Sent Events |

Chat UI available at [http://localhost:8080/chat.html](http://localhost:8080/chat.html).

### Phase 3 — Safety & Observability

| Feature | Endpoint | Description |
|---|---|---|
| **Input Guardrails** | `POST /api/tintin/guarded` | Off-topic keyword filtering via `SafeGuardAdvisor` |
| **Output Guardrails** | (same) | Response validation (empty/length) with retry via custom `SafeContentAdvisor` |
| **Content Moderation** | `POST /api/tintin/moderated` | Family-friendly responses with OpenAI `ModerationModel` pre-check |
| **Health** | `GET /actuator/health` | Spring Boot Actuator health endpoint |
| **Prometheus Metrics** | `GET /actuator/prometheus` | Prometheus-format metrics including Spring AI observations |

## Project Structure

```
src/main/java/com/redhat/tintin/
├── TintinAiDemoApplication.java       # Main class
├── QueryRequest.java                  # Shared request record
├── RagConfig.java                     # PDF ingestion + vector store setup
├── TintinExpertController.java        # RAG endpoint
├── CharacterController.java           # Structured output
├── AdventureClassifierController.java # Few-shot classification
├── CoverController.java              # Vision / multimodal
├── TintinTriviaController.java       # Tool calling
├── tools/
│   ├── TintinFactsTool.java           # @Tool: publication dates, character lookup
│   └── DateCalculatorTool.java        # @Tool: date calculations
├── chat/
│   ├── AdventureChatWebSocketHandler.java  # WebSocket handler with memory
│   ├── StreamingChatController.java        # SSE streaming
│   └── WebSocketConfig.java               # WebSocket registration
├── guardrails/
│   ├── GuardedController.java         # Input + output guardrails
│   └── SafeContentAdvisor.java        # Custom CallAdvisor for output validation
└── moderation/
    └── ModeratedController.java       # Content moderation with ModerationModel
```

## Tech Stack

- **Spring Boot 3.5.14** / **Spring AI 1.1.5**
- **OpenAI GPT-4o** (chat) / **omni-moderation-latest** (moderation)
- **SimpleVectorStore** — in-memory vector store (no external database required)
- **Micrometer + Prometheus** — metrics and observability

## Configuration

All config is in `src/main/resources/application.properties`. The only required environment variable is `OPENAI_API_KEY`.
