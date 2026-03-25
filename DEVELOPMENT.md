# Development Notes

## What was built

Native Android app (Kotlin + Jetpack Compose) that calculates the half-angle of a truncated cone.
The user inputs **D1** (top diameter), **H** (height), and **D2** (bottom diameter) in millimeters.
The result is displayed live as they type — no button needed.

**Formula:**
```
angle = atan((D2/2 - D1/2) / H)  →  converted to degrees, rounded to 2 decimal places
```

---

## Architecture

```
MVVM + Clean Architecture
├── domain/usecase/     CalculateAngleUseCase   (pure logic, no Android deps)
├── domain/model/       ConeCalculation         (data class)
├── viewmodel/          CalculatorViewModel     (StateFlow, Hilt)
└── ui/screens/         SplashScreen, CalculatorScreen
```

Dependency injection with **Hilt**. Navigation with **NavHost** (splash is removed from backstack on navigate).

---

## UI / UX decisions

- **Colors**: Peumax navy `#0D2B6E` as primary, `#1565C0` as accent, `#0097A7` teal for the cone diagram.
- **Splash screen**: gradient background (navy → blue → navy), Peumax spray logo (vector XML approximation), auto-navigates after 2 seconds.
- **Calculator screen**: result card at top (large font for elderly users), cone diagram expands to fill center space, input fields at the bottom ordered D1 → H → D2 to match the cone visually.
- **Cone diagram**: Canvas-drawn trapezoid with live labels — shows D1/H/D2 when empty, switches to the typed value + "mm" as the user types.
- **Font sizes**: intentionally large (result 80sp, labels 22sp, inputs 24sp) for accessibility.

### Reference images used during design

Two images were shared during development:

1. **UI inspiration**: a modern app screenshot with a large prominent number display, card-based layout, and clean typography — used to define the overall layout direction.
2. **Peumax brand logo**: the official logo showing the teal sprinkler fan graphic, "PEUMAX" bold text, and "100% BRASILEIRA" subtitle — used to build the vector drawable (`ic_peumax_spray.xml`) and define the brand colors.

The logo asset was recreated as an Android Vector Drawable since no source file was available.

---

## Known minor issues (not yet fixed)

- `ConeCalculation` model exists but is unused — was scaffolded for future use (e.g. history feature).
- Navigation routes are plain strings (`"splash"`, `"calculator"`) — could be replaced with a sealed class to avoid typos if the app grows.

---

## Future ideas discussed

- Additional calculation modes: Angle + D1 → D2, Angle + D2 → D1
- Language toggle: Portuguese / English
- Calculation history
