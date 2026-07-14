/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import update.LocProperties;
import update.support.log.Log;

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

    public LoginPage(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(CommonPage.driver), this);
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

    public void reinstall() throws InterruptedException {
        File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath, "src/test/resources");
        File app = new File(appDir,LocProperties.getProperties().getProperty("apk2update"));
        CommonPage.driver.installApp(app.getAbsolutePath());
        // Ugly waiter, no other way to wait for the app to be installed and the system to be ready
        Thread.sleep(3000);
        CommonPage.driver.activateApp(LocProperties.getProperties().getProperty("appPackage"));
        //Go to settings and check
    }
}
