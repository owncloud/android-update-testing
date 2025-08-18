[![Tests](https://github.com/jesmrec/CucumberAppiumAndroidSkeleton/actions/workflows/tests.yml/badge.svg)](https://github.com/jesmrec/CucumberAppiumAndroidSkeleton/actions/workflows/tests.yml)

# Cucumber + Appium + Android testing

This is an example, how to integrate Cucumber and Appium to automate scenarios over an Android device or emulator.

Our sample app will be [Now in Android](https://github.com/android/nowinandroid), developed in Kotlin and Jetpack Compose, licensed Apache 2.0

## Get the code


	git clone https://github.com/jesmrec/CucumberAppiumAndroidSkeleton


or [download a zip](https://github.com/jesmrec/CucumberAppiumAndroidSkeleton/archive/refs/heads/main.zip) file.

## Android SDK

Be sure your machine hosts the Android SDK. It is always included in the latest version of Android Studio, but other package manager like homebrew allows to install it witout the IDE.

## Appium

Install and run *Appium* in your machine. This sample is intended to be run with *Appium* 2.0+. If *Appium* is not installed in your machine, follow the [official Appium installation manual](https://appium.io/docs/en/2.0/quickstart/install/)

## Device

Next step is attaching device or emulator to the machine in which tests are going to be run. With the device attached, the command

	adb devices
	
should return one line with the device ID.	

## Use Gradle

Now, we are ready to launch the testing. Type the following command to run the tests

    ./gradlew clean test --info

If everything goes fine, you'll see everything green!

<font color='green'>

    Scenario Outline: Done unlocked # io/cucumber/features.feature:12
      Given I open the app          # io.cucumber.StepDefinitions.I_open_the_app()
      When I click on Headlines     # io.cucumber.StepDefinitions.I_click_on(java.lang.String)
      Then Done button is unlocked  # io.cucumber.StepDefinitions.done_unlocked()

    Scenario: Settings displayed        # io/cucumber/features.feature:16
      Given I open the app              # io.cucumber.StepDefinitions.I_open_the_app()
      When I open Settings              # io.cucumber.StepDefinitions.I_click_on_settings()
      Then I see the following sections # io.cucumber.StepDefinitions.sections_in_settings(io.cucumber.datatable.DataTable)
        | Theme                |
        | Use Dynamic Color    |
        | Dark mode preference |

    ┌───────────────────────────────────────────────────────────────────────────────────┐
    │ Share your Cucumber Report with your team at https://reports.cucumber.io          │
    │ Activate publishing with one of the following:                                    │
    │                                                                                   │
    │ src/test/resources/cucumber.properties:          cucumber.publish.enabled=true    │
    │ src/test/resources/junit-platform.properties:    cucumber.publish.enabled=true    │
    │ Environment variable:                            CUCUMBER_PUBLISH_ENABLED=true    │
    │ JUnit:                                           @CucumberOptions(publish = true) │
    │                                                                                   │
    │ More information at https://cucumber.io/docs/cucumber/environment-variables/      │
    │                                                                                   │
    │ Disable this message with one of the following:                                   │
    │                                                                                   │
    │ src/test/resources/cucumber.properties:          cucumber.publish.quiet=true      │
    │ src/test/resources/junit-platform.properties:    cucumber.publish.quiet=true      │
    └───────────────────────────────────────────────────────────────────────────────────┘

   
</font>

This sample is ready to use [cucumber-reports](https://cucumber.io/docs/cucumber/reporting/?lang=java). Check documentation about the values to set in cucumber.properties file 

Repository forked from https://github.com/cucumber/cucumber-java-skeleton
