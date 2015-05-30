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
                StoryFrag x = new StoryFrag(s.next(), new ArrayList<String>(), 0, 0);
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
