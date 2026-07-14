/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.assertions;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import update.support.log.Log;
import update.world.World;

public class FileListAssertions {

    private World world;

    public FileListAssertions(World world) {
        this.world = world;
    }

    public void isFileListVisible() {
        assertTrue(world.fileListTasks().isFileListVisible());
    }

    public void isFileDownloaded(String itemName) {
        world.fileListTasks().downloadFile(itemName);
        assertTrue(world.filelistPage().isItemPreviewed());
        world.filelistPage().backListFiles();
    }

    public void areItemsDisplayed(List<List<String>> listItems) {
        world.filelistPage().refreshList();
        for (List<String> rows : listItems) {
            String name = rows.get(0);
            Log.log(Level.FINE, "Checking " + name);
            assertTrue(world.filelistPage().isItemInList(name));
        }
    }

    public void areFilesDownloaded(List<List<String>> listItems) throws IOException {
        String folderId = world.graphAPI().getPersonal().getId().replace("$", "\\$");
        Log.log(Level.FINE, "Folder id: " + folderId);
        String listFiles = world.filelistPage().pullList(folderId);
        Log.log(Level.FINE, "Pulled list " + listFiles.replace("\n", " "));
        for (List<String> rows : listItems) {
            String itemName = rows.get(0);
            Log.log(Level.FINE, "Checking itemName: " + itemName);
            assertTrue(listFiles.contains(itemName));
        }
    }

    public void areFilesDownloadedInFolder(String folder, List<List<String>> listItems) throws IOException {
        String folderId = world.graphAPI().getPersonal().getId().replace("$", "\\$");
        Log.log(Level.FINE, "Folder id: " + folderId);
        String listFiles = world.filelistPage().pullList(folderId + "/" + folder);
        Log.log(Level.FINE, "Pulled list " + listFiles.replace("\n", " "));
        for (List<String> rows : listItems) {
            String itemName = rows.get(0);
            Log.log(Level.FINE, "Checking itemName: " + itemName);
            assertTrue(listFiles.contains(itemName));
        }
    }
}
