package io.cucumber;

import java.io.IOException;

import io.android.FilelistPage;
import io.android.LoginPage;
import utils.api.FilesAPI;
import utils.api.TrashbinAPI;

public class World {

    //Involved pages
    public LoginPage loginPage = LoginPage.getInstance();
    public FilesAPI filesAPI = FilesAPI.getInstance();
    public TrashbinAPI trashbinAPI = TrashbinAPI.getInstance();
    public FilelistPage filelistPage = FilelistPage.getInstance();

    public World() throws IOException {
    }
}
