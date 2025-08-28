package io.cucumber;

import java.io.IOException;

import io.android.FilelistPage;
import io.android.LoginPage;
import io.android.PasscodePage;
import io.android.SettingsPage;
import utils.api.FilesAPI;
import utils.api.GraphAPI;
import utils.api.TrashbinAPI;

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
