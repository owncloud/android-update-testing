/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.assertions;

import static org.junit.Assert.assertTrue;

import java.util.logging.Level;

import update.support.log.Log;
import update.world.World;

public class SettingsAssertions {

    private World world;

    public SettingsAssertions(World world) {
        this.world = world;
    }

    public void isCommitCorrect() {
        world.filelistPage().openSettings();
        String commit = System.getProperty("commit");
        Log.log(Level.FINE, "Checking commit: " + commit);
        assertTrue(world.settingsPage().isCommitCorrect(commit));
    }
}
