<div align="center">

# 💧 HydroTrack

**A daily water intake tracker for Android.**
A personal goal based on your weight, one-tap logging, home screen widgets and reminders that stay quiet when you're on track.

[![CI](https://github.com/arunkmr13/HydroTracker/actions/workflows/ci.yml/badge.svg)](https://github.com/arunkmr13/HydroTracker/actions/workflows/ci.yml)
[![Release](https://img.shields.io/github/v/release/arunkmr13/HydroTracker?include_prereleases&sort=semver&label=release)](https://github.com/arunkmr13/HydroTracker/releases)
[![License: MIT](https://img.shields.io/badge/license-MIT-00838F.svg)](LICENSE)
![Android](https://img.shields.io/badge/Android-8.0%2B-3DDC84?logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-Jetpack%20Compose-7F52FF?logo=kotlin&logoColor=white)

[Report a bug](https://github.com/arunkmr13/HydroTracker/issues/new/choose) ·
[Request a feature](https://github.com/arunkmr13/HydroTracker/issues/new/choose) ·
[Changelog](CHANGELOG.md)

</div>

> [!NOTE]
> **v2 is in progress:** a local-only rebuild with no accounts and no analytics, named drinks, smart pace-based
> reminders, weekly insights and optional Health Connect sync. This README describes v1.

---

## Features

- [x] **Personal daily goal**, calculated from body weight (35 ml/kg), with manual override
- [x] **Progress ring** with one-tap bottle refill logging and today's intake log
- [x] **Monthly history calendar**, colour-coded by goal progress, with a 7-day average and day details
- [x] **Home screen widgets** in three sizes (2×2, 4×2, 4×3) with one-tap logging
- [x] **Reminders** via WorkManager that skip when you've logged recently, with a sleep window and
      notification actions (*+1 Refill*, *Snooze*)
- [x] **Symptom check-in** with 30-day CSV export
- [x] **Light and dark themes**
- [x] Optional sign-in with cloud backup (Firebase)

## Tech stack

| | |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Storage | Room (SQLite) |
| Background work | WorkManager |
| Widgets | Jetpack Glance |
| Cloud (optional) | Firebase Auth, Firestore, Analytics |
| Min / target SDK | 26 (Android 8.0) / 36 (Android 16) |

## Getting started

```bash
git clone https://github.com/arunkmr13/HydroTracker.git
cd HydroTracker
./gradlew assembleDebug        # APK in app/build/outputs/apk/debug/
./gradlew installDebug         # install on a connected device
```

Requirements: Android Studio (latest stable), JDK 17+, Android SDK 36.
See [DEVELOPMENT.md](DEVELOPMENT.md) for tests, database migrations and the release process.

## Project structure

```
app/src/main/java/com/arunkmr/hydrotrack/
├── data/          # Room entities, DAOs, repositories
├── domain/        # Target calculation and models
├── navigation/    # Bottom navigation graph
├── reminder/      # WorkManager scheduler, worker, notifications
├── sync/          # Firestore sync
├── auth/          # Sign-in
├── analytics/     # Event logging
├── ui/            # Screens: home, history, symptoms, settings, onboarding, account, theme
└── widget/        # Glance home screen widgets
```

## Contributing

Issues and pull requests are welcome. Please read [CONTRIBUTING.md](CONTRIBUTING.md) first, and report
security issues privately as described in [SECURITY.md](SECURITY.md).

## License

[MIT](LICENSE) © Arun Kumar
