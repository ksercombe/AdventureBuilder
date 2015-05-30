package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import android.location.Location;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONException;
import org.joda.time.DateTime;

/**
 * Created by katesercombe on 5/30/15.
 */
public class FBOccasion extends Occasion{
    EventTime start;
    EventTime end;
    int duration = 0;
    ArrayList<String> guests;
    String service;
    String title;
    String desc;
    Location location;
    int calories;
    ArrayList<String> tags;

    public FBOccasion(JSONObject o){
       try {
           start.localDatetime = (DateTime)o.get("start_time");
           end.localDatetime = (DateTime)o.get("end_time");
           start.timezone = o.getString("timezone");
           end.timezone = o.getString("timezone");
           duration = (int) (end.localDatetime.getMillis() - start.localDatetime.getMillis());
           service = "Facebook";
           title = o.getString("name");
           desc = o.getString("description");
           JSONObject p = o.getJSONObject("place");
           location = (Location)p.get("location");
           calories = 0;
       }
       catch (JSONException m){
           //do something
       }

    }

}
