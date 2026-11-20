# Contributing to HydroTrack

Thanks for helping out. Bug reports, ideas and pull requests are all welcome.

## Before you start

- **Bugs and ideas:** open an issue using the templates.
- **Larger changes:** open an issue first so we can agree on the approach before you write code.
- **Principles:** HydroTrack is local-only. No accounts, no analytics, no network calls. Health Connect
  sync is optional and write-only. Changes that break this won't be merged.

## Workflow

1. Fork the repo and branch from `main` (`feat/…`, `fix/…`, `chore/…`).
2. Set up the project as described in [DEVELOPMENT.md](DEVELOPMENT.md).
3. Keep business rules in `domain/` (pure Kotlin) and cover them with unit tests.
4. Run `./gradlew testDebugUnitTest lintDebug` before pushing.
5. Add a line under **Unreleased** in [CHANGELOG.md](CHANGELOG.md).
6. Open a pull request and fill in the template. CI must pass.

## Commit messages

Use the [Conventional Commits](https://www.conventionalcommits.org/) style:
`feat: add drink types`, `fix: widget not refreshing after midnight`, `docs: …`, `chore: …`.

## Code style

Follow the official Kotlin style (`kotlin.code.style=official`) and the `.editorconfig` in the repo.
