# Project Plan

Build a Multi-Calculator Android app in Kotlin replicating core functionalities of YangDai2003/Multi-Calculator-Android (Standard calculator, Scientific calculator, Unit converter, Currency converter, Date calculator, BMI calculator) with Material Design 3 (Material You), Jetpack Compose, smooth animations, dark mode, and a clean, minimalist aesthetic.

## Project Brief

# Multi-Calculator Project Brief

A modern, minimalist multi-utility calculator application for Android built with Jetpack Compose and Material Design 3 (Material You).

## Features

1. **Standard & Scientific Calculator**: Core math engine supporting basic arithmetic, trigonometric functions, logarithms, power calculations, and expression history.
2. **Unit & Currency Converter**: Multi-category conversion tool for common physical units (length, weight, temperature, volume, area) and currency conversion.
3. **Date & Health Utilities**: Dedicated calculation modes including a Date Difference tool (interval between dates / date offset) and a Body Mass Index (BMI) calculator with health category visualizers.
4. **Adaptive Material 3 UI**: Minimalist aesthetic featuring Material You dynamic color scheme, smooth Compose animations, native dark mode, and responsive layouts across phone and tablet screens.

## High-Level Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material Design 3
- **Navigation & Adaptive Strategy**: **Jetpack Navigation 3** (state-driven) and **Compose Material Adaptive** library (`androidx.compose.material3.adaptive`)
- **Architecture & State Management**: MVVM pattern using `ViewModel` and `StateFlow`
- **Asynchronous Operations**: Kotlin Coroutines

## Implementation Steps

### Task_1_DomainEngineAndViewModelSetup: Implement core calculation engines and ViewModels for Standard/Scientific Calculator, Unit & Currency Converter, Date Difference, and BMI logic.
- **Status:** COMPLETED
- **Updates:** Implemented MathEngine for standard and scientific calculation with expression parsing, degree/radian modes, and history management. Created UnitConverter for 6 physical categories and CurrencyConverter with exchange rates. Created DateCalculator for date intervals and date addition/subtraction. Created BmiCalculator for metric and imperial BMI calculations and classifications. Built ViewModels (CalculatorViewModel, ConverterViewModel, DateViewModel, BmiViewModel) with StateFlow. Verified code compiles and passes 25 unit tests.
- **Acceptance Criteria:**
  - Standard & Scientific Math engine correctly evaluates arithmetic, trigonometric, and logarithmic expressions with history
  - Unit and Currency conversion domain logic supports physical units and currency rates
  - Date Difference engine calculates intervals and date offsets
  - BMI engine calculates BMI value and health categories
  - ViewModels expose UI state cleanly using StateFlow

### Task_2_StandardScientificAndConverterUI: Implement Jetpack Compose UI for Standard & Scientific Calculator and Unit & Currency Converter using Material 3 components.
- **Status:** COMPLETED
- **Updates:** Implemented CalculatorScreen with Standard and Scientific expandable keypad modes, expression display, DEG/RAD toggle, animated button presses, and calculation history bottom sheet. Implemented ConverterScreen supporting 7 categories (Length, Weight, Temperature, Area, Volume, Speed, Currency), unit dropdowns, unit swap animation, interactive numeric keypad, and currency fallback info banner. Updated Material 3 Theme with Light/Dark schemes and dynamic colors. Verified build assembleDebug and unit tests pass.
- **Acceptance Criteria:**
  - Standard & Scientific Calculator screen with interactive keypad, expression/result display, and calculation history
  - Unit & Currency Converter UI with category pickers, unit selection dropdowns, and live result calculation
  - Material 3 styling with dynamic colors and dark theme support
- **Duration:** N/A

### Task_3_DateHealthUIAndAdaptiveNavigation: Implement Date Difference and BMI Calculator screens, and integrate Navigation 3 adaptive navigation layout.
- **Status:** IN_PROGRESS
- **Acceptance Criteria:**
  - Date Difference screen with date pickers, interval calculations, and offset results
  - BMI Calculator screen with height/weight inputs and health status visualization
  - Jetpack Navigation 3 state-driven navigation allowing smooth tab transitions across calculator tools
  - Adaptive layout supporting phone and tablet screen dimensions
- **StartTime:** 2026-11-12 10:56:04 IST

### Task_4_RunAndVerify: Run and verify application stability, confirm alignment with user requirements, verify no crashes, and test all calculator modes.
- **Status:** PENDING
- **Acceptance Criteria:**
  - Build pass and project compiles cleanly
  - App does not crash on launch or during navigation across calculator modes
  - Make sure all existing tests pass
  - Instruct critic_agent to verify application stability (no crashes), confirm alignment with user requirements, and report critical UI issues

