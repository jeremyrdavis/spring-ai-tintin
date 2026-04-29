# Phase 1: MVP -- Core AI Services (Spring AI Tintin Demo)

## Context

This is a functional port of the Quarkus Langchain4j Tintin demo to Spring Boot + Spring AI. The goal is to demonstrate the same AI features (structured output, RAG, function calling, vision, few-shot prompting) using idiomatic Spring AI patterns. The new project lives at `spring-ai-tintin/`.

The Quarkus version at `quarkus-langchain4j-tintin/langchain4j-tintin/` is the reference implementation for all feature behavior and prompts.

## Architecture Decision

**Idiomatic Spring AI** -- no declarative AI service interfaces. Use `ChatClient` fluent API in `@RestController` classes, injecting `ChatClient.Builder` via constructor.

## Tasks

### Task 1: Save Spec Documentation

Create `spring-ai-tintin/agent-os/specs/2026-04-29-phase1-mvp-core-ai-services/` with:
- `plan.md` -- this plan
- `shape.md` -- shaping notes (scope, decisions, context)
- `standards.md` -- applicable standards (naming adapted for Spring: Controller suffix, etc.)
- `references.md` -- pointer to the Quarkus reference implementation

### Task 2: Scaffold Spring Boot Project

Create the project skeleton:

**Files:**
- `pom.xml` -- Spring Boot 3.x parent, Spring AI BOM, dependencies:
  - `spring-boot-starter-web`
  - `spring-ai-starter-model-openai`
  - `spring-ai-pdf-document-reader`
  - `spring-ai-advisors-vector-store`
  - `spring-boot-starter-test` (test scope)
- `src/main/java/com/redhat/tintin/TintinAiDemoApplication.java` -- `@SpringBootApplication`
- `src/main/resources/application.properties`:
  ```properties
  spring.ai.openai.api-key=${OPENAI_API_KEY}
  spring.ai.openai.chat.options.model=gpt-4o
  spring.ai.openai.chat.options.temperature=0.7
  ```
- `.gitignore`
- Copy resources from Quarkus project:
  - `tintin-docs/*.pdf` -> `src/main/resources/tintin-docs/`
  - `tintin-image.png` -> `src/main/resources/static/images/`

**Validation:** `./mvnw clean compile` succeeds.

### Task 3: Shared Types + Landing Page

- `QueryRequest.java` -- shared record (same as Quarkus version)
- `src/main/resources/static/index.html` -- port from Quarkus, change branding to "Spring AI", remove Phase 2/3 features, adjust dev UI links

### Task 4: CharacterAnalyzer (Structured Output)

**Spring AI pattern:** `.entity(CharacterInfo.class)` for automatic JSON schema injection and deserialization.

**Files:**
- `CharacterInfo.java` -- record (identical to Quarkus)
- `CharacterController.java` -- `@RestController`, `@PostMapping`, inline `CharacterRequest` record
  ```java
  chatClient.prompt()
      .system(SYSTEM_PROMPT)
      .user("Analyze the Tintin character named \"%s\"...".formatted(request.name()))
      .call()
      .entity(CharacterInfo.class);
  ```

**Validation:** `curl -X POST localhost:8080/api/tintin/character -H "Content-Type: application/json" -d '{"name":"Captain Haddock"}' | jq`

### Task 5: TintinExpert (RAG with PDF)

**Spring AI pattern:** `PagePdfDocumentReader` + `TokenTextSplitter` + `SimpleVectorStore` + `QuestionAnswerAdvisor`.

**Files:**
- `RagConfig.java` -- `@Configuration` with:
  - `SimpleVectorStore` bean
  - `CommandLineRunner` to ingest PDFs at startup
- `TintinExpertController.java` -- `@RestController` with `QuestionAnswerAdvisor` wired into ChatClient
  ```java
  chatClient.prompt()
      .advisors(QuestionAnswerAdvisor.builder(vectorStore)
          .searchRequest(SearchRequest.builder().topK(5).build())
          .build())
      .user(request.question())
      .call()
      .content();
  ```

