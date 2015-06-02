package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import android.database.Cursor;
import android.location.Location;
import android.provider.CallLog;

import java.util.ArrayList;

/**
 * Created by Kaavya Balan on 5/31/2015.
 */
public class CallOccasion extends Occasion {
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

    public CallOccasion(Cursor managedCursor) {
        guests = new ArrayList<String>();
        guests.add(managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
        duration = managedCursor.getInt(managedCursor.getColumnIndex(CallLog.Calls.DURATION));
        service = "phonelog";
        title = "call";
        desc = managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.NUMBER));
        calories = 0;

    }
}
