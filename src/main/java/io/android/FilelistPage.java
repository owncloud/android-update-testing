/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class FilelistPage extends CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/root_toolbar")
    private List<WebElement> toolbar;

    @AndroidFindBy(id = "com.owncloud.android:id/bottom_nav_view")
    private List<WebElement> bottomBar;

    @AndroidFindBy(id = "com.owncloud.android:id/root_toolbar_left_icon")
    private List<WebElement> hamburgerButton;

    @AndroidFindBy(id = "com.owncloud.android:id/nav_settings")
    private WebElement settingsButton;

    public static FilelistPage instance;
    private final String fabId = "com.owncloud.android:id/fab_expand_menu_button";

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc=\"Back\"]")
    private WebElement back;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.owncloud.android:id/action_mode_close_button\");")
    private WebElement closeSelectionMode;

    @AndroidFindBy(id = "com.owncloud.android:id/text_preview")
    private WebElement textPreview;



    private FilelistPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static FilelistPage getInstance() {
        if (instance == null) {
            instance = new FilelistPage();
        }
        return instance;
    }

    public void download(String itemName) {
        Log.log(Level.FINE, "Starts: download action: " + itemName);
        findListUIAutomatorText(itemName).get(0).click();
    }

    public void backListFiles() {
        Log.log(Level.FINE, "Start: Back to the list of files");
        back.click();
    }

    public boolean isViewVisible() {
        Log.log(Level.FINE, "Starts: Check if file list view is visible");
        waitById(fabId);
        return !toolbar.isEmpty() && !bottomBar.isEmpty();
    }

    public boolean isItemInList(String itemName) {
        Log.log(Level.FINE, "Starts: Check if item is in list: " + itemName);
        return !findListUIAutomatorText(itemName).isEmpty();
    }

    public boolean isItemPreviewed() {
        return textPreview.isDisplayed();
    }

    public void openMenuActions(String operation) {
        findUIAutomatorDescription("More options").click();
        findListUIAutomatorText(operation).get(0).click();
    }

    public void closeSelectionMode() {
        Log.log(Level.FINE, "Starts: close selection mode");
        closeSelectionMode.click();
    }

    public void openSettings() {
        Log.log(Level.FINE, "Starts: Open Settings");
        hamburgerButton.get(0).click();
        settingsButton.click();
    }

    public void openPasscode() {
        Log.log(Level.FINE, "Starts: Open Passocode Settings");
        openSettings();
        findListUIAutomatorText("Security").get(0).click();
        findListUIAutomatorText("Passcode lock").get(0).click();
    }
}
