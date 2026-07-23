/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.assertions;

import static org.junit.Assert.assertTrue;

import update.LocProperties;
import update.world.World;

public class PasscodeAssertions {

    private World world;

    public PasscodeAssertions(World world) {
        this.world = world;
    }

    public void isPasscodeViewDisplayed() {
        assertTrue(world.passcodePage().isPasscodeVisible());
        String passcode = LocProperties.getProperties().getProperty("passcode");
        if (passcode == null || passcode.length() < 4) {
            throw new IllegalStateException("Property 'passcode' must be at least 4 characters, got: " + passcode);
        }
        world.passcodePage().enterPasscode(
            String.valueOf(passcode.charAt(0)),
            String.valueOf(passcode.charAt(1)),
            String.valueOf(passcode.charAt(2)),
            String.valueOf(passcode.charAt(3)));
    }
}
