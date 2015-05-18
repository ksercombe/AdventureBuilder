/**
 * Created by katesercombe on 5/14/15.
 */
package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import java.util.ArrayList;
import java.util.Random;
import android.content.Context;
import java.util.Objects;


public class Generator {
    Context context;

public String ckTag(String title){
    if (title.contains("run"))
        return "run";
    else if (title.contains("exercise"))
        return "exercise";
    else if (title.contains("walk"))
        return "walk";
    else if (title.contains("meal"))
        return "meal";
    else if (title.contains("restaurant"))
        return "restaurant";
    else if (title.contains("study"))
        return "study";
    else if (title.contains("movie"))
        return "movie";
    else if (title.contains("home"))
        return "home";
    else if (title.contains("school"))
        return "school";
    else if (title.contains("class"))
        return "school";
    else if (title.contains("work"))
        return "work";
    else if (title.contains("lecture"))
        return "lecture";
    else if (title.contains("show"))
        return "show";
    else if (title.contains("party"))
        return "party";
    else if (title.contains("sports"))
        return "sports game";
    else if (title.contains("game"))
        return "sports game";
    else if (title.contains("museum"))
        return "museum";
    else if (title.contains("festival"))
        return "festival";
    else if (title.contains("library"))
        return "library";
    else if (title.contains("meeting"))
        return "meeting";
    else if (title.contains("holiday"))
        return "holiday";
    else if (title.contains("birthday"))
        return "birthday";
    else if (title.contains("phone"))
        return "phone call";
    else if (title.contains("night out"))
        return "night out";
    else
        return "";
}

public ArrayList<String> tagOccasion(Occasion occ){
    ArrayList<String> tags = new ArrayList<String>();
    String tag;
    String descTag;
    String service = occ.getService().toLowerCase();
    String title = occ.getTitle().toLowerCase();
    String desc = occ.getDescription().toLowerCase();

    tag = ckTag(title);
    descTag = ckTag(desc);

    if (service == null){
        return tags;
    }

    else if (service.contains("googlefit")){
        if (tag.equals("")){
            if (descTag.equals("")){
                tags.add("exercise");
            }
            else{
                tags.add(descTag);
            }
        }
        else{
            tags.add(tag);
        }
    }

    else if (service.contains("calendar")){
        if (tag.equals("")){
            if (descTag.equals("")){
                tags.add("meeting");
            }
            else{
                tags.add(descTag);
            }
        }
        else{
            tags.add(tag);
        }
    }

    else if (service.contains("facebook")){
        if (tag.equals("")){
            if (descTag.equals("")){
                tags.add("party");
            }
            else{
                tags.add(descTag);
            }
        }
        else{
            tags.add(tag);
        }
    }

    else if (service.contains("eventbrite")){
        if (tag.equals("")){
            if (descTag.equals("")){
                tags.add("lecture");
            }
            else{
                tags.add(descTag);
            }
        }
        else{
            tags.add(tag);
        }
    }

    else if (service.contains("phonelog")){
        tags.add("phone call");
    }

    else {

    }
    return tags;
}

public StoryFrag fragMatch(Occasion occ, ArrayList<StoryFrag> allFrags){
   Random rand = new Random();
   ArrayList<String> occTags = occ.getTags();
   ArrayList<StoryFrag> fragOptions = new ArrayList<StoryFrag>();
   int numOccTags = occTags.size();
   int numAllFrags = allFrags.size();
   for (int i = 0; i < numAllFrags; i++){
       StoryFrag currFrag = allFrags.get(i);
       int numTags = currFrag.tags.size();
       for(int j = 0; j < numTags; j++){
          for (int k = 0; k < numOccTags; k++){
              if (currFrag.tags.get(j).equals(occTags.get(k))){
                  fragOptions.add(currFrag);
                  continue;
              }
          }
       }
   }

   int numFragOptions = fragOptions.size();
   int chosen = rand.nextInt(numFragOptions);
   return fragOptions.get(chosen);


}

public String storyTeller(ArrayList<StoryFrag> frags, ArrayList<Occasion> occasions, ArrayList<String> replaceStrings){
    int numFrags = frags.size();
    int numReplace = replaceStrings.size();
    String replacer = "";
    String story = "" ;
    for (int i = 0; i < numFrags; i++){
        StoryFrag currFrag = frags.get(i);
        String actualFrag = currFrag.frag;
        Occasion currOcc = occasions.get(i);
        for(int j = 0; j< numReplace; j++){
            String currReplace = replaceStrings.get(j);
            replacer = "";
            switch(currReplace) {
                case "[NAME]":
                    replacer = context.getString(R.string.you);
                    break;
                case "[GUEST]":
                    ArrayList<String> guestList = currOcc.getGuests();
                    int len = guestList.size();
                    if (len > 3) {
                        replacer = context.getString(R.string.friends);
                    }
                    else{
                        for (int k = 0; k < len; k++) {
                            replacer.concat(guestList.get(k));
                        }
                    }
                    break;
                case "[TIME]":
                    replacer = String.valueOf(currOcc.getDuration());
                    break;
                case "[LOCATION]":
                    replacer = currOcc.getLocation().toString();
                    break;
                default:
                    break;
            }
            actualFrag.replaceAll(currReplace, replacer);
        }
        story.concat(actualFrag);
    }
    return story;
    }

}


