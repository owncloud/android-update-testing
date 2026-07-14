/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.preconditions;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import update.support.log.Log;
import update.world.World;

public class FileListPreconditions {

    private World world;
    private final String userName = System.getProperty("username");

    public FileListPreconditions(World world) {
        this.world = world;
    }

    public void createItems(List<List<String>> listItems) throws IOException {
        for (List<String> rows : listItems) {
            String type = rows.get(0);
            String name = rows.get(1);
            Log.log(Level.FINE, type + " " + name);
            if (!world.filesAPI().itemExist(name)) {
                switch (type) {
                    case "folder", "item" -> world.filesAPI().createFolder(name, userName);
                    case "file" -> world.filesAPI().pushFile(name, userName);
                    case "image" -> world.filesAPI().pushFileByMime(name, "image/jpg");
                    case "audio" -> world.filesAPI().pushFileByMime(name, "audio/mpeg3");
                    case "video" -> world.filesAPI().pushFileByMime(name, "video/mp4");
                    case "shortcut" -> world.filesAPI().pushFileByMime(name, "text/uri-list");
                    case "damaged" -> world.filesAPI().pushFileByMime(name, "image/png");
                }
            }
        }
    }
}
