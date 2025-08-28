/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;

import io.android.AndroidManager;
import io.android.CommonPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.LocProperties;
import utils.entities.OCFile;
import utils.log.Log;

public class Hooks {

    private World world;

    public Hooks(World world) {
        this.world = world;
    }

    @Before
    public void setup(Scenario scenario) {
        Log.log(Level.FINE, "START SCENARIO EXECUTION: " + scenario.getName());
        AndroidManager.getDriver().activateApp(
                LocProperties.getProperties().getProperty("appPackage"));
        CommonPage.startRecording();
    }

    @After
    public void tearDown(Scenario scenario) throws IOException, ParserConfigurationException, SAXException {
        AndroidManager.getDriver().terminateApp(
                LocProperties.getProperties().getProperty("appPackage"));
        cleanUp();
        CommonPage.stopRecording();
        world.filelistPage.cleanUpDevice();
        Log.log(Level.FINE, "END SCENARIO EXECUTION: " + scenario.getName() + "\n\n");
    }

    private void cleanUp()
            throws IOException, ParserConfigurationException, SAXException {
        Log.log(Level.FINE, "-------------------------------");
        Log.log(Level.FINE, "STARTS: CLEAN UP AFTER SCENARIO");
        Log.log(Level.FINE, "-------------------------------");
        //First, remove leftovers in root folder for every user
        ArrayList<String> userNames = new ArrayList<>(Arrays.asList("admin"));
        for (String userToClean: userNames) {
            ArrayList<OCFile> filesRoot = world.filesAPI.listItems("", userToClean);
            for (OCFile iterator : filesRoot) {
                world.filesAPI.removeItem(iterator.getName(), userToClean);
            }
            //Empty trashbins
            world.trashbinAPI.emptyTrashbin(userToClean);
        }
    }
}
