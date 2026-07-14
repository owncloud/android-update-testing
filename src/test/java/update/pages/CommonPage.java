/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.pages;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import update.support.log.Log;

public class CommonPage {

    protected AndroidDriver driver;
    protected static final int WAIT_TIME = 20;

    public CommonPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public WebElement findId(String id) {
        return driver.findElement(AppiumBy.id(id));
    }

    public List<WebElement> findListUIAutomatorText(String finder) {
        return driver.findElements(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"" + finder + "\");"));
    }

    public void waitById(String resourceId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id(resourceId)));
    }

    public WebElement findUIAutomatorDescription(String description) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().description(\"" + description + "\");"));
    }

    public void longPress(String text) {
        Log.log(Level.FINE, "Starting long press on element with text: " + text);
        WebElement element = driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + text + "\")"));
        Log.log(Level.FINE, "Target element text: " + element.getText());
        Log.log(Level.FINE, "Location: " + element.getLocation());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME));
        wait.until(driver1 -> element.isDisplayed() && element.isEnabled());
        Point location = element.getLocation();
        Dimension size = element.getSize();
        int centerX = location.getX() + size.getWidth() / 2;
        int centerY = location.getY() + size.getHeight() / 2;
        Log.log(Level.FINE, "Pressing at: (" + centerX + ", " + centerY + ")");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence longPress = new Sequence(finger, 1);
        longPress.addAction(finger.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), centerX, centerY));
        longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        longPress.addAction(new Pause(finger, Duration.ofSeconds(2)));
        longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
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
        driver.perform(List.of(swipe));
    }
}
