/**
 * @author Jesús Recio Rincón (@jesmrec)
 */

package update.support.parser;

import org.json.JSONArray;
import org.json.JSONObject;

public class DrivesJSONHandler {

    public static String getPersonalDriveId(String json){
        JSONObject drivesObj = new JSONObject(json);
        JSONArray valuesArr = drivesObj.getJSONArray("value");
        for (int i = 0; i < valuesArr.length() ; i++){
            JSONObject drivesList = valuesArr.getJSONObject(i);
            if ("personal".equals(drivesList.optString("driveType"))){
                return drivesList.getString("id");
            }
        }
        throw new IllegalStateException("No personal drive found in Graph API response");
    }
}
