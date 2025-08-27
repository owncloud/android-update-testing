#!/bin/bash
set -ex
npm install -g appium
appium -v --allow-insecure=adb_shell
appium driver install uiautomator2
appium &>/dev/null &
