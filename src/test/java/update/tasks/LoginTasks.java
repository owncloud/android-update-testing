/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.tasks;

import update.world.World;

public class LoginTasks {

    private World world;

    public LoginTasks(World world) {
        this.world = world;
    }

    public void login(String serverURL, String userName, String password) {
        world.loginPage().typeURL(serverURL);
        world.loginPage().typeCredentials(userName, password);
        world.loginPage().submitLogin();
    }

    public void reinstallApp() throws InterruptedException {
        world.loginPage().reinstall();
    }
}
