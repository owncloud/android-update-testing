# AI Agent Guidelines for Android Update Testing

This file provides context for AI coding agents (Claude Code, GitHub Copilot, Cursor, etc.) working in this repository.

## Repository Overview
- **Product family:** Mobile (Android)
- **Primary language(s):** Java
- **Build system:** Gradle
- **Test framework:** JUnit, Appium
- **CI system:** GitHub Actions

## Architecture & Key Paths
- `src/` - Test source code; the single Gherkin feature file is
  `src/test/resources/io/cucumber/features.feature`
- `server/` - Server setup scripts
- `build.gradle` - Gradle build configuration
- `gradle/` - Gradle wrapper
- `runAppium.sh` - Appium server launcher
- `local.properties` - Local configuration (APK names, passcode, package name,
  Appium URL). Gitignored; see `LocProperties.java` for the keys it expects.

## Development Conventions
- **Branching:** main
- **Commit messages:** DCO sign-off required (`git commit -s`)
- **Code style:** No linter is configured
- **PR process:** Open a PR against main. All CI checks must pass.

## Build & Test Commands
```bash
# Build
./gradlew build

# Build without running tests
./gradlew build -x test

# Test
./gradlew clean test -Dserver="https://myserver:9200" -Dusername=john -Dpassword=mypass -Dcommit=87a6f33
```

## Test Architecture

A single-scenario Cucumber/JUnit/Appium suite that verifies the ownCloud Android
app upgrade path.

### Layer model

All Cucumber classes receive a `World` instance via PicoContainer constructor
injection. `World` is the single service locator — it lazily constructs every
collaborator on first access.

```
StepDefinitions  ->  preconditions / tasks / assertions  ->  pages / APIs
                                    ^
                                  World
```

- **`steps/StepDefinitions`** - thin glue only; each step body is a single delegation call.
- **`preconditions/`** - server-side setup via API before the app is touched (`@Given`).
- **`tasks/`** - UI interactions the actor performs (`@When`).
- **`assertions/`** - state verification with `assertTrue`/`assertEquals` (`@Then`).
- **`pages/`** - Page Object Model; one class per screen. `CommonPage` is the base.
- **`api/`** - OkHttp-based REST/WebDAV clients. `CommonAPI` is the base (holds
  credentials, personal-space ID, `baseRequestBuilder()`). All `Response` objects
  must be wrapped in try-with-resources.
- **`world/World`** - lazy singleton hub; never instantiates a collaborator more
  than once per scenario.
- **`hooks/Hooks`** - Cucumber `@Before`/`@After`; starts/stops screen recording
  and device cleanup.
- **`support/`** - cross-cutting utilities: `Log`, `StepLogger`, `ScreenRecorder`,
  `DeviceClient`, `oCHttpClient`, SAX/JSON parsers.

### Things to know before changing the code

- `isOCIS` in `CommonAPI` is hardcoded `true` - all OC10 branches in the API
  layer are unreachable dead code.
- `AndroidManager` is a static singleton; `getDriver()` initialises it on first call.
- `ScreenRecorder` saves video only when a scenario fails.

## Important Constraints
- All code contributions must be compatible with the **GPL-3.0** license
- Do not introduce new **copyleft-licensed dependencies** (GPL, AGPL, LGPL, MPL) without explicit discussion in an issue first. This is especially important for repos migrating to Apache 2.0.
- Do not introduce new dependencies without discussion in an issue first
- Tests require a running Appium server and an Android device/emulator
- Tests require two APK files (older and newer versions) placed under
  `src/test/resources/`, and a reachable ownCloud server


## OSPO Policy Constraints

### GitHub Actions
- **Only** use actions owned by `owncloud`, created by GitHub (`actions/*`), verified on the GitHub Marketplace, or verified by the ownCloud Maintainers.
- Pin all actions to their full commit SHA (not tags): `uses: actions/checkout@<SHA> # vX.Y.Z`
- Never introduce actions from unverified third parties.

### Dependency Management
- Dependabot is configured for automated dependency updates.
- Review and merge Dependabot PRs as part of regular maintenance.
- Do not introduce new dependencies without discussion in an issue first.

### Git Workflow
- **Rebase policy**: Always rebase; never create merge commits. Use `git pull --rebase` and `git rebase` before pushing.
- **Signed commits**: All commits **must** be PGP/GPG signed (`git commit -S -s`).
- **DCO sign-off**: Every commit needs a `Signed-off-by` line (`git commit -s`).
- **Conventional Commits & Squash Merge**: Use the [Conventional Commits](https://www.conventionalcommits.org/) format where the repository enforces it. Many repos use squash merge, where the PR title becomes the commit message on the default branch — apply Conventional Commits format to PR titles as well. A reusable GitHub Actions workflow enforces this.

## Context for AI Agents
- Match existing code style
- Do not refactor unrelated code in the same PR
- Write tests for new functionality
- Keep PRs focused and atomic
- This is a focused test repo - changes should relate to update/upgrade testing only
