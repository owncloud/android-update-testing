package update.world;

import java.io.IOException;

import update.pages.FilelistPage;
import update.pages.LoginPage;
import update.pages.PasscodePage;
import update.pages.SettingsPage;
import update.api.FilesAPI;
import update.api.GraphAPI;
import update.api.TrashbinAPI;

public class World {

    //Involved pages
    public LoginPage loginPage = LoginPage.getInstance();
    public FilelistPage filelistPage = FilelistPage.getInstance();
    public SettingsPage settingsPage = SettingsPage.getInstance();
    public PasscodePage passcodePage = PasscodePage.getInstance();
    public FilesAPI filesAPI = FilesAPI.getInstance();
    public GraphAPI graphAPI = GraphAPI.getInstance();
    public TrashbinAPI trashbinAPI = TrashbinAPI.getInstance();

    public World() throws IOException {
    }
}
