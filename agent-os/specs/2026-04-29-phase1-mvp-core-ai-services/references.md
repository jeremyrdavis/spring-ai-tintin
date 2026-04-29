# References for Phase 1: MVP Core AI Services

## Reference Implementation

### Quarkus Langchain4j Tintin Demo

- **Location:** `quarkus-langchain4j-tintin/langchain4j-tintin/`
- **Relevance:** Source of all feature behavior, prompts, tool logic, and domain data
- **Key patterns to port:**
  - AI Service interfaces -> ChatClient calls in controllers
  - `@RegisterAiService` -> `ChatClient.Builder` injection
  - `@SystemMessage` / `@UserMessage` -> `.system()` / `.user()` fluent API
  - `@ToolBox` -> `.tools()`
  - `@ResponseAugmenter` -> system prompt instruction
  - EasyRAG auto-config -> `RagConfig` + `CommandLineRunner`
  - JAX-RS `@Path` -> Spring `@RequestMapping`
  - `dev.langchain4j.data.image.Image` -> Spring AI `Media` + `Resource`
