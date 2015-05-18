import java.util.Date;
import java.util.ArrayList;


/**
 * Created by katesercombe on 5/9/15.
 */
public class Adventure {

    ArrayList<StoryFrag> frags = new ArrayList<StoryFrag>();
    ArrayList<Occasion> occasions = new ArrayList<Occasion>();
    Date date = new Date();
    String story = "";

    public Adventure(ArrayList<StoryFrag> stories, ArrayList<Occasion> dayEvent, Date dayDate){
        frags = stories;
        occasions = dayEvent;
        date = dayDate;
    }
}
