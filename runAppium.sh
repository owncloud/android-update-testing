#!/bin/bash
set -ex
npm install -g appium
appium -v
appium driver install uiautomator2
appium --allow-insecure=adb_shell &>/dev/null &
echo "Initializing Appium..."
while true; do
    if curl -s http://localhost:4723/status | jq -e '.value.ready' >/dev/null 2>&1; then
        echo "Appium is ready!"
        break
    else
        echo "Waiting for Appium to be ready..."
        sleep 1
    fi
done
