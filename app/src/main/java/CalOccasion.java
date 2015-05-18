import android.location.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by evan on 5/10/15.
 * Calendar event occasion type, can be used to represent events from Calendar, Facebook, and Eventbrite.
 */
public class CalOccasion implements Occasion {
    Date startDate = new Date();
    Date endDate = new Date();
    int duration = 0;
    ArrayList<String> guests = new ArrayList<String>();
    String service;
    String title;
    String desc;
    Location location = new Location();
    ArrayList<String> tags = new ArrayList<String>();


    @Override
    public Date getDate() {
        return startDate;
    }

    @Override
    public long getDuration() { //Returns duration in minutes
        return TimeUnit.MILLISECONDS.toMinutes(endDate.getTime() - startDate.getTime());
    }

    @Override
    public ArrayList<String> getGuests() {
        return guests;
    }

    @Override
    public String getService() {
        return service;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public Object getLocation() {
        return location;
    }

    @Override
    public ArrayList<String> getTags() {
        return tags;
    }

    @Override
    public void setTags(ArrayList<String> newtags) {
        tags = newtags;
    }

    @Override
    public void addTag(String tag) {
        tags.add(tag);
    }
}
