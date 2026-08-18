# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Workflows overview

| File | Trigger | Purpose |
|---|---|---|
| `update.yml` | Schedule (Mon–Fri 01:00 UTC) + `workflow_dispatch` | Orchestrator: checks for new commits in `owncloud/android`, then fans out to build, sign, provision, test, and clean up |
| `ocisbackend.yml` | `workflow_call`, `workflow_dispatch` | Provisions an oCIS server on a remote VM via Ansible (`server/ansible/ocis.yml`) |
| `sign-apk.yml` | `workflow_call` | Zipaligns + signs an APK artifact with the test keystore using Android build-tools 34.0.0 |
| `cleanup.yml` | `workflow_call`, `workflow_dispatch` | Tears down Docker containers and images on the remote VM via Ansible |
| `compile.yml` | `pull_request` | Compiles the test suite (`compileJava` + `compileTestJava`) — smoke-checks PRs without running tests |

## `update.yml` pipeline

```
check_repo_changes
       │
       ├─► create_ocis (ocisbackend.yml)   ─────────────────────────────────┐
       ├─► build_apk_latest (owncloud/android reusable workflow)              │
       │       └─► sign_latest (sign-apk.yml)                                 │
       └─► build_apk_master (owncloud/android reusable workflow)              │
               └─► sign_master (sign-apk.yml)                                 │
                                                                               ▼
                                               update_test (emulator + gradlew test)
                                                               │
                                                               ▼
                                                           cleanup.yml
```

`check_repo_changes` uses `Europe/Madrid` timezone to count commits in `owncloud/android` master. Monday runs cover Friday–Saturday (weekend bridge). The whole pipeline is skipped on schedule if there were zero commits; `workflow_dispatch` always runs.

## Required secrets and variables

| Name | Used by | Description |
|---|---|---|
| `OC_SERVER_URL` | `update.yml` → `ocisbackend.yml` | Full URL of the remote oCIS server |
| `OC_SERVER_USERNAME_TEST` | `update.yml` | Test user credentials for the Gradle run |
| `OC_SERVER_PASSWORD_TEST` | `update.yml` | Test user credentials for the Gradle run |
| `SSH_HOST` | `ocisbackend.yml`, `cleanup.yml` | Remote VM hostname |
| `SSH_USER` | `ocisbackend.yml`, `cleanup.yml` | Remote VM SSH user |
| `SSH_PRIVATE_KEY` | `ocisbackend.yml`, `cleanup.yml` | Ed25519 private key for SSH access |
| `OCIS_PWD` | `ocisbackend.yml` | oCIS admin password used during provisioning |
| `TEST_KS_B64` | `sign-apk.yml` | Base64-encoded PKCS12 test keystore |
| `TEST_KS_ALIAS` | `sign-apk.yml` | Key alias inside the keystore |
| `TEST_KS_KEY` | `sign-apk.yml` | Key + keystore password |
| `vars.OCIS_VERSION` | `update.yml` | Repository variable controlling which oCIS version to deploy |

## Emulator configuration

`update_test` runs on `reactivecircus/android-emulator-runner` with:
- API level 31, `google_apis` target, `x86_64`, `pixel_5` profile
- KVM acceleration enabled via udev rule before the step runs
- `disable-animations: true`; no snapshot, no window, no audio, `swiftshader_indirect` GPU, `-feature -Vulkan`
- 4 096 MB RAM; crash logcat captured to `crash_log.txt` in parallel with the test run

## Artifacts produced by `update_test`

| Artifact name | Content | Retained |
|---|---|---|
| `crash-log` | ADB crash logcat | default |
| `logs` | Appium/test execution log (`logs/log.log`) | default |
| `appium` | `appium.log` | default |
| `video-recording` | `test-recording.zip` (screen capture on failure) | default |
| `owncloudSigned-latest` / `owncloudSigned-master` | Signed APKs from `sign-apk.yml` | 2 days |

## Conventions

- All action references must be pinned to a full commit SHA, not a tag.
- Only actions owned by `owncloud`, `actions/*`, or verified Marketplace publishers are allowed.
- `ocis_url` secret name in `ocisbackend.yml` is lowercase when passed via `workflow_call` (`secrets: ocis_url`) but the internal env var is `BASE_URL`. Keep this asymmetry in mind when editing.
