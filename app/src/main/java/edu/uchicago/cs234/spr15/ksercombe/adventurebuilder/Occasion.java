package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;
import java.util.Date;
import java.util.ArrayList;
import android.location.Location;


/**
 * Created by katesercombe on 5/9/15.
 */
public interface Occasion {
    public Date getDate();
    public long getDuration();

    public ArrayList<String> getGuests();
    public String getService();
    public String getTitle();
    public String getDescription();

    public Object getLocation();

    public ArrayList<String> getTags();
    public void setTags(ArrayList<String> newtags);
    public void addTag(String tag);

//  Remnant of old Occasion object
//    public Occasion (Date eventTime, String from, String name, String desc){
//        Date time = eventTime;
//        String source = from;
//        String title = name;
//        String description = desc;
//        ArrayList<String> tags ;
//        float distance;
//       	float[] locationBox = new float[4];
//        int duration;
//        float[] speed = new float[3];
//        float[] heartRate = new float[3];
//        ArrayList<String> names;
//        Location location;
//
//    }
}
