import android.location.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by evan on 5/10/15.
 * Calendar event occasion type, can be used to represent events from Calendar, Facebook, and Eventbrite.
 */
public class CalOccasion implements Occasion {
    Date startDate;
    Date endDate;
    int duration;
    ArrayList<String> guests;
    String service;
    String title;
    String desc:
    Location location;
    ArrayList<String> tags;


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
