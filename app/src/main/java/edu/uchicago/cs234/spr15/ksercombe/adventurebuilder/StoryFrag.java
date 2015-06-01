/**
 * Created by katesercombe on 5/9/15.
 */
package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;


public class StoryFrag {
    String frag = "";
    ArrayList<String> tags = new ArrayList<String>();
    int numPpl = 0;
    int numLoc = 0;


    public StoryFrag(String sentFrag, ArrayList<String> actTags, int persons, int locations ){
        frag = sentFrag;
        tags = actTags;
        numPpl = persons;
        numLoc = locations;
    }

    public static ArrayList<StoryFrag> getAllFrags(){
        ArrayList<StoryFrag> allFrags = new ArrayList<StoryFrag>();
        try {
            Scanner s = new Scanner(new File("storyFrags.txt"));

            while (s.hasNextLine()) {
                String[] line = s.next().split("&");
                ArrayList<String> tag = new ArrayList<String>();
                tag.add(line[1].toLowerCase());
                String people = line[2].replaceAll(" \n\r","");
                String location = line[3].replaceAll(" \n\r","");
                StoryFrag x = new StoryFrag(line[0], tag, Integer.parseInt(people), Integer.parseInt(location));
                allFrags.add(x);
            }
            s.close();
        }
        catch(Exception e){
            //Send Error Message
        }
        return allFrags;
    }


}
