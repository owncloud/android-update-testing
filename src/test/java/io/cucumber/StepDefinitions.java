/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.LocProperties;
import utils.log.Log;
import utils.log.StepLogger;

public class StepDefinitions {

    private World world;
    private final String serverURL = System.getProperty("server");
    private final String userName = System.getProperty("username");
    private final String password = System.getProperty("password");

    @ParameterType("file|folder")
    public String fileType(String type) {
        return type;
    }

    public StepDefinitions(World world) {
        this.world = world;
    }

    @Given("the following items have been created in the account")
    public void theFollowingItemsHaveBeenCreatedInTheAccount(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String type = rows.get(0);
            String name = rows.get(1);
            Log.log(Level.FINE, type + " " + name);
            if (!world.filesAPI.itemExist(name)) {
                switch (type) {
                    case "folder", "item" -> world.filesAPI.createFolder(name, userName);
                    case "file" -> world.filesAPI.pushFile(name, userName);
                    case "image" -> world.filesAPI.pushFileByMime(name, "image/jpg");
                    case "audio" -> world.filesAPI.pushFileByMime(name, "audio/mpeg3");
                    case "video" -> world.filesAPI.pushFileByMime(name, "video/mp4");
                    case "shortcut" -> world.filesAPI.pushFileByMime(name, "text/uri-list");
                    case "damaged" -> world.filesAPI.pushFileByMime(name, "image/png");
                }
            }
        }
    }

    @Given("app is installed")
    public void appIsInstalled() {
        StepLogger.logCurrentStep(Level.FINE);
    }

    @Given("the following items were created in the account")
    public void theFollowingItemsWereCreatedInTheAccount() {
        StepLogger.logCurrentStep(Level.FINE);
    }

    @Given("passcode is set")
    public void passcodeIsSetTo() {
        StepLogger.logCurrentStep(Level.FINE);
        world.filelistPage.openPasscode();
        String passcode = LocProperties.getProperties().getProperty("passcode");
        enterPasscode(passcode);
        // Repetition
        enterPasscode(passcode);
    }

    @When("log in")
    public void weLogin() {
        StepLogger.logCurrentStep(Level.FINE);
        world.loginPage.typeURL(serverURL);
        world.loginPage.typeCredentials(userName, password);
        world.loginPage.submitLogin();
    }

    @When("list of files is displayed")
    public void listOfFilesIsDisplayed() {
        StepLogger.logCurrentStep(Level.FINE);
        // Just a control check
        assertTrue(world.filelistPage.isViewVisible());
    }

    @When("{fileType} {word} is {word}")
    public void fileIsDownloaded(String type, String itemName, String operation) {
        StepLogger.logCurrentStep(Level.FINE);
        switch (operation) {
            case "downloaded" -> {
                if (type.equals("file")) {
                    world.filelistPage.download(itemName);
                    assertTrue(world.filelistPage.isItemPreviewed());
                    world.filelistPage.backListFiles();
                } else if (type.equals("folder")) { //sync
                    world.filelistPage.longPress(itemName);
                    world.filelistPage.openMenuActions("Download");
                    world.filelistPage.closeSelectionMode();
                }
            }
            case "av.offline" -> {
                world.filelistPage.longPress(itemName);
                world.filelistPage.openMenuActions("Set as available offline");
                world.filelistPage.closeSelectionMode();
            }
        }
    }

    @When("app is reinstalled")
    public void appIsReinstalled() {
        StepLogger.logCurrentStep(Level.FINE);
        world.loginPage.reinstall();
    }

    @Then("the following items should be displayed")
    public void theFollowingItemsShouldBeDisplayed(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        world.filelistPage.refreshList();
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String name = rows.get(0);
            Log.log(Level.FINE, "Checking " + name);
            assertTrue(world.filelistPage.isItemInList(name));
        }
    }

    @Then("the following files should be downloaded")
    public void theFollowingItemsShouldBeDownloaded(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        String folderId = world.graphAPI.getPersonal().getId().replace("$", "\\$");
        Log.log(Level.FINE, "Folder id: " + folderId);
        String listFiles = world.filelistPage.pullList(folderId);
        Log.log(Level.FINE, "Pulled list " + listFiles.replace("\n", " "));
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String itemName = rows.get(0);
            Log.log(Level.FINE, "Checking itemName: " + itemName);
            assertTrue(listFiles.contains(itemName));
        }
    }

    @Then("the folder {word} should contain the following downloaded files")
    public void theFolderShouldContainTheFollowingFiles(String folder, DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        String folderId = world.graphAPI.getPersonal().getId().replace("$", "\\$");
        Log.log(Level.FINE, "Folder id: " + folderId);
        String listFiles = world.filelistPage.pullList(folderId + "/" + folder);
        Log.log(Level.FINE, "Pulled list " + listFiles.replace("\n", " "));
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String itemName = rows.get(0);
            Log.log(Level.FINE, "Checking itemName: " + itemName);
            assertTrue(listFiles.contains(itemName));
        }
    }

    @Then("the correct commit is displayed in Settings")
    public void theCorrectCommitIsDisplayedInSettings() {
        StepLogger.logCurrentStep(Level.FINE);
        world.filelistPage.openSettings();
        String commit = System.getProperty("commit");
        Log.log(Level.FINE, "Checking commit: " + commit);
        assertTrue(world.settingsPage.isCommitCorrect(commit));
    }

    @Then("passcode view is displayed")
    public void passcodeViewIsDisplayed() {
        StepLogger.logCurrentStep(Level.FINE);
        assertTrue(world.passcodePage.isPasscodeVisible());
        String passcode = LocProperties.getProperties().getProperty("passcode");
        enterPasscode(passcode);
    }

    private void enterPasscode(String passcode){
        world.passcodePage.enterPasscode(
            String.valueOf(passcode.charAt(0)),
            String.valueOf(passcode.charAt(1)),
            String.valueOf(passcode.charAt(2)),
            String.valueOf(passcode.charAt(3)));
    }
}
