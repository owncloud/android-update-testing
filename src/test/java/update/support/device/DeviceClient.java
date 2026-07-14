package update.support.device;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import io.appium.java_client.android.AndroidDriver;
import update.support.log.Log;

public class DeviceClient {

    private final AndroidDriver driver;
    public DeviceClient(AndroidDriver driver) {
        this.driver = driver;
    }

    public void cleanUpDevice() {
        Log.log(Level.FINE, "Starts: Clean up device, owncloud folder");
        // Remove owncloud folder from device
        Map<String, Object> args = new HashMap<>();
        args.put("command", "rm");
        args.put("args", Arrays.asList("-rf", getDownloadsFolder() + "/*"));
        driver.executeScript("mobile: shell", args);
    }

    public String getDownloadsFolder() {
        Log.log(Level.FINE, "Starts: Get downloads folder");
        Map<String, Object> args = new HashMap<>();
        args.put("command", "ls");
        args.put("args", Arrays.asList("/sdcard"));
        String output = (String) driver.executeScript("mobile: shell", args);

        if (output.contains("Download")) {
            Log.log(Level.FINE, "/sdcard/Download");
            return "/sdcard/Download";
        } else if (output.contains("Downloads")) {
            Log.log(Level.FINE, "/sdcard/Downloads");
            return "/sdcard/Downloads";
        } else {
            return "";
        }
    }
    public String pullList(String folderId) {
        Log.log(Level.FINE, "Starts: pull file from: " + folderId);
        Map<String, Object> args = new HashMap<>();
        String user = System.getProperty("username");
        String owncloudFolder = getDownloadsFolder() + "/owncloud/";
        String server = System.getProperty("server")
                .replaceFirst("^https?://", "")
                .replace(":", "%3A" );
        String target = owncloudFolder + user + "@" + server  + "/" + folderId;
        Log.log(Level.FINE, "Command args to execute: " + target);
        args.put("command", "ls");
        args.put("args", List.of(target));

        String output = (String) driver.executeScript("mobile: shell", args);
        Log.log(Level.FINE, "List of files in given folder: " + output);
        return output;
    }
}
