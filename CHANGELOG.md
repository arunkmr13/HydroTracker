# Changelog

All notable changes to this project are documented here.
The format follows [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and the project uses
[Semantic Versioning](https://semver.org/).

## [Unreleased]

### Added
- CI (unit tests, debug build, lint) and a tag-driven signed release workflow.
- Issue and pull request templates, Dependabot, `LICENSE`, `SECURITY.md`, `CONTRIBUTING.md`,
  `DEVELOPMENT.md`, `.editorconfig`.

### Removed
- Committed build outputs (`app/build/`) from version control.

## [1.0.0]

### Added
- Weight-based daily target (35 ml/kg) with manual override.
- Progress ring, bottle refill logging, monthly history calendar.
- Home screen widgets, reminders with notification actions.
- Symptom check-in and CSV export.
- Firebase sign-in, Firestore sync and analytics.

[Unreleased]: https://github.com/arunkmr13/HydroTracker/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/arunkmr13/HydroTracker/releases/tag/v1.0.0
