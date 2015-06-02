package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Created by evan on 5/28/15.
 */
public class EventTime {
    public String timezone;
    public String utc;
    public String local;
    public DateTime localDatetime;


    public void convertTime(){
        DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();
        localDatetime = parser2.parseDateTime(local);
    }
}
