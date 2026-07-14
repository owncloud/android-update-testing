package update.world;

import java.io.IOException;

import io.appium.java_client.android.AndroidDriver;
import update.pages.AndroidManager;
import update.pages.FileListPage;
import update.pages.LoginPage;
import update.pages.PasscodePage;
import update.pages.SettingsPage;
import update.api.FilesAPI;
import update.api.GraphAPI;
import update.api.TrashbinAPI;
import update.assertions.FileListAssertions;
import update.assertions.PasscodeAssertions;
import update.assertions.SettingsAssertions;
import update.preconditions.FileListPreconditions;
import update.preconditions.PasscodePreconditions;
import update.support.device.DeviceClient;
import update.support.video.ScreenRecorder;
import update.tasks.FileListTasks;
import update.tasks.LoginTasks;

public class World {

    private final AndroidDriver driver;

    //Involved pages
    private LoginPage loginPage;
    private FileListPage filelistPage;
    private SettingsPage settingsPage;
    private PasscodePage passcodePage;
    private FilesAPI filesAPI;
    private GraphAPI graphAPI;
    private TrashbinAPI trashbinAPI;
    private FileListPreconditions fileListPreconditions;
    private PasscodePreconditions passcodePreconditions;
    private LoginTasks loginTasks;
    private FileListTasks fileListTasks;
    private FileListAssertions fileListAssertions;
    private SettingsAssertions settingsAssertions;
    private PasscodeAssertions passcodeAssertions;
    private ScreenRecorder screenRecorder;
    private DeviceClient deviceClient;

    public World() {
        this.driver = AndroidManager.getDriver();
    }

    public LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(driver);
        }
        return loginPage;
    }

    public FileListPage filelistPage() {
        if (filelistPage == null) {
            filelistPage = new FileListPage(driver);
        }
        return filelistPage;
    }

    public SettingsPage settingsPage() {
        if (settingsPage == null) {
            settingsPage = new SettingsPage(driver);
        }
        return settingsPage;
    }

    public PasscodePage passcodePage() {
        if (passcodePage == null) {
            passcodePage = new PasscodePage(driver);
        }
        return passcodePage;
    }

    public FilesAPI filesAPI() throws IOException {
        if (filesAPI == null) {
            filesAPI = new FilesAPI();
        }
        return filesAPI;
    }

    public GraphAPI graphAPI() throws IOException {
        if (graphAPI == null) {
            graphAPI = new GraphAPI();
        }
        return graphAPI;
    }

    public TrashbinAPI trashbinAPI() throws IOException {
        if (trashbinAPI == null) {
            trashbinAPI = new TrashbinAPI();
        }
        return trashbinAPI;
    }

    public FileListPreconditions fileListPreconditions() {
        if (fileListPreconditions == null) {
            fileListPreconditions = new FileListPreconditions(this);
        }
        return fileListPreconditions;
    }

    public PasscodePreconditions passcodePreconditions() {
        if (passcodePreconditions == null) {
            passcodePreconditions = new PasscodePreconditions(this);
        }
        return passcodePreconditions;
    }

    public LoginTasks loginTasks() {
        if (loginTasks == null) {
            loginTasks = new LoginTasks(this);
        }
        return loginTasks;
    }

    public FileListTasks fileListTasks() {
        if (fileListTasks == null) {
            fileListTasks = new FileListTasks(this);
        }
        return fileListTasks;
    }

    public FileListAssertions fileListAssertions() {
        if (fileListAssertions == null) {
            fileListAssertions = new FileListAssertions(this);
        }
        return fileListAssertions;
    }

    public SettingsAssertions settingsAssertions() {
        if (settingsAssertions == null) {
            settingsAssertions = new SettingsAssertions(this);
        }
        return settingsAssertions;
    }

    public PasscodeAssertions passcodeAssertions() {
        if (passcodeAssertions == null) {
            passcodeAssertions = new PasscodeAssertions(this);
        }
        return passcodeAssertions;
    }

    public ScreenRecorder screenRecorder() {
        if (screenRecorder == null) {
            screenRecorder = new ScreenRecorder(driver);
        }
        return screenRecorder;
    }

    public DeviceClient deviceClient() {
        if (deviceClient == null) {
            deviceClient = new DeviceClient(driver);
        }
        return deviceClient;
    }
}
