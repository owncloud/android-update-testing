# Update Testing

This project verifies that upgrading from an older version of the app to a newer one does not cause crashes or break functionality.

The tests in this repository are designed to run within a CI workflow that builds both the older and newer versions for every pull request.

## Setup

In the `local.properties` file

- Name of the file containing older and newer version. By default: `owncloudSigned1.apk` and `owncloudSigned2.apk`.
- Passcode to set in the app (4 digits)
- Package name
- Appium URL

## Execution

The gradlew process launchs the tests with the following parameters:

- Server URL: ownCloud server to test. Basic auth as 1st auth method.
- Username: available in the server.
- Password: for the username to access.
- Commit: hash to compare against `latest` tag in CI.

Command:

```
./gradlew clean test -Dserver="https://myserver:9200" -Dusername=john -Dpassword=mypass -Dcommit=87a6f33
```

## Process

1. Add some example files to the given account
2. Install the older version `owncloudSigned1.apk`
3. Log in by using the given credentials
4. Check list of files
5. Add a passcode to the app
6. Install the newest version over the older (without reinstalling)
7. Verifies the passcode, the list of files, and the commit hash

(open to add more checks)

