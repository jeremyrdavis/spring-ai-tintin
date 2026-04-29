# Standards for Phase 1: MVP Core AI Services

The following standards apply, adapted for Spring conventions.

---

## Naming Conventions (adapted from global/naming-conventions)

Suffix classes by role:
- REST controllers: `*Controller` (`CharacterController`, `CoverController`)
- Tools: action + `Tool` (`DateCalculatorTool`, `TintinFactsTool`)
- Configuration: `*Config` (`RagConfig`)
- Records/DTOs: plain nouns (`CharacterInfo`, `QueryRequest`, `ClassifiedAdventure`)

---

## Java Records (from global/java-records)

Use Java records for all request/response data carriers.
- Single-use request types: inline as nested record inside the controller
- Shared request types: extract to root package
- AI structured output: top-level records with descriptive field names

---

## Code Style (from global/code-style)

Demo application -- keep code minimal and readable.
- No Lombok
- No abstract base classes
- No app-level logging -- rely on Spring AI auto-logging
- Use Java text blocks for prompts
- Let exceptions propagate unless specific HTTP status needed

---

## Configuration (adapted from global/configuration)

All config in `src/main/resources/application.properties`.
- Use `spring.ai.openai.*` properties
- Secrets via env var interpolation: `${OPENAI_API_KEY}`

---

## Package Structure (from global/package-structure)

Root: `com.redhat.tintin`
- Controllers and records in root package
- Feature subpackages: `tools/`
- Max one level of nesting
