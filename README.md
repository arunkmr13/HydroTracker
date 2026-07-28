# HydroTrack 💧

A native Android app for tracking daily water intake, built with Kotlin + Jetpack Compose + Material 3.

## Overview

HydroTrack helps you stay hydrated by tracking water intake against a medically-informed daily target (35ml/kg body weight). Designed for users who log intake by bottle refills rather than arbitrary glasses.

## Features (MVP Roadmap)

- [x] **Step 1**: Room database + entities + DAOs + repositories
- [ ] **Step 2**: Onboarding screen (weight input, bottle size, manual override)
- [ ] **Step 3**: Home screen (progress ring, +1 refill button)
- [ ] **Step 4**: Glance widget (one-tap log from home screen)
- [ ] **Step 5**: WorkManager reminders (smart suppression, sleep window)
- [ ] **Step 6**: Notification action buttons (log from notification, snooze)
- [ ] **Step 7**: History screen (heatmap calendar, rolling 7-day average)
- [ ] **Step 8**: Symptom check-in (daily dizzy/dry skin tracking)
- [ ] **Step 9**: Export (CSV/PDF for doctor visits)

## Tech Stack

| Component | Technology |
|:---|:---|
| Language | Kotlin |
| UI | Jetpack Compose (Material 3) |
| Widget | Glance (Compose for widgets) |
| Database | Room 2.8.4 |
| Background | WorkManager |
| Architecture | MVVM (ViewModel + Repository + Room) |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 36 (Android 16) |

## Build

Open in Android Studio (Quail 1 or later) and sync Gradle. Build and run on a device with API 26+.

```bash
# If using command line
./gradlew assembleDebug
```

## Project Structure

```
app/src/main/java/com/arunkmr/hydrotrack/
├── HydroTrackApp.kt          # Application class
├── MainActivity.kt            # Compose entry point
├── data/
│   ├── local/
│   │   ├── entity/            # Room entities
│   │   ├── AppDatabase.kt     # Room database
│   │   ├── WaterLogDao.kt     # Water intake DAO
│   │   ├── SettingsDao.kt     # User settings DAO
│   │   └── SymptomLogDao.kt   # Symptom tracking DAO
│   └── repository/
│       ├── WaterRepository.kt
│       └── SettingsRepository.kt
├── domain/
│   ├── TargetCalculator.kt    # 35ml/kg target logic
│   └── model/
│       ├── DailyProgress.kt
│       └── SymptomEntry.kt
└── ui/theme/
    ├── Color.kt               # Calm blue/teal palette
    ├── Theme.kt               # Material 3 dynamic color
    └── Type.kt                # Inter font typography
```
