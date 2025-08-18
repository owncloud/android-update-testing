/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.android;

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

    public static FilelistPage instance;

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

    public boolean isViewVisible() {
        Log.log(Level.FINE, "Starts: Check if file list view is visible");
        waitById(WAIT_TIME,"com.owncloud.android:id/fab_expand_menu_button");
        return !toolbar.isEmpty() && !bottomBar.isEmpty();
    }

    public boolean isItemInList(String itemName) {
        Log.log(Level.FINE, "Starts: Check if item is in list: " + itemName);
        return !findListUIAutomatorText(itemName).isEmpty();
    }

}
