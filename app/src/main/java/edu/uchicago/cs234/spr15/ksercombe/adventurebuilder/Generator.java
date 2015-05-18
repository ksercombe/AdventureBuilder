/**
 * Created by katesercombe on 5/14/15.
 */
package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import java.util.ArrayList;
import java.util.Random;

public class Generator {


public String ck_service(String service){

        if (service.contains(""))

}

public String ck_title(String title){
        if (title.contains(""))

}

public String ck_desc(String desc){

}

public ArrayList<String> tagOccasion(Occasion occ){
    ArrayList<String> tags = new ArrayList<String>();
    String tag;

    String service = occ.getService().toLowerCase();
    String title = occ.getTitle().toLowerCase();
    String desc = occ.getDescription().toLowerCase();

    if (service != null){
        tag = ck_service(service);
        tags.add(tag);

    }


    if (title != null){
        tag = ck_title(title);
        tags.add(tag);
    }


    if (desc != null){
        tag = ck_desc(desc);
        tags.add(tag);
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
                    replacer = getString(R.string.you);
                    break;
                case "[GUEST]":
                    ArrayList<String> guestList = currOcc.getGuests();
                    int len = guestList.size();
                    if (len > 3) {
                        replacer = res.getString(R.string.friends);
                    }
                    else{
                        for (int k = 0; k < len; k++) {
                            replacer.concat(guestList.get(k));
                        }
                    }
                    break;
                case "[TIME]":
                    replacer = currOcc.getDuration().toString();
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

