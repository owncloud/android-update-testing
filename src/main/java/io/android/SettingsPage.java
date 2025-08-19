/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.android;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class SettingsPage extends CommonPage {

    public static SettingsPage instance;

    public SettingsPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static SettingsPage getInstance() {
        if (instance == null) {
            instance = new SettingsPage();
        }
        return instance;
    }

    public boolean isCommitCorrect(String commitHash) {
        Log.log(Level.FINE, "Starts: Check if commit is correct: " + commitHash);
        return !findListUIAutomatorText(commitHash).isEmpty();
    }
}
