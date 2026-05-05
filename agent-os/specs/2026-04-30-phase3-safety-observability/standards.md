# Standards for Phase 3: Safety & Observability

The following standards apply, adapted for Spring conventions.

---

## Naming Conventions (adapted from global/naming-conventions)

Suffix classes by role:
- REST controllers: `*Controller` (`GuardedController`, `ModeratedController`)
- Advisors: `*Advisor` (`SafeContentAdvisor`)

---

## Java Records (from global/java-records)

No new DTOs in Phase 3. Reuses existing `QueryRequest` record.

---

## Code Style (from global/code-style)

Demo application -- keep code minimal and readable.
- No Lombok, no abstract base classes
- Use Java text blocks for prompts
- Let exceptions propagate unless specific HTTP status needed

---

## Configuration (adapted from global/configuration)

All config in `src/main/resources/application.properties`.
- Actuator endpoint exposure via `management.endpoints.web.exposure.include`
- Moderation model via `spring.ai.openai.moderation.options.model`

---

## Package Structure (from global/package-structure)

Root: `com.redhat.tintin`
- Phase 3 classes go in `guardrails/` and `moderation/` subpackages
- Max one level of nesting
