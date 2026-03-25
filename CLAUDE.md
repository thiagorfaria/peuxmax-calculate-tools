# CLAUDE.md — peuxmax-calculate-tools

This file defines the agreements and coding standards for this project.
Claude must follow these rules in every conversation and every code change.

---

## Development Philosophy

- **Baby steps**: Never implement everything at once. Break work into small, focused increments.
- **No big bang**: Each session should deliver one working, testable piece.
- **Quality over speed**: Prefer clean, readable, maintainable code over shortcuts.

---

## Architecture

This project follows **MVVM + Clean Architecture**.

### Layer structure

```
app/
├── ui/                  # Jetpack Compose screens and components (UI only, no logic)
│   ├── screens/         # One file per screen
│   └── components/      # Reusable UI components
├── viewmodel/           # ViewModels — manage UI state, no business logic
├── domain/
│   ├── usecase/         # Business logic — one class per use case
│   └── model/           # Domain models (pure Kotlin, no Android dependencies)
├── data/
│   ├── repository/      # Repository implementations
│   └── source/          # Local (Room) and remote (Retrofit) data sources
└── di/                  # Hilt dependency injection modules
```

### Rules
- UI layer must NEVER contain business logic — only display state and forward events to ViewModel.
- ViewModels must NEVER directly access data sources — always go through UseCases.
- UseCases must NEVER know about Android framework classes.
- Repositories are the only entry point to data (database, API, etc.).

---

## Tech Stack

| Concern | Library |
|---|---|
| UI | Jetpack Compose |
| State management | ViewModel + StateFlow |
| Async | Kotlin Coroutines + Flow |
| Dependency injection | Hilt |
| Local database | Room |
| HTTP client | Retrofit |
| Testing (unit) | JUnit5 + MockK |
| Testing (UI) | Compose UI Testing |

---

## Testing

- Every UseCase must have unit tests.
- Every ViewModel must have unit tests covering all state transitions.
- UI tests for critical user flows.
- No mocking of repositories in ViewModel tests — use fake implementations instead.
- Tests live alongside source code under `src/test/` and `src/androidTest/`.

---

## Code Quality

- Kotlin only — no Java.
- Follow official [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html).
- No logic in Composable functions — they only render state received from ViewModel.
- Each Composable should be small and focused — extract reusable components early.
- Use `sealed class` for UI state and events.
- Prefer `data class` for models.

---

## About the Developer

- Backend developer — strong in backend patterns, less familiar with frontend/UI.
- Needs clear separation between UI structure, UI styling, and business logic explained when relevant.
- Prefers incremental, well-explained changes over large refactors.
