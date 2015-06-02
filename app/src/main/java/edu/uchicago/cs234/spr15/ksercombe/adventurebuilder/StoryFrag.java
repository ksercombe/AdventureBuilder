/**
 * Created by katesercombe on 5/9/15.
 */
package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.regex.Pattern;


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
        String[] hardCode = new String[] {"[NAME] and [GUEST] found a secret door in a carousel, leading [NAME] to explore tunnels under the fair grounds for [DURATION].&Festival&1&0", "After engorging [NAME] on cotton candy, [NAME] dressed up as a ghost clown and proceeded to terrify guests at [LOCATION].&Festival&0&1", "[NAME] and [GUEST] decided to go to [LOCATION] and lie on the ground with [NAME]’s eyes closed and wearing  noise-cancelling headphones to protest festival culture.&Festival&1&1", "[NAME] was innocently looking for a book in the stacks of  [LOCATION], when all of a sudden, the shelves tipped over, trapping [NAME] (unharmed) underneath, until a friendly librarian came to dig [NAME] out [DURATION] later.&Library&0&1", "After [DURATION] of research, [NAME] and [GUEST] discovered the secret to eternal life is eating a slice of watermelon everyday.&Library&1&0", "Instead of studying, [NAME] and [GUEST] brought nerf guns to [LOCATION] and proceeded to have a nerf gun fight in the stacks.&Library&1&1", "At [LOCATION], [NAME] and [GUEST] created the newest plan for world peace and submitted it to Congress.&Meeting&1&1", "After a [DURATION] discussion, [NAME] and [GUEST] determined that the color red is a social construct and while henceforth ban refuse to acknowledge it.&Meeting&1&0", "[NAME] and [GUEST] battled for [DURATION] minutes before coming to the conclusion that maybe orange really is the new black.&Meeting&1&0", "After an uneventful dinner, [NAME] family proceeded to play the annual present dodgeball game.  Winner is whomever’s present is still intact after the game!&Holiday&0&0", "[NAME] and [GUEST] chose to do a couple costume for Halloween this year, except they mixed up which dynamic duo they were.  [NAME] dressed as Cher; [GUEST] dressed as Robin.&Holiday&1&0"," [NAME] forgot the mashed potatoes for Thanksgiving.  Instead, [NAME] ripped up the couches and tried to pass off the couch stuffing for potatoes; no one even noticed the difference.&Holiday&0&0", "Congrats on being another year older!  To celebrate, [NAME] performed [NAME]’s well-practiced dance routine to Whitney Houston’s I Wanna Dance with Somebody on the subway on the way to work.&Birthday&0&0", "[NAME]’s co-workers surprised [NAME] on [NAME]’s special day by doing all [NAME]’s work for [NAME], which allowed [NAME] to watch Netflix all day.&Birthday&0&0", "[NAME] woke up this morning and realized everything [NAME] wished for was coming true.  Probably because it’s [NAME]’s birthday.&Birthday&0&0", "After a [DURATION] conversation, [GUEST] broke the news that they were inviting [NAME] on their Free Cruise that they won in a raffle.&Phone Call&1&0", "[GUEST] called [NAME] today screaming, 'Give me the codes! Give me the codes!' and then hung up.  [NAME] still don’t know what that was about.&Phone Call&1&0", "After realizing [NAME] were nominated for an Oscar this morning, [NAME] immediately called [GUEST], who, although upset that they were not nominated, was very gracious in their congratulations.&Phone Call&1&0", "[NAME] and [GUEST] walked to [LOCATION] solely for the purpose of listening to the song 'Blank Space' by Taylor Swift to resolve a lyric dispute.&Night Out&1&1", "After getting dressed up as clowns, [NAME] and [GUEST] went to [LOCATION] to make balloon animals and pass them out to passing drunk people.&Night Out&1&1", "After hopping into the DJ booth at [LOCATION] and playing some of [NAME] music, Kanye West, who just happened to be there that night, told [NAME] he wanted to jump start [NAME]’s career.&Night Out&0&0", "[NAME] went to school even though it was kind of a drag. [NAME] fell asleep in History of Magic, [NAME]'s potion nearly exploded, and something tried to eat [NAME] in Care of Magical Creatures. Again. Hogwarts just isn't what it used to be, right?&School&0&0", "[NAME] went to the class of world renowned professor Kanye West, after which, when someone asked where [NAME] learned calculus, java, and modernist culinary techniques [NAME] replied 'Yeezy taught me'.&School&0&0", "[NAME] and [GUEST] decided to surprise [NAME]'s class and teacher with cupcakes! But, little did they know, the cupcakes were topped with A-inducing frosting so the teacher had no choice but to give [NAME] all A's.&School&1&0", "At work, [NAME]'s boss experiments by replacing [NAME] with a monkey for the day. Instead of doing [NAME]'s normal work, all [NAME] did is feed the monkey bananas. [NAME] thought it was awesome.&Work&0&0", "Today, [NAME] went to work as a consultant to Game of Thrones. [NAME] researched the accuracy of the portrayal of dragons, the strength of the houses, and the compatibility of the show with the book.&Work&0&0", "While at work, [NAME] didn't get much actual work done. Instead [NAME] perfected the lost art of pickpocketing so now [NAME] can quit [NAME]'s job.&Work&0&0", "After [NAME]'s normal school and work [NAME] decided more education was really necessary instead of watching Netflix and attended lecture by [EVENT NAME].&Lecture&0&0", "[NAME] attended a lecture where Nicholas Cage is guiding an intense, focused dissection of his career and the artistic relevance of the wicker man. To foster open discussion, everyone wore a Nicolas Cage paper mask and spoke only in the 3rd person, including Nicolas Cage.&Lecture&0&0", "[NAME]  and [GUEST] protested the [EVENT NAME] by standing outside the door trying to scare people away by acting like a hungry dinosaur.&Lecture&1&0", "[NAME] and [GUEST] attended [EVENT NAME] in [LOCATION]. [NAME] may have thought [NAME] saw some really cool Shakespearian drama or contemporary show but in reality, [NAME] saw the timeless classic--Cats. No this is not a joke, there is one true show and [NAME] saw it today. PS  it's Cats.&Show&1&0", "[NAME] attended a concert by [EVENT NAME] where [NAME] crowd surfed up to the stage.&Show&0&0", "[NAME] and [GUEST] attended a party at Chuck E. Cheese's [NAME] played arcade games, ate pizza, and sang happy birthday to Jimmy. They're still not quite sure who Jimmy is.&Party&1&0", "[NAME] dressed up in a banana costume to attend [EVENT NAME] where [NAME] met up with an apple and grape bunch to form the infamous party entertainment group--fruit salad.&Party&0&0", "[NAME] and [GUEST] attend [EVENT NAME] but get bored halfway through and left to drink milkshakes and finger paint.&Party&1&0", "[NAME] and [GUEST] headed over to Soldier Field to watch a Bears Game and the most ridiculous, absurd thing happened. They won.&Sports game&1&0"," [NAME] got mistaken for a professional ping pong player and was invited to a tournament. [NAME] did manage to win a couple points, but committed so many fouls that [NAME] was banned from professional ping pong for life.&Sports game&0&0", "[NAME] dressed up as the mascot at the [EVENT NAME] and danced during the halftime show. [NAME] was so proud of the performance that [NAME] kept the costume and wore it for the next week.&Sports game&0&0", "[NAME] was innocently looking at some paintings and statues when [NAME] witnessed some ski-masked individuals trying to steal a painting! In a daring, heroic move [NAME] grabbed a statue and threw it at the thieves but [NAME] missed them and the statue broke! The museum was so angry about the lost painting and statue that they kicked [NAME] out.&Museum&0&0", "[NAME] and [GUEST] painted themselves white and joined the marble statue gallery for [TIME]$Museum&1&0", "[NAME] and an artist got into an arduous argument about whether watermelons or peaches are the pinnacle fruit form of art at [Location].&Museum&1&0", "At [TIME], [NAME] tried to spray paint themselves silver to be a dancing robot on the street. A nearby honey badger was irritated by [NAME]’s wanton spraying and chased [NAME] for [DISTANCE]. [NAME] got a pretty good workout out of the experience, but the badger’s location is currently unknown.&Exercise&0&0", "While patrolling the streets, [NAME] noticed the Bat-Signal in the sky and sprung into action. Not having a Bat-Claw handy, [NAME] had to run [DISTANCE] before realizing it was just a normal spotlight. Also, that [NAME] isn’t Batman.&Exercise&0&0", "[NAME] decided to take a nice long drive, and got a good [DISTANCE] before realizing they forgot the car.&Exercise&0&0", "Feeling particularly psychopathic, [NAME] burned [CALORIES] calories alive with a flamethrower. Because that is how calories work.&Exercise&0&0", "At [TIME], [NAME] grabbed a bite to eat at [LOCATION]. No, not that [LOCATION], [NAME] opened up a competing restaurant nearby with the exact same name but much, much better food and higher ethical/culinary/sanitary/beauty/comedic/ artistic/sanitary/olfactory/acoustic standards. Don’t bother looking it up, there was a huge legal battle and even though [NAME] totally could have won out, they decided it just wasn’t worth the hassle, you know?&Meal&0&2", "[NAME] definitely beat [GUEST] in a [FOOD]-eating contest at [LOCATION]. Don’t ask anyone there, [GUEST] is really embarrassed about it and they’d probably lie to make [GUEST] feel better.&Meal&3&1", "At [TIME], [NAME] and [GUEST] discovered that [LOCATION] was not the kind of place where you can smash a plate and yell “Opa!” That’s the tenth time this month.&Meal&1&1", "Armed with two friends, a Proton Pack, and a thirst for adventure, [NAME] spent [DURATION] destroying most of the second floor of [LOCATION] in an attempt to get their paranormal situation under control.&Study&0&1", "[NAME] and [GUEST] spent [DURATION] combing through the forbidden section of [LOCATION] looking for anything that could release [NAME] from that ancient hex.&Study&1&1", "After [DURATION], [NAME] has obtained squatter’s rights at [LOCATION]. No congratulations warranted, but a shower couldn’t hurt.&Study&0&1"," [NAME] watched [MOVIE], and can totally send you the link.&Movie&0&1, [NAME] and [GUEST] saw a movie at [LOCATION]. The film: not bad. [NAME]’s fake-yawn arm-move: on point.&Study&1&1"," At [ENDTIME], [NAME] spent a good 15 minutes waiting in line for the bathroom at [LOCATION] after the movie ended.&Study&0&1, [NAME] spent [DURATION] repacking the bugout bag, just in case.&Home&0&0", "[NAME] played peek-a-boo with all the mice in the house, just to keep them on their toes.&Home&0&0, [NAME] did something super exciting outside of the house and it was super fun and eventful and real and totally happened.&Home&0&0"};
        ArrayList<StoryFrag> allFrags = new ArrayList<StoryFrag>();

        for (int i =0; i< hardCode.length;i++){
            String[] line = hardCode[i].split(Pattern.quote("&"));
            //Log.i("ADD STORY: ", hardCode[i]);
            //Log.i("ADD STORY: ", line[0]);
            //Log.i("ADD STORY: ", line[1]);
            //Log.i("ADD STORY: ", line[2]);
            ArrayList<String> tag = new ArrayList<String>();
            tag.add(line[1].toLowerCase());
            String people = line[2].replaceAll(" \n\r","");
            //String location = line[3].replaceAll(" \n\r","");
            StoryFrag x = new StoryFrag(line[0], tag, Integer.parseInt(people), 0);
            //StoryFrag x = new StoryFrag(line[0], tag, Integer.parseInt(people), Integer.parseInt(location));
            allFrags.add(x);

        }
        return allFrags;

        /*ArrayList<StoryFrag> allFrags = new ArrayList<StoryFrag>();
        try {
            Scanner s = new Scanner(new File("storyFrags"));
            Log.i("FragMatch: ", "SCANNING NOW");
            while (s.hasNextLine()) {
                String[] line = s.next().split(Pattern.quote("&"));
                ArrayList<String> tag = new ArrayList<String>();
                tag.add(line[1].toLowerCase());
                String people = line[2].replaceAll(" \n\r","");
                String location = line[3].replaceAll(" \n\r","");
                StoryFrag x = new StoryFrag(line[0], tag, Integer.parseInt(people), Integer.parseInt(location));
                allFrags.add(x);
            }
            s.close();
        }
        catch(FileNotFoundException ex) {
            Log.i("FragMatch: ", "FILE NOT FOUND");
        }
        catch (IOException ex){
            Log.i("FragMatch: ", "IOEXCEPION ERR");
            //System.out.println("IO exception\n");
        }
        Log.i("FragMatch: ", "all frags added");
        Log.i("FragMatch: ",String.valueOf(allFrags.size())+" frags added");
        return allFrags;*/
    }


}
