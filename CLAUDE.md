# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run the full test suite
./gradlew clean test -Dserver="https://myserver:9200" -Dusername=john -Dpassword=mypass -Dcommit=87a6f33

# Build without running tests
./gradlew build -x test
```

There is no linter configured. Tests require a running Appium server, an Android device/emulator, two APK files under `src/test/resources/`, and a reachable ownCloud server.

Local configuration goes in `local.properties` (APK filenames, passcode, app package name, Appium URL). This file is gitignored; see `LocProperties.java` for the keys it expects.

## Architecture

This is a single-scenario Cucumber/JUnit/Appium test suite that verifies the ownCloud Android app upgrade path. The one feature file lives at `src/test/resources/io/cucumber/features.feature`.

### Layer model

All Cucumber classes receive a `World` instance via PicoContainer constructor injection. `World` is the single service locator — it lazily constructs every collaborator on first access.

```
StepDefinitions  →  preconditions / tasks / assertions  →  pages / APIs
                                   ↑
                                 World
```

- **`steps/StepDefinitions`** — thin glue only; each step body is a single delegation call.
- **`preconditions/`** — server-side setup via API before the app is touched (`@Given`).
- **`tasks/`** — UI interactions the actor performs (`@When`).
- **`assertions/`** — state verification with `assertTrue`/`assertEquals` (`@Then`).
- **`pages/`** — Page Object Model; one class per screen. `CommonPage` is the base.
- **`api/`** — OkHttp-based REST/WebDAV clients. `CommonAPI` is the base (holds credentials, personal-space ID, `baseRequestBuilder()`). All `Response` objects must be wrapped in try-with-resources.
- **`world/World`** — lazy singleton hub; never instantiates a collaborator more than once per scenario.
- **`hooks/Hooks`** — Cucumber `@Before`/`@After`; starts/stops screen recording and device cleanup.
- **`support/`** — cross-cutting utilities: `Log`, `StepLogger`, `ScreenRecorder`, `DeviceClient`, `oCHttpClient`, SAX/JSON parsers.

### Key constraints

- `isOCIS` in `CommonAPI` is hardcoded `true` — all OC10 branches in the API layer are unreachable dead code.
- `AndroidManager` is a static singleton; `getDriver()` initialises it on first call.
- `ScreenRecorder` saves video only when a scenario fails.
- All commits must be GPG-signed and carry a DCO `Signed-off-by` line: `git commit -S -s -m "..."`.
- GitHub Actions may only use actions owned by `owncloud`, `actions/*`, or verified Marketplace actions, pinned to a full commit SHA.
- Do not introduce new dependencies without an issue discussion first (license migration to Apache 2.0 is in progress).
