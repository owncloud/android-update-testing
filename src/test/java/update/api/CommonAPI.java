/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.api;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.logging.Level;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import update.LocProperties;
import update.support.log.Log;
import update.support.network.oCHttpClient;
import update.support.parser.DrivesJSONHandler;

public class CommonAPI {

    protected OkHttpClient httpClient = oCHttpClient.getUnsafeOkHttpClient();

    protected String urlServer = System.getProperty("server");
    protected String userAgent = LocProperties.getProperties().getProperty("userAgent");
    protected String host = getHost();

    protected String user = System.getProperty("username");
    protected String password = System.getProperty("password");

    protected final String webdavEndpoint = "/remote.php/dav/files";
    protected final String spacesEndpoint = "/dav/spaces/";
    protected final String graphDrivesEndpoint = "/graph/v1.0/me/drives";
    protected String davEndpoint = "";
    boolean isOCIS = true;
    protected HashMap<String, String> personalSpaces;

    protected String basicPropfindBody = "<?xml version='1.0' encoding='UTF-8' ?>\n" +
            "<propfind xmlns=\"DAV:\" xmlns:CAL=\"urn:ietf:params:xml:ns:caldav\"" +
            " xmlns:CARD=\"urn:ietf:params:xml:ns:carddav\" " +
            " xmlns:SABRE=\"http://sabredav.org/ns\" " +
            " xmlns:OC=\"http://owncloud.org/ns\">\n" +
            "  <prop>\n" +
            "    <displayname />\n" +
            "    <getcontenttype />\n" +
            "    <resourcetype />\n" +
            "    <getcontentlength />\n" +
            "    <getlastmodified />\n" +
            "    <creationdate />\n" +
            "    <getetag />\n" +
            "    <quota-used-bytes />\n" +
            "    <quota-available-bytes />\n" +
            "    <OC:permissions />\n" +
            "    <OC:id />\n" +
            "    <OC:size />\n" +
            "    <OC:privatelink />\n" +
            "  </prop>\n" +
            "</propfind>";

    public CommonAPI() throws IOException {
        personalSpaces = new HashMap<>();
        personalSpaces.put(user, getPersonalDrives(urlServer, user));
    }

    public String getEndpoint(String userName) {
        String endpoint;
        if (isOCIS) {
            endpoint = spacesEndpoint + personalSpaces.get(userName);
        } else {
            endpoint = webdavEndpoint + "/" + user;
        }
        return endpoint;
    }

    public String getEndpoint() {
        if (isOCIS) {
            return spacesEndpoint + personalSpaces.get(user);
        } else {
            return webdavEndpoint + "/" + user;
        }
    }

    protected Request davRequest(String url, String method, RequestBody body, String userName) {
        Log.log(Level.FINE, "Starts: Request to DAV API: " + userName);
        return baseRequestBuilder(url, credentialsBuilder(userName))
                .method(method, body)
                .build();
    }

    protected Request deleteRequest(String url, String userName) {
        return baseRequestBuilder(url, credentialsBuilder(userName))
                .delete()
                .build();
    }

    protected Request getRequest(String url) {
        String credentials = Base64.getEncoder().encodeToString((user + ":" + password).getBytes());
        return baseRequestBuilder(url, credentials)
                .get()
                .build();
    }

    private Request.Builder baseRequestBuilder(String url, String credentials) {
        return new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic " + credentials)
                .addHeader("Host", host);
    }

    private String getHost() {
        Log.log(Level.FINE, "URL: " + urlServer);
        host = urlServer.split("//")[1];
        Log.log(Level.FINE, "HOST: " + host);
        return host;
    }

    private String getPersonalDrives(String url, String userName) throws IOException {
        Log.log(Level.FINE, "Starts: Call get personal ID: " + url);
        Request request = getRequest(url + graphDrivesEndpoint);
        Log.log(Level.FINE, "Request: " + request.toString());
        String body;
        try (Response response = httpClient.newCall(request).execute()) {
            body = response.body().string();
        }
        Log.log(Level.FINE, "Body from me endpoint: " + body);
        String personalId = DrivesJSONHandler.getPersonalDriveId(body);
        Log.log(Level.FINE, "Personal Drive ID: " + personalId);
        return personalId;
    }

    private String credentialsBuilder(String userName) {
        return Base64.getEncoder().encodeToString((userName.toLowerCase() + ":" + password).getBytes());
    }
}