Source citations via system prompt instruction ("cite the specific passages") instead of a custom augmenter.

**Validation:** `curl -X POST localhost:8080/api/tintin/ask -H "Content-Type: application/json" -d '{"question":"What happens in Tintin in the Land of the Soviets?"}'`

### Task 6: TintinTrivia (Function Calling / Tools)

**Spring AI pattern:** `@Tool` annotation on plain classes, passed via `.tools()`.

**Files:**
- `tools/TintinFactsTool.java` -- same logic as Quarkus, change `@Tool` import to `org.springframework.ai.tool.annotation.Tool`
- `tools/DateCalculatorTool.java` -- same change
- `TintinTriviaController.java` -- `@RestController`
  ```java
  chatClient.prompt()
      .system(SYSTEM_PROMPT)
      .user(request.question())
      .tools(new TintinFactsTool(), new DateCalculatorTool())
      .call()
      .content();
  ```

**Validation:** `curl -X POST localhost:8080/api/tintin/trivia -H "Content-Type: application/json" -d '{"question":"How many years ago was Tintin in Tibet published?"}'`

### Task 7: CoverAnalyzer (Vision / Multimodal)

**Spring AI pattern:** `Media` class with Spring `Resource` objects -- much simpler than Quarkus's manual base64 encoding.

**Files:**
- `CoverController.java` -- `@RestController` with two endpoints
  ```java
  chatClient.prompt()
      .system(SYSTEM_PROMPT)
      .user(u -> u.text("Describe this cover...")
          .media(MimeTypeUtils.IMAGE_PNG, imageResource))
      .call()
      .content();
  ```
  Uses `UrlResource` for URL input, `ClassPathResource` for bundled image.

**Validation:** `curl -X POST localhost:8080/api/tintin/cover/describe -H "Content-Type: application/json" -d '{}'`

### Task 8: AdventureClassifier (Few-Shot Prompting)

**Spring AI pattern:** Few-shot examples in system message text block + `.entity()` for structured output.

**Files:**
- `AdventureCategory.java` -- enum (identical to Quarkus)
- `ClassifiedAdventure.java` -- record (identical to Quarkus)
- `AdventureClassifierController.java` -- `@RestController`
  ```java
  chatClient.prompt()
      .system(FEW_SHOT_SYSTEM_PROMPT)  // contains 3 classification examples
      .user("Description: \"%s\"".formatted(request.description()))
      .call()
      .entity(ClassifiedAdventure.class);
  ```

**Validation:** `curl -X POST localhost:8080/api/tintin/classify -H "Content-Type: application/json" -d '{"description":"Tintin infiltrates Soviet Russia..."}' | jq`

## File Inventory (14 Java files, 22 total)

```
spring-ai-tintin/
  pom.xml
  .gitignore
  src/main/java/com/redhat/tintin/
    TintinAiDemoApplication.java
    QueryRequest.java
    CharacterInfo.java
    CharacterController.java
    RagConfig.java
    TintinExpertController.java
    TintinTriviaController.java
    CoverController.java
    AdventureCategory.java
    ClassifiedAdventure.java
    AdventureClassifierController.java
    tools/
      TintinFactsTool.java
      DateCalculatorTool.java
  src/main/resources/
    application.properties
    tintin-docs/*.pdf (3 files, copied)
    static/
      index.html
      images/tintin-image.png (copied)
  src/test/java/com/redhat/tintin/
    TintinAiDemoApplicationTests.java
```

## Verification

After all tasks complete:
1. `./mvnw clean compile` -- project builds
2. `export OPENAI_API_KEY=... && ./mvnw spring-boot:run` -- app starts
3. Open `http://localhost:8080` -- landing page loads
4. Test each endpoint via the "Try It Out" panel or curl commands above
5. Verify structured output returns valid JSON records
6. Verify RAG answers reference PDF content
7. Verify tool chaining works (publication year + years-ago calculation)
8. Verify cover analysis describes the Tintin image
9. Verify classification returns valid AdventureCategory enum values
