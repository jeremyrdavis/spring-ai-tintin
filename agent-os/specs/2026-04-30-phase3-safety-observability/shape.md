# Phase 3: Safety & Observability -- Shaping Notes

## Scope

Port the Quarkus `guardrails/` and `moderation/` subpackages to Spring AI. Add Spring Boot Actuator observability. Three new Java classes across two subpackages.

## Decisions

- Input guardrails use Spring AI's built-in `SafeGuardAdvisor` (keyword matching) rather than a custom advisor
- Output guardrails require a custom `CallAdvisor` since Spring AI has no built-in output validation
- Content moderation calls `ModerationModel` explicitly in the controller for demo clarity
- Observability is primarily Actuator + Prometheus -- Spring AI auto-instruments chat/embedding/tool operations

## Context

- **Visuals:** None
- **References:** Quarkus `guardrails/` and `moderation/` subpackages, Spring AI SafeGuardAdvisor and ModerationModel docs
- **Product alignment:** Matches Phase 3 of the migration roadmap

## Standards Applied

- naming-conventions -- Controller suffix, Advisor suffix
- package-structure -- `guardrails/` and `moderation/` subpackages per convention
- code-style -- minimal, text blocks for prompts
- configuration -- application.properties for actuator and moderation
