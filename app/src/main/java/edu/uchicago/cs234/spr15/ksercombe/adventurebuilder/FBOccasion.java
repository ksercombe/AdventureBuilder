package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import android.location.Location;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONException;
import org.joda.time.DateTime;

import android.util.Log;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
        DateTimeFormatter formatter;

       try {
           Log.i("FB: ", "Date: " + o.get("start_time"));
           String sTime = o.getString("start_time");
           String eTime = o.getString("end_time");
           if(o.getBoolean("is_date_only")) {
               formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

               start.localDatetime = formatter.parseDateTime(sTime);
               end.localDatetime = formatter.parseDateTime(eTime);
           }
           else{
               formatter = DateTimeFormat.forPattern("yyyy-MM-dd'HH:mm:ssZ");
               start.localDatetime = formatter.parseDateTime(sTime);
               end.localDatetime = formatter.parseDateTime(eTime);

           }
           start.timezone = o.getString("timezone");
           end.timezone = o.getString("timezone");
           Log.i("FB: ", "timezone: " + end.timezone);
           duration = (int) (end.localDatetime.getMillis() - start.localDatetime.getMillis());
           service = "Facebook";
           title = o.getString("name");
           desc = o.getString("description");
           JSONObject p = o.getJSONObject("place");
           location = (Location)p.get("location");
           calories = 0;
       }
       catch (JSONException m){
           Log.e("FB: ", "Error");
           //do something
       }

    }

}
