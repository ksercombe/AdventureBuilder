/**
 * Created by katesercombe on 5/14/15.
 */
package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import android.content.Context;
import android.os.Bundle;
import java.util.Objects;


public class Generator {
    Context context;

    public String ckTag(String title) {
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

    public Occasion tagOccasion(Occasion occ) {
        ArrayList<String> tags = new ArrayList<String>();
        String tag;
        String descTag;
        String service = occ.getService().toLowerCase();
        String title = occ.getTitle().toLowerCase();
        String desc = occ.getDescription().toLowerCase();

        tag = ckTag(title);
        descTag = ckTag(desc);

        if (service == null) {
            return occ;
        } else if (service.contains("googlefit")) {
            if (tag.equals("")) {
                if (descTag.equals("")) {
                    tags.add("exercise");
                } else {
                    tags.add(descTag);
                }
            } else {
                tags.add(tag);
            }
        } else if (service.contains("calendar")) {
            if (tag.equals("")) {
                if (descTag.equals("")) {
                    tags.add("meeting");
                } else {
                    tags.add(descTag);
                }
            } else {
                tags.add(tag);
            }
        } else if (service.contains("facebook")) {
            if (tag.equals("")) {
                if (descTag.equals("")) {
                    tags.add("party");
                } else {
                    tags.add(descTag);
                }
            } else {
                tags.add(tag);
            }
        } else if (service.contains("eventbrite")) {
            if (tag.equals("")) {
                if (descTag.equals("")) {
                    tags.add("lecture");
                } else {
                    tags.add(descTag);
                }
            } else {
                tags.add(tag);
            }
        } else if (service.contains("phonelog")) {
            tags.add("phone call");
        } else {

        }
        occ.tags = tags;
        return occ;
    }

    public StoryFrag fragMatch(Occasion occ, ArrayList<StoryFrag> allFrags) {
        Random rand = new Random();
        ArrayList<String> occTags = occ.getTags();
        ArrayList<StoryFrag> fragOptions = new ArrayList<StoryFrag>();
        int numOccTags = occTags.size();
        int numAllFrags = allFrags.size();
        for (int i = 0; i < numAllFrags; i++) {
            StoryFrag currFrag = allFrags.get(i);
            int numTags = currFrag.tags.size();
            for (int j = 0; j < numTags; j++) {
                for (int k = 0; k < numOccTags; k++) {
                    if (currFrag.tags.get(j).equals(occTags.get(k))) {
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

    public String storyTeller(ArrayList<StoryFrag> frags, ArrayList<Occasion> occasions, ArrayList<String> replaceStrings) {
        int numFrags = frags.size();
        int numReplace = replaceStrings.size();
        String replacer = "";
        String story = "";
        Random rand = new Random();
        ArrayList<String> movies = new ArrayList<String>();
        movies.add("Jackass");
        movies.add("Silence of the Lambs");
        movies.add("Hairspray");
        movies.add("Four Christmases");
        movies.add("Zoolander") ;
        movies.add("The Emperor's New Groove");
        movies.add("Bride and Prejudice");
        ArrayList<String> food = new ArrayList<String>();
        food.add("blueberries");
        food.add("marshmallows");
        food.add("chocolate covered crickets");
        food.add("peanut butter");
        food.add("chocolate chips");
        food.add("macaroni and cheese");
        food.add("puppy chow");
        for (int i = 0; i < numFrags; i++) {
            StoryFrag currFrag = frags.get(i);
            String actualFrag = currFrag.frag;
            Occasion currOcc = occasions.get(i);
            for (int j = 0; j < numReplace; j++) {
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
                    case "[DURATION]":
                        replacer = String.valueOf(currOcc.getDuration());
                        break;
                    case "[LOCATION]":
                        replacer = currOcc.getLocation().toString();
                        break;
                    case "[CALORIES]":
                        if (currOcc.calories != 0) {
                            replacer = Integer.toString(currOcc.calories * 1000);
                        }
                        else{
                            replacer = String.valueOf(rand.nextFloat() * 1000);
                        }
                        break;
                    case "[ENDTIME]":
                        replacer = new SimpleDateFormat("HH:mm").format(currOcc.endDate.getTime());
                        break;
                    case "[MOVIE]":
                        int lenMovies = movies.size();
                        int index = rand.nextInt(lenMovies);
                        replacer = movies.get(index);
                        break;
                    case "[FOOD]":
                        int lenFood = food.size();
                        int foodInd = rand.nextInt(lenFood);
                        replacer = food.get(foodInd);
                        break;
                    case "[EVENT NAME]":
                        replacer = currOcc.getTitle();
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

    public Adventure buildAdventure() {
        ArrayList<StoryFrag> stories = new ArrayList<StoryFrag>();

        ArrayList<Occasion> dayEvent = new ArrayList<Occasion>();
        /*BUILD OCCASION LIST HERE*/

        /*read in all frags from a file*/
        ArrayList<StoryFrag> allFrag = new ArrayList<StoryFrag>();
        String filename = "stories.txt";
        String line = null;

        try{
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            /* not sure how to fully initialize frags; what is sent fill and will
            n persons and location be provided in file or must we search

            while((line = bufferedReader.readLine()) != null){

                StoryFrag initFrag = new StoryFrag(line,);
                allFrag.add(initFrag);
            }
            */
            bufferedReader.close();
        }
        catch(FileNotFoundException ex){
            System.out.println("file not found\n");
        }
        catch (IOException ex){
            System.out.println("IO exception\n");
        }

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date dayDate = new Date();

        int eventSize = dayEvent.size();
        for (int i = 0; i < eventSize; i++){
            Occasion occ = dayEvent.get(i);
            occ = tagOccasion(occ);
            StoryFrag frag = fragMatch(occ,allFrag);
            stories.add(frag);
        }

        /*what is the replaceString array list? -- list of things we may have to replace in story
        * frags*/

        String[] replaceL = {"[GUEST]","[TIME]","[LOCATION]","[NAME]", "[CALORIES]", "[ENDTIME]", "[DURATION]", "[MOVIE]", "[FOOD]", "[EVENT NAME]"};

        ArrayList<String> replaceString = new ArrayList<String>();
        replaceString.addAll(Arrays.asList(replaceL));

        String storyWhole = storyTeller(stories,dayEvent,replaceString);
        /*return adv to be stored in database?*/

        Adventure adv = new Adventure(stories,dayEvent,dayDate);
        adv.story = storyWhole;

        return adv;

    }
}


