/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.api;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import update.model.OCFile;
import update.support.log.Log;
import update.support.parser.FileSAXHandler;

public class FilesAPI extends CommonAPI {

    public FilesAPI() throws IOException {
        super();
    }

    public void removeItem(String itemName, String userName)
            throws IOException {
        String url = urlServer + getEndpoint(userName) + "/" + itemName;
        Log.log(Level.FINE, "Starts: Request remove item from server");
        Log.log(Level.FINE, "URL: " + url);
        Request request = deleteRequest(url, userName);
        try (Response response = httpClient.newCall(request).execute()) {
            // response body not needed
        }
    }

    public void createFolder(String folderName, String userName)
            throws IOException {
        String url = urlServer + getEndpoint(userName) + "/" + folderName + "/";
        Log.log(Level.FINE, "Starts: Request create folder: " + folderName + " for user: " + userName);
        Log.log(Level.FINE, "URL: " + url);
        Request request = davRequest(url, "MKCOL", null, userName);
        try (Response response = httpClient.newCall(request).execute()) {
            // response body not needed
        }
    }

    public void pushFile(String fileName, String userName)
            throws IOException {
        String url = urlServer + getEndpoint(userName) + "/" + fileName;
        Log.log(Level.FINE, "Starts: Request create file");
        Log.log(Level.FINE, "URL: " + url);
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), "textExample");
        Request request = davRequest(url, "PUT", body, userName);
        try (Response response = httpClient.newCall(request).execute()) {
            // response body not needed
        }
    }

    public void pushFileByMime(String itemName, String mimetype)
            throws IOException {
        String url = urlServer + getEndpoint() + "/" + itemName;
        File rootPath = new File(System.getProperty("user.dir"));
        Log.log(Level.FINE, "Starts: Request create file");
        Log.log(Level.FINE, "URL: " + url);
        File appDir = new File(rootPath, "src/test/resources");
        File content = new File(appDir, "io/cucumber/example-files/" + itemName);
        RequestBody body = RequestBody.create(MediaType.parse(mimetype), content);
        Request request = davRequest(url, "PUT", body, user);
        try (Response response = httpClient.newCall(request).execute()) {
            // response body not needed
        }
    }

    public boolean itemExist(String itemName)
            throws IOException {
        String url = urlServer + getEndpoint() + "/" + itemName;
        Log.log(Level.FINE, "Starts: Request check if item exists in server");
        Log.log(Level.FINE, "URL: " + url);
        Request request = davRequest(url, "PROPFIND", null, user);
        int responseCode;
        String responseMessage;
        try (Response response = httpClient.newCall(request).execute()) {
            responseCode = response.code();
            responseMessage = response.message();
        }
        switch (responseCode / 100) {
            case 2: {
                Log.log(Level.FINE, "Response " + responseCode + ". Item exists");
                return true;
            }
            case 4: {
                Log.log(Level.FINE, "Response " + responseCode + " "
                        + responseMessage + ". Item does not exist");
                return false;
            }
            default: {
                Log.log(Level.WARNING, "Response neither 4xx nor 2xx. Something went wrong");
                return false;
            }
        }
    }

    public ArrayList<OCFile> listItems(String path, String userName)
            throws IOException, SAXException, ParserConfigurationException {
        String url = urlServer + getEndpoint(userName) + "/" + path;
        Log.log(Level.FINE, "Starts: Request to fetch list of items from server");
        Log.log(Level.FINE, "URL: " + url);
        RequestBody body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"),
                basicPropfindBody);
        Request request = davRequest(url, "PROPFIND", body, userName);
        try (Response response = httpClient.newCall(request).execute()) {
            return getList(response);
        }
    }

    private ArrayList<OCFile> getList(Response httpResponse)
            throws IOException, SAXException, ParserConfigurationException {
        if (httpResponse.body() == null) {
            throw new IOException("Empty response body from PROPFIND request");
        }
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        FileSAXHandler handler = new FileSAXHandler();
        parser.parse(new InputSource(new StringReader(httpResponse.body().string())), handler);
        return handler.getListFiles();
    }
}
