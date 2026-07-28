# HydroTrack 💧 — Commercial Medical Hydration Suite

A production-grade, native Android application for medically-informed daily hydration tracking, built with **Kotlin**, **Jetpack Compose**, **Material 3**, **Room SQLite**, and **Firebase (Auth, Firestore, Analytics)**.

---

## 🌟 Key Features & Capabilities

- [x] **Phase 1: Medically-Informed Daily Target Calculation**
  - Dynamic daily target calculation based on body weight (`35 ml/kg` formula), with custom manual target override support.
- [x] **Phase 2: Liquid Glassmorphism Design System**
  - Custom `GlassCard` and `GlassBackground` composables featuring 3D ambient refraction depth, 1.5dp glowing borders, dark/light theme switching, and San Francisco (SF Pro) typography.
- [x] **Phase 3: Real-Time Hydration Engine & Visual Progress**
  - Smooth 3/4 semi-circular `ProgressRing` with bottle refill counter (`+850 ml` default), quick log history, and encouraging health banners.
- [x] **Phase 4: Interactive Monthly Hydration Calendar**
  - Full monthly calendar navigation (`Mon`–`Sun` grid), color-coded daily goal tiles (0 ml, partial, 100%+ goal met), 7-day rolling average calculation, and day breakdown pop-ups with symptom notes.
- [x] **Phase 5: Multi-Provider Authentication & Account Profile**
  - Dual support for **Google One-Tap Sign-In** and **Firebase Email / Password Authentication**, user avatar badge, UID display, and session management.
- [x] **Phase 6: Bi-Directional Cloud Synchronization & Analytics**
  - Automatic cloud backup of Room intake logs and symptom records to **Firebase Firestore** (`users/{uid}/water_logs`), plus **Firebase Analytics** event telemetry (`water_refill_logged`, `symptom_checkin_logged`, `hydration_goal_achieved`).
- [x] **Phase 7: Responsive Glance Home Screen Widgets**
  - Multi-density Compose Glance widgets (Small 2x2, Medium 4x2, Large 4x3 with mini activity log) featuring one-tap `+1 Refill` logging directly from the Android Home Screen.
- [x] **Phase 8: High-Priority Heads-Up Notifications & Reminders**
  - Periodic hydration reminders using **WorkManager** with smart log suppression, sleep window enforcement (11 PM – 7 AM), sound/vibration, and interactive notification buttons (`+1 Refill` & `Snooze`).
- [x] **Phase 9: Symptom Check-In & Doctor CSV Export**
  - Symptom tracking for medical monitoring (Dizziness, Dry Skin, custom notes) and 1-tap 30-day CSV report export for clinical consultations.
- [x] **Phase 10: Data Management & Reset**
  - Instant wipe option for resetting local logs and history.

---

## 🛠️ Architecture & Tech Stack

| Component | Technology |
|:---|:---|
| **Language** | Kotlin 2.4.0 |
| **UI Framework** | Jetpack Compose + Material 3 |
| **Design System** | Liquid Glassmorphism + San Francisco (SF Pro) Typography |
| **Database** | Room 2.8.4 (SQLite) |
| **Cloud Backend** | Firebase Firestore (Cloud Database) |
| **Authentication** | Firebase Auth (Google Sign-In + Email/Password) |
| **Analytics** | Firebase Analytics |
| **Widget** | AndroidX Glance (Compose for Home Screen Widgets) |
| **Background Tasks** | WorkManager |
| **Min SDK** | 26 (Android 8.0 Oreo) |
| **Target SDK** | 36 (Android 16) |

---

## 🚀 Building & Running

### Prerequisites
- Android Studio (Quail or later)
- Android SDK 36
- Physical Android Device (e.g. Google Pixel 10) or Emulator (API 26+)

### Command Line Build
```bash
# Clone the repository
git clone https://github.com/arunkmr13/HydroTracker.git
cd HydroTracker

# Assemble Debug APK
./gradlew assembleDebug
```

Output APK location:
`app/build/outputs/apk/debug/app-debug.apk`

---

## 📁 Project Structure

```
app/src/main/java/com/arunkmr/hydrotrack/
├── HydroTrackApp.kt          # Application entry & Firebase/Notification init
├── MainActivity.kt            # Compose Activity entry point
├── analytics/
│   └── AnalyticsManager.kt    # Firebase Analytics event logger
├── auth/
│   └── GoogleAuthManager.kt   # Firebase Authentication & Google Sign-In manager
├── data/
│   ├── local/
│   │   ├── entity/            # Room entities (WaterLogEntity, SymptomLogEntity, UserSettingsEntity)
│   │   ├── AppDatabase.kt     # Room database version 2
│   │   ├── WaterLogDao.kt     # Water intake queries & daily aggregations
│   │   ├── SettingsDao.kt     # User preferences DAO
│   │   └── SymptomLogDao.kt   # Symptom tracking DAO
│   └── repository/
│       ├── WaterRepository.kt # Central data repository & widget sync
│       └── SettingsRepository.kt
├── domain/
│   ├── TargetCalculator.kt    # 35ml/kg daily target calculator
│   └── model/
│       ├── DailyProgress.kt
│       └── SymptomEntry.kt
├── navigation/
│   └── NavGraph.kt            # Bottom bar navigation (Home, History, Symptoms, Account, Settings)
├── reminder/
│   ├── NotificationHelper.kt  # Heads-up screen notifications with action buttons
│   ├── ReminderScheduler.kt   # WorkManager periodic scheduler
│   └── ReminderWorker.kt      # Smart reminder logic & sleep window filter
├── sync/
│   └── FirebaseSyncManager.kt # Firebase Firestore cloud sync manager
├── ui/
│   ├── account/               # User account profile & auth screens
│   ├── export/                # Doctor visit CSV export utility
│   ├── history/               # Interactive Monthly Hydration Calendar & 7-day average
│   ├── home/                  # Semi-circular progress ring & intake log
│   ├── onboarding/            # Weight & bottle size setup flow
│   ├── settings/              # App theme, reminders, data management
│   ├── symptoms/              # Symptom check-in screen
│   └── theme/                 # GlassCard, GlassBackground, Color, Type
└── widget/
    ├── HydroWidget.kt         # Responsive Glance home screen widget
    └── HydroWidgetReceiver.kt # Widget receiver
```

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for details.
