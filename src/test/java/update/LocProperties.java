/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LocProperties {

    private static final Properties properties;

    static {
        Properties p = new Properties();
        try (FileInputStream inputStream = new FileInputStream("local.properties")) {
            p.load(inputStream);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Cannot load local.properties: " + e.getMessage());
        }
        properties = p;
    }

    private LocProperties() {}

    public static Properties getProperties() {
        return properties;
    }
}

