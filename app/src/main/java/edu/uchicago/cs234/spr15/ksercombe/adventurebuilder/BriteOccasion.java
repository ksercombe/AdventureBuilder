package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by evan on 5/28/15.
 */
public class BriteOccasion extends Occasion {
    public EventTime start; //datetime-tz object.
    public EventTime end; //datetime-tz object.
    int duration = 0;
    public ArrayList<String> guests;
    public static final String service = "eventbrite";
    public String name; //title
    public String description; //description from event page, may be very long.
    public String status; //Either cancelled, live, started, ended, completed.
    public boolean onlineEvent; //if true, online and no physical venue.
    public Venue venue;
    public ArrayList<String> tags;
}
