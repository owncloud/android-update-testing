/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.android;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SettingsPage extends CommonPage {

    public SettingsPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
