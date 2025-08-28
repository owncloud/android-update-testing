/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package utils.api;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.logging.Level;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.LocProperties;
import utils.log.Log;
import utils.network.oCHttpClient;
import utils.parser.DrivesJSONHandler;

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
        personalSpaces.put("admin", getPersonalDrives(urlServer, "admin"));
    }

    public String getEndpoint(String userName) {
        String endpoint;
        if (isOCIS) {
            endpoint = spacesEndpoint + personalSpaces.get(userName);
        } else {
            endpoint = davEndpoint = webdavEndpoint + "/" + user;
        }
        return endpoint;
    }

    public String getEndpoint() {
        if (isOCIS) {
            return spacesEndpoint + personalSpaces.get(user);
        } else {
            return davEndpoint = webdavEndpoint + "/" + user;
        }
    }

    protected Request davRequest(String url, String method, RequestBody body, String userName) {
        Log.log(Level.FINE, "Starts: Request to DAV API: " + userName );
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic " + credentialsBuilder(userName))
                .addHeader("Host", host)
                .method(method, body)
                .build();
        return request;
    }

    protected Request deleteRequest(String url, String userName) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic " + credentialsBuilder(userName))
                .addHeader("Host", host)
                .delete()
                .build();
        return request;
    }

    protected Request getRequest(String url) {
        String credentialsB64 = Base64.getEncoder().encodeToString((user + ":" + password).getBytes());
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic " + credentialsB64)
                .addHeader("Host", host)
                .get()
                .build();
        return request;
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
        Response response = httpClient.newCall(request).execute();
        String body = response.body().string();
        response.close();
        String personalId = DrivesJSONHandler.getPersonalDriveId(body);
        Log.log(Level.FINE, "Personal Drive ID: " + personalId);
        return personalId;
    }

    private String credentialsBuilder(String userName) {
        return Base64.getEncoder().encodeToString((userName.toLowerCase() + ":" + password).getBytes());
    }
}
