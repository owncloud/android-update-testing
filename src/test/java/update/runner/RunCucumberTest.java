/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.logging.Level;

import update.pages.AndroidManager;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import update.LocProperties;
import update.support.log.Log;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        glue = "update",
        features = "src/test/resources/io/cucumber/"
)
public class RunCucumberTest {

    @BeforeClass
    public static void beforeclass() {
        Log.init();
        Log.log(Level.FINE, "START EXECUTION\n");
    }

    @AfterClass
    public static void afterclass() {
        AndroidManager.getDriver().removeApp(LocProperties.getProperties().getProperty("appPackage"));
        AndroidManager.getDriver().quit();
        Log.log(Level.FINE, "END EXECUTION\n");
    }
}
