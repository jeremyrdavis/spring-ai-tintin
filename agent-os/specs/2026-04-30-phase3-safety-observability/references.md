# References for Phase 3: Safety & Observability

## Reference Implementation

### Quarkus guardrails/ subpackage

- **Location:** `quarkus-langchain4j-tintin/langchain4j-tintin/src/main/java/com/redhat/tintin/guardrails/`
- **Relevance:** Source of guardrail logic, keyword lists, and error handling patterns
- **Key patterns to port:**
  - `InputGuardrail` -> `SafeGuardAdvisor` (built-in)
  - `OutputGuardrail` with retry -> custom `CallAdvisor` with retry loop
  - `@InputGuardrails`/`@OutputGuardrails` annotations -> `.defaultAdvisors()` on ChatClient
  - `GuardrailException` catching -> SafeGuardAdvisor returns rejection message directly

### Quarkus moderation/ subpackage

- **Location:** `quarkus-langchain4j-tintin/langchain4j-tintin/src/main/java/com/redhat/tintin/moderation/`
- **Relevance:** Source of moderation behavior and prompts
- **Key patterns to port:**
  - `@Moderate` annotation -> explicit `ModerationModel.call()` in controller
  - `ModeratedTintinService` -> inline ChatClient in controller

## Spring AI Documentation

- [Advisors API](https://docs.spring.io/spring-ai/reference/api/advisors.html) -- SafeGuardAdvisor, custom CallAdvisor
- [OpenAI Moderation](https://docs.spring.io/spring-ai/reference/api/moderation/openai-moderation.html) -- ModerationModel, ModerationPrompt
