# Phase 3: Safety & Observability (Spring AI Tintin Demo)

## Context

Phases 1-2 implemented core AI services and interactive features. Phase 3 adds safety (input/output guardrails, content moderation) and observability (Actuator metrics, Prometheus, OpenTelemetry tracing).

## Architecture Decisions

- **Input guardrails**: `SafeGuardAdvisor` (built-in) with off-topic keyword list
- **Output guardrails**: Custom `SafeContentAdvisor` implementing `CallAdvisor` with retry
- **Moderation**: `ModerationModel` called in controller before ChatClient
- **Observability**: Spring Boot Actuator + Micrometer Prometheus registry

## Tasks

1. Save spec documentation
2. Add `spring-boot-starter-actuator` + `micrometer-registry-prometheus` dependencies
3. `SafeContentAdvisor` + `GuardedController` in `guardrails/` subpackage
4. `ModeratedController` in `moderation/` subpackage
5. Update `application.properties` + `index.html`
6. Verify build and test

## File Inventory

```
guardrails/SafeContentAdvisor.java     (new)
guardrails/GuardedController.java      (new)
moderation/ModeratedController.java    (new)
application.properties                 (updated)
static/index.html                      (updated)
pom.xml                                (updated)
```
