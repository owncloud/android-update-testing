/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class CommonPage {

    protected static AndroidDriver driver = AndroidManager.getDriver();
    protected static final int WAIT_TIME = 10;

    public CommonPage() {
    }

    public List<WebElement> findListUIAutomatorText(String finder) {
        return driver.findElements(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"" + finder + "\");"));
    }

    public static void waitById(int timeToWait, String resourceId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id(resourceId)));
    }
}
