# 📆 Event Planner App MidTerm)

This Android mobile app is developed in **Kotlin** using **Jetpack Compose** as a midterm project.  
It allows users to register an event and view a summary of the registered event details.

---

## Project Overview

**Objective:** Build a two-screen Event Planner app with Jetpack Compose, ViewModel state management, and a custom Material 3 theme.

Key goals:
- Single-Activity architecture
- Navigation with Navigation Compose
- ViewModel + `mutableStateOf` for all input states
- Custom Material Theme (light & dark)
- Input validation implemented using a sealed class

---

## Screens

### 1) Event Registration Screen
- Title: **“Event Registration”**
- Logo image below the title
- `OutlinedTextField`s:
  - Event Name
  - Organizer Name
  - Event Location
- **Radio Buttons** (single selection) for Event Type:
  - Conference, Wedding, Concert (example)
- **Checkboxes** (multi-selection) for Additional Services:
  - Catering, Photography, Music, Decoration
- **Register Button**:
  - Validates inputs
  - Shows `Toast` — “Event Registration Successful”
  - Navigates to Order Summary screen using a shared `ViewModel`

### 2) Order Summary Screen
- Title: **“Event Order Summary”**
- Displays:
  - Event Name
  - Organizer Name
  - Event Location
  - Selected Event Type
  - Selected Additional Services
- Back navigation to registration screen

---

## Architecture & Best Practices

- **Single Activity** (one `MainActivity.kt`) that hosts composables
- **Navigation Compose** for screen transitions (`NavGraph.kt`)
- **MVVM**-style separation: UI composables are stateless; all UI state lives in `EventViewModel`
- Use `mutableStateOf` for fields in ViewModel
- All **strings** are in `res/values/strings.xml` — no hardcoded UI text
- **Sealed class** used for input validation results (`ValidationResult` with states like `Success`, `EmptyField`, `InvalidEmail`, etc.)
- Meaningful comments in code and separation of concerns

---

## State Management

All form states are inside `EventViewModel`:
- `eventName: MutableState<String>`
- `organizerName: MutableState<String>`
- `eventLocation: MutableState<String>`
- `selectedEventType: MutableState<EventType?>`
- `selectedServices: MutableStateSet<ServiceType>` (or boolean flags)
- Validation functions produce `ValidationResult` sealed class responses

---

## Material Theme

- Custom **Material 3** theme created (colors, typography, shapes).
- Light and dark theme support via `Theme.kt`.
- Colors defined in `Color.kt` and applied across UI components.

---

## How to Run

1. Open the project in **Android Studio**.
2. Let Gradle sync and install any required SDK components.
3. Run on an emulator or device (API 21+ recommended).

---

## Validation

- Validation implemented via a `sealed class ValidationResult` with cases:
  - `Success`
  - `EmptyField(fieldName: String)`
  - `InvalidFormat(fieldName: String, reason: String)`
- Register button will only navigate on `Success`. Otherwise, show appropriate error indicators (e.g., helper text under `OutlinedTextField`) and avoid navigation.

---

## File structure (important files)
app/
├── java/com/example/midtermstudentid/
│ ├── MainActivity.kt
│ ├── navigation/NavGraph.kt
│ ├── viewmodel/EventViewModel.kt
│ ├── ui/screens/EventRegistrationScreen.kt
│ ├── ui/screens/OrderSummaryScreen.kt
│ └── ui/theme/Theme.kt, Color.kt, Type.kt
├── res/
│ ├── drawable/logo.png
│ └── values/strings.xml, colors.xml, themes.xml
