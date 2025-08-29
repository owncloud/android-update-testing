/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.android;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import utils.log.Log;

public class CommonPage {

    protected static AndroidDriver driver = AndroidManager.getDriver();
    protected static final int WAIT_TIME = 10;

    public CommonPage() {
    }

    public WebElement findId(String id) {
        return driver.findElement(AppiumBy.id(id));
    }

    public List<WebElement> findListUIAutomatorText(String finder) {
        return driver.findElements(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"" + finder + "\");"));
    }

    public static void waitById(String resourceId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id(resourceId)));
    }

    public static void waitById(int timeToWait, WebElement mobileElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.visibilityOf(mobileElement));
    }

    public WebElement findUIAutomatorDescription(String description) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().description(\"" + description + "\");"));
    }

    public void longPress(String text) {
        Log.log(Level.FINE, "Starting long press on element with text: " + text);
        // Find the element using exact text match
        WebElement element = driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + text + "\")"));
        Log.log(Level.FINE, "Target element text: " + element.getText());
        Log.log(Level.FINE, "Location: " + element.getLocation());
        // Wait until the element is actually visible and enabled
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
        wait.until(driver1 -> element.isDisplayed() && element.isEnabled());
        // Get the element's location and size to calculate its center
        Point location = element.getLocation();
        Dimension size = element.getSize();
        int centerX = location.getX() + size.getWidth() / 2;
        int centerY = location.getY() + size.getHeight() / 2;
        Log.log(Level.FINE, "Pressing at: (" + centerX + ", " + centerY + ")");
        // Set up the long press gesture using W3C actions
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence longPress = new Sequence(finger, 1);
        // Move the finger to the center of the element
        longPress.addAction(finger.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), centerX, centerY));
        // Touch down (press)
        longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        // Hold for 2 seconds
        longPress.addAction(new Pause(finger, Duration.ofSeconds(2)));
        // Release (lift up)
        longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        // Execute the long press gesture
        driver.perform(List.of(longPress));
    }

    public void swipe(double startx, double starty, double endx, double endy) {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * starty);
        int endY = (int) (size.height * endy);
        int startX = (int) (size.width * startx);
        int endX = (int) (size.width * endx);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));
    }

    public void cleanUpDevice() {
        Log.log(Level.FINE, "Starts: Clean up device, owncloud folder");
        // Remove owncloud folder from device
        Map<String, Object> args = new HashMap<>();
        args.put("command", "rm");
        args.put("args", Arrays.asList("-rf", getDownloadsFolder() + "/*"));
        driver.executeScript("mobile: shell", args);
    }

    protected String getDownloadsFolder() {
        Log.log(Level.FINE, "Starts: Get downloads folder");
        Map<String, Object> args = new HashMap<>();
        args.put("command", "ls");
        args.put("args", Arrays.asList("/sdcard"));
        String output = (String) driver.executeScript("mobile: shell", args);

        if (output.contains("Download")) {
            Log.log(Level.FINE, "/sdcard/Download");
            return "/sdcard/Download";
        } else if (output.contains("Downloads")) {
            Log.log(Level.FINE, "/sdcard/Downloads");
            return "/sdcard/Downloads";
        } else {
            return "";
        }
    }

    public static void startRecording() {
        AndroidStartScreenRecordingOptions androidStartScreenRecordingOptions =
                new AndroidStartScreenRecordingOptions();
        androidStartScreenRecordingOptions.withBitRate(2000000);
        androidStartScreenRecordingOptions.withVideoSize("360x640");
        driver.startRecordingScreen(androidStartScreenRecordingOptions);
    }

    public static void stopRecording() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String base64String = driver.stopRecordingScreen();
        byte[] data = Base64.decodeBase64(base64String);
        String destinationPath = "video/upgrade_" +
                sdf.format(new Timestamp(System.currentTimeMillis()).getTime()) + ".mp4";
        Path path = Paths.get(destinationPath);
        try {
            Files.write(path, data);
        } catch (IOException e) {
            Log.log(Level.FINE, e.getMessage());
        }
    }
}
