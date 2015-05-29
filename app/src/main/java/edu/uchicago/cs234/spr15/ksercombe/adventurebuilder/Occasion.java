package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import android.location.Location;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by evan on 5/10/15.
 * Occasion type, can be used to represent events from Calendar, Facebook, and Eventbrite.
 */
public class Occasion {
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

//    public Occasion(String newTitle, String newDesc, String newService, Date start, Date end,
//                    ArrayList<String> newGuests, Location loc, ArrayList<String> newTags, int dur){
//        title = newTitle;
//        desc = newDesc;
//        service = newService;
//        startDate = start;
//        endDate = end;
//        guests = newGuests;
//        location = loc;
//        tags = newTags;
//        duration = dur;
//    }


    public DateTime getDate() { return start.localDatetime;}

    public long getDuration() { //Returns duration in minutes
        return TimeUnit.MILLISECONDS.toMinutes(end.localDatetime.getMillis() - start.localDatetime.getMillis());
    }

    public ArrayList<String> getGuests() {
        return guests;
    }

    public String getService() {
        return service;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return desc;
    }

    public Object getLocation() {
        return location;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> newtags) {
        tags = newtags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }
}
