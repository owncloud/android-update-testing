/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.preconditions;

import update.LocProperties;
import update.world.World;

public class PasscodePreconditions {

    private World world;

    public PasscodePreconditions(World world) {
        this.world = world;
    }

    public void setPasscode() {
        world.filelistPage().openPasscode();
        String passcode = LocProperties.getProperties().getProperty("passcode");
        // Passcode to be entered twice to confirm it
        world.passcodePage().enterPasscode(
            String.valueOf(passcode.charAt(0)),
            String.valueOf(passcode.charAt(1)),
            String.valueOf(passcode.charAt(2)),
            String.valueOf(passcode.charAt(3)));
        world.passcodePage().enterPasscode(
            String.valueOf(passcode.charAt(0)),
            String.valueOf(passcode.charAt(1)),
            String.valueOf(passcode.charAt(2)),
            String.valueOf(passcode.charAt(3)));
    }
}
