# Development

## Requirements

- Android Studio (latest stable)
- JDK 17 or newer (CI uses Temurin 21)
- Android SDK 36
- A device or emulator on API 26+ (API 34+ to test Health Connect)

## Build and run

```bash
git clone https://github.com/arunkmr13/HydroTracker.git
cd HydroTracker
./gradlew assembleDebug          # APK in app/build/outputs/apk/debug/
./gradlew installDebug           # install on a connected device
```

## Tests and checks

```bash
./gradlew testDebugUnitTest      # JVM unit tests (domain logic)
./gradlew lintDebug              # Android lint; report in app/build/reports/
```

CI runs the same commands on every pull request and on `main`
(see [`.github/workflows/ci.yml`](.github/workflows/ci.yml)).

## Database changes

Room schemas are exported to `app/schemas/`. Any entity change needs:

1. A version bump in the database class.
2. A `Migration` (no destructive fallback in release builds).
3. The new exported schema JSON committed with the change.

## Releasing

Releases are built and signed by GitHub Actions
(see [`.github/workflows/release.yml`](.github/workflows/release.yml)).

**One-time setup**: add these repository secrets (Settings → Secrets and variables → Actions):

| Secret | Value |
|---|---|
| `SIGNING_KEYSTORE_BASE64` | `base64 -w0 release.jks` (macOS: `base64 -i release.jks`) |
| `SIGNING_KEYSTORE_PASSWORD` | keystore password |
| `SIGNING_KEY_ALIAS` | key alias |
| `SIGNING_KEY_PASSWORD` | key password |

Keep the keystore itself out of the repo and backed up somewhere safe. Losing it means users can't
update in place.

**Each release:**

1. Bump `versionCode` and `versionName` in `app/build.gradle.kts`.
2. Move the **Unreleased** notes in `CHANGELOG.md` under the new version.
3. Commit, then tag and push:
   ```bash
   git tag v2.0.0
   git push origin v2.0.0
   ```
4. The workflow publishes a GitHub Release with the signed APK and its SHA-256.
   Tags with a suffix (`v2.0.0-beta.1`) are marked as pre-releases.
