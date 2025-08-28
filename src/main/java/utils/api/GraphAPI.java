/**
 * @author Jesús Recio Rincón (@jesmrec)
 */
package utils.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Level;

import okhttp3.Request;
import okhttp3.Response;
import utils.entities.OCSpace;
import utils.log.Log;

public class GraphAPI extends CommonAPI{

    public static GraphAPI instance;

    private GraphAPI() throws IOException {
    }

    public static GraphAPI getInstance() throws IOException {
        if (instance == null) {
            instance = new GraphAPI();
        }
        return instance;
    }

    public OCSpace getPersonal() throws IOException {
        Log.log(Level.FINE, "Get personal space");
        String graphPath = "/graph/v1.0/";
        String myDrives = "me/drives/";
        String url = urlServer + graphPath + myDrives;
        Request request = getRequest(url);
        Response response = httpClient.newCall(request).execute();
        String json = response.body().string();
        OCSpace personal = new OCSpace();
        JSONObject obj = new JSONObject(json);
        JSONArray value = obj.getJSONArray("value");
        for (int i = 0; i < value.length(); i++) {
            JSONObject jsonObject = value.getJSONObject(i);
            String type = jsonObject.getString("driveType");
            if (type.equals("personal")) { //Just for user created spaces
                personal.setType(jsonObject.getString("driveType"));
                personal.setId(jsonObject.getString("id"));
                personal.setName(jsonObject.getString("name"));
                Log.log(Level.FINE, "Space id returned: " +
                        personal.getId() + " " + personal.getName());
            }
        }
        return personal;
    }
}
