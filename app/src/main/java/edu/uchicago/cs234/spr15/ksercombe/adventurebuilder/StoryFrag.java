/**
 * Created by katesercombe on 5/9/15.
 */
package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import java.util.ArrayList;

public class StoryFrag {
    String frag = "";
    ArrayList<String> tags = new ArrayList<String>();
    int numPpl = 0;
    int numLoc = 0;
    ArrayList<String> fillers = new ArrayList<String>();


    public StoryFrag(String sentFrag, ArrayList<String> actTags, int persons, int locations, ArrayList<String> sentFill ){
        frag = sentFrag;
        tags = actTags;
        numPpl = persons;
        numLoc = locations;
        fillers = sentFill;
    }



}
