/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.pages;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Level;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
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

        UiAutomator2Options capabilities = new UiAutomator2Options();
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
    public static void setCapabilities(UiAutomator2Options capabilities) {
        capabilities.setDeviceName("test");
        capabilities.setApp(app.getAbsolutePath());
        capabilities.setAppPackage(appPackage);
        capabilities.setAppActivity("com.owncloud.android.ui.activity.SplashActivity");
        capabilities.setAppWaitPackage(LocProperties.getProperties().getProperty("appPackage"));
        capabilities.setAppWaitForLaunch(true);
        capabilities.setAutoGrantPermissions(true);
        capabilities.setDisableWindowAnimation(true);
        capabilities.setNoReset(true);
        capabilities.setNewCommandTimeout(Duration.ofSeconds(60));
        capabilities.setCapability("appium:uiautomator2ServerLaunchTimeout", 60000);
        capabilities.setCapability("appium:adbExecTimeout", 60000);
        capabilities.setCapability("appium:androidInstallTimeout", 90000);
    }
}
