/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.pages;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Level;

import io.appium.java_client.android.AndroidDriver;
import update.LocProperties;
import update.support.log.Log;

public class AndroidManager {

    private static AndroidDriver driver = null;
    private static File app;
    private static final String driverURL = LocProperties.getProperties().getProperty("appiumURL");
    private static final String appPackage = LocProperties.getProperties().getProperty("appPackage");
    private static final long IMPLICIT_WAIT = 20;

    private AndroidManager() {
    }

    public static void init() {

        File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath, "src/test/resources");
        app = new File(appDir, LocProperties.getProperties().getProperty("apkName"));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        setCapabilities(capabilities);

        try {
            driver = new AndroidDriver(new URL(driverURL), capabilities);
        } catch (MalformedURLException e) {
            Log.log(Level.SEVERE, "Driver could not be created: " + e.getMessage());
            throw new RuntimeException("Invalid Appium URL: " + driverURL, e);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));

        Log.log(Level.FINE, "Device: " +
                driver.getCapabilities().getCapability("deviceManufacturer") + " " +
                driver.getCapabilities().getCapability("deviceModel"));
        Log.log(Level.FINE, "Platform: " +
                driver.getCapabilities().getCapability("platformName") + " " +
                driver.getCapabilities().getCapability("platformVersion"));
        Log.log(Level.FINE, "API Level: " +
                driver.getCapabilities().getCapability("deviceApiLevel") + "\n");

    }

    public static AndroidDriver getDriver() {
        if (driver == null) {
            init();
        }
        return driver;
    }

    //Check https://appium.io/docs/en/2.5/guides/caps/
    public static void setCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability("appium:deviceName", "test");
        capabilities.setCapability("appium:app", app.getAbsolutePath());
        capabilities.setCapability("appium:platformName", "Android");
        capabilities.setCapability("appium:automationName", "UIAutomator2");
        capabilities.setCapability("appium:appPackage", appPackage);
        capabilities.setCapability("appium:appActivity",
                "com.owncloud.android.ui.activity.SplashActivity");
        capabilities.setCapability("appium:appWaitPackage",
                LocProperties.getProperties().getProperty("appPackage"));
        capabilities.setCapability("appium:appWaitForLaunch", "true");
        capabilities.setCapability("appium:autoGrantPermissions", true);
        capabilities.setCapability("appium:unicodeKeyboard", true);
        capabilities.setCapability("appium:resetKeyboard", true);
        capabilities.setCapability("appium:disableWindowAnimation", true);
        capabilities.setCapability("appium:noReset", true);
        capabilities.setCapability("appium:newCommandTimeout", 60);
        capabilities.setCapability("appium:uiautomator2ServerLaunchTimeout", 60000);
        capabilities.setCapability("appium:adbExecTimeout", 60000);
        capabilities.setCapability("appium:androidInstallTimeout", 90000);
    }
}
