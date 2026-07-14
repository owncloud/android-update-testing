/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.steps;

import java.io.IOException;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import update.support.log.StepLogger;
import update.world.World;

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
        world.fileListPreconditions().createItems(table.asLists());
    }

    @Given("app is installed")
    public void appIsInstalled() {
        StepLogger.logCurrentStep(Level.FINE);
    }

    @Given("passcode is set")
    public void passcodeIsSetTo() {
        StepLogger.logCurrentStep(Level.FINE);
        world.passcodePreconditions().setPasscode();
    }

    @When("log in")
    public void weLogin() {
        StepLogger.logCurrentStep(Level.FINE);
        world.loginTasks().login(serverURL, userName, password);
    }

    @When("list of files is displayed")
    public void listOfFilesIsDisplayed() {
        StepLogger.logCurrentStep(Level.FINE);
        // Just a control check
        world.fileListAssertions().isFileListVisible();
    }

    @When("{fileType} {word} is {word}")
    public void fileIsDownloaded(String type, String itemName, String operation) {
        StepLogger.logCurrentStep(Level.FINE);
        switch (operation) {
            case "downloaded" -> {
                if (type.equals("file")) {
                    world.fileListAssertions().isFileDownloaded(itemName);
                } else if (type.equals("folder")) {
                    world.fileListTasks().downloadFolder(itemName);
                }
            }
            case "av.offline" -> world.fileListTasks().setAvailableOffline(itemName);
        }
    }

    @When("app is reinstalled")
    public void appIsReinstalled() throws InterruptedException {
        StepLogger.logCurrentStep(Level.FINE);
        world.loginTasks().reinstallApp();
    }

    @Then("the following items should be displayed")
    public void theFollowingItemsShouldBeDisplayed(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().areItemsDisplayed(table.asLists());
    }

    @Then("the following files should be downloaded")
    public void theFollowingItemsShouldBeDownloaded(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().areFilesDownloaded(table.asLists());
    }

    @Then("the folder {word} should contain the following downloaded files")
    public void theFolderShouldContainTheFollowingFiles(String folder, DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListAssertions().areFilesDownloadedInFolder(folder, table.asLists());
    }

    @Then("the correct commit is displayed in Settings")
    public void theCorrectCommitIsDisplayedInSettings() {
        StepLogger.logCurrentStep(Level.FINE);
        world.settingsAssertions().isCommitCorrect();
    }

    @Then("passcode view is displayed")
    public void passcodeViewIsDisplayed() {
        StepLogger.logCurrentStep(Level.FINE);
        world.passcodeAssertions().isPasscodeViewDisplayed();
    }
}
