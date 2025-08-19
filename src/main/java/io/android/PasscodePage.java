package io.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class PasscodePage extends CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/passcodeLockLayout")
    private WebElement passcodeLayout;

    @AndroidFindBy(id = "com.owncloud.android:id/key1Container")
    private WebElement key1;

    @AndroidFindBy(id = "com.owncloud.android:id/key2Container")
    private WebElement key2;

    @AndroidFindBy(id = "com.owncloud.android:id/key3Container")
    private WebElement key3;

    @AndroidFindBy(id = "com.owncloud.android:id/key4Container")
    private WebElement key4;

    @AndroidFindBy(id = "com.owncloud.android:id/key5Container")
    private WebElement key5;

    @AndroidFindBy(id = "com.owncloud.android:id/key6Container")
    private WebElement key6;

    @AndroidFindBy(id = "com.owncloud.android:id/key7Container")
    private WebElement key7;

    @AndroidFindBy(id = "com.owncloud.android:id/key8Container")
    private WebElement key8;

    @AndroidFindBy(id = "com.owncloud.android:id/key9Container")
    private WebElement key9;

    @AndroidFindBy(id = "com.owncloud.android:id/key0Container")
    private WebElement key0;

    public static PasscodePage instance;

    public PasscodePage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static PasscodePage getInstance() {
        if (instance == null) {
            instance = new PasscodePage();
        }
        return instance;
    }

    public boolean isPasscodeVisible() {
        Log.log(Level.FINE, "Starts: Check if passcode view is visible");
        return passcodeLayout.isDisplayed();
    }

    public void enterPasscode (String char1, String char2, String char3, String char4) {
        Log.log(Level.FINE, "Starts: Enter Passcode: " + char1 + char2 + char3 + char4);
        clickPasscodeChar(char1);
        clickPasscodeChar(char2);
        clickPasscodeChar(char3);
        clickPasscodeChar(char4);
    }

    private void clickPasscodeChar (String num) {
        switch (num) {
            case "1" -> key1.click();
            case "2" -> key2.click();
            case "3" -> key3.click();
            case "4" -> key4.click();
            case "5" -> key5.click();
            case "6" -> key6.click();
            case "7" -> key7.click();
            case "8" -> key8.click();
            case "9" -> key9.click();
            case "0" -> key0.click();
        }
    }
}
