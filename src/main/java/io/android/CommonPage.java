/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.android;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
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
