# Tech Stack

## Backend

- **Spring Boot** (latest stable, 3.x) -- Java application framework
- **Spring AI** -- AI/LLM integration
- **Java 17+** -- minimum language version
- **Maven** -- build tool

## LLM Provider

- **OpenAI GPT-4o** -- primary chat model (supports vision, function calling, moderation)
- **OpenAI Moderation API** -- content moderation model
- **Spring AI embedding model** -- for vector embeddings (in-process or OpenAI)

## Frontend

- **Static HTML/CSS/JS** -- served from `src/main/resources/static/`
- **wc-chatbot** web component -- pre-built chat UI (port from Quarkus version)

## Database

- **In-memory vector store** -- via Spring AI's `SimpleVectorStore` (no external DB needed for demo)

## Other

- **WebSockets** (Spring WebSocket) -- real-time chat
- **Micrometer + Prometheus** -- metrics via Spring Boot Actuator
- **OpenTelemetry** -- distributed tracing
- **Apache Tika** -- PDF text extraction for RAG document loading
