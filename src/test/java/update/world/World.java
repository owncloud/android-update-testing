package update.world;

import java.io.IOException;

import io.appium.java_client.android.AndroidDriver;
import update.pages.AndroidManager;
import update.pages.FilelistPage;
import update.pages.LoginPage;
import update.pages.PasscodePage;
import update.pages.SettingsPage;
import update.api.FilesAPI;
import update.api.GraphAPI;
import update.api.TrashbinAPI;

public class World {

    private final AndroidDriver driver;

    //Involved pages
    private LoginPage loginPage;
    private FilelistPage filelistPage;
    private SettingsPage settingsPage;
    private PasscodePage passcodePage;
    private FilesAPI filesAPI;
    private GraphAPI graphAPI;
    private TrashbinAPI trashbinAPI;

    public World() {
        this.driver = AndroidManager.getDriver();
    }

    public LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(driver);
        }
        return loginPage;
    }

    public FilelistPage filelistPage() {
        if (filelistPage == null) {
            filelistPage = new FilelistPage(driver);
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
}
