/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Level;

import okhttp3.Request;
import okhttp3.Response;
import update.model.OCSpace;
import update.support.log.Log;

public class GraphAPI extends CommonAPI{

    public GraphAPI() throws IOException {
    }

    public OCSpace getPersonal() throws IOException {
        Log.log(Level.FINE, "Get personal space");
        String graphPath = "/graph/v1.0/";
        String myDrives = "me/drives/";
        String url = urlServer + graphPath + myDrives;
        Request request = getRequest(url);
        String json;
        try (Response response = httpClient.newCall(request).execute()) {
            json = response.body().string();
        }
        OCSpace personal = new OCSpace();
        JSONObject obj = new JSONObject(json);
        JSONArray value = obj.getJSONArray("value");
        for (int i = 0; i < value.length(); i++) {
            JSONObject jsonObject = value.getJSONObject(i);
            String type = jsonObject.getString("driveType");
            if (type.equals("personal")) {
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
