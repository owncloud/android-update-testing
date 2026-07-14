/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.tasks;

import update.world.World;

public class FileListTasks {

    private World world;

    public FileListTasks(World world) {
        this.world = world;
    }

    public void downloadFile(String itemName) {
        world.filelistPage().download(itemName);
    }

    public void downloadFolder(String itemName) {
        world.filelistPage().longPress(itemName);
        world.filelistPage().openMenuActions("Download");
        world.filelistPage().closeSelectionMode();
    }

    public void setAvailableOffline(String itemName) {
        world.filelistPage().longPress(itemName);
        world.filelistPage().openMenuActions("Set as available offline");
        world.filelistPage().closeSelectionMode();
    }

    // Not regular task, but a helper method to check if the file list is visible
    public boolean isFileListVisible() {
        return world.filelistPage().isViewVisible();
    }
}
