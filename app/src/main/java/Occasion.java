import java.util.Date;
import java.util.ArrayList;
import android.location.Location;


/**
 * Created by katesercombe on 5/9/15.
 */
public class Occasion {
    public Occasion (Date eventTime, String from, String name, String desc){
        Date time = eventTime;
        String source = from;
        String title = name;
        String description = desc;
        ArrayList<String> tags ;
        float distance;
       	float[] locationBox = new float[4];
        int duration;
        float[] speed = new float[3];
        float[] heartRate = new float[3];
        ArrayList<String> names;
        Location location;

    }
}
