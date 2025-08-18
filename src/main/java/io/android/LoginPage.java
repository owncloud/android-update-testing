/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.LocProperties;
import utils.log.Log;

public class LoginPage extends CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/hostUrlInput")
    private List<WebElement> urlServer;

    @AndroidFindBy(id = "com.owncloud.android:id/embeddedCheckServerButton")
    private WebElement checkServerButton;

    @AndroidFindBy(id = "com.owncloud.android:id/account_username")
    private WebElement userNameText;

    @AndroidFindBy(id = "com.owncloud.android:id/account_password")
    private WebElement passwordText;

    @AndroidFindBy(id = "com.owncloud.android:id/loginButton")
    private WebElement loginButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"YES\");")
    private WebElement acceptCertificate;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement acceptHttp;

    public static LoginPage instance;
    private String server;

    private LoginPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static LoginPage getInstance() {
        if (instance == null) {
            instance = new LoginPage();
        }
        return instance;
    }

    public void typeURL(String server) {
        urlServer.get(0).sendKeys(server);
        this.server = server;
        checkServerButton.click();
    }

    public void typeCredentials(String username, String password) {
        acceptWarning();
        userNameText.sendKeys(username);
        passwordText.sendKeys(password);
    }

    public void submitLogin() {
        Log.log(Level.FINE, "Starts: Submit login");
        loginButton.click();
    }

    public void acceptWarning() {
        Log.log(Level.FINE, "Accept warning");
        String prefix = server.split("://")[0];
        if (prefix.equals("https")) {
            acceptCertificate.click();
        } else { //http
            acceptHttp.click();
        }
    }

    public void reinstall() {
        File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath, "src/test/resources");
        File app = new File(appDir,LocProperties.getProperties().getProperty("apk2update"));
        driver.installApp(app.getAbsolutePath());
        driver.activateApp(LocProperties.getProperties().getProperty("appPackage"));
        //Go to settings and check
    }
}
