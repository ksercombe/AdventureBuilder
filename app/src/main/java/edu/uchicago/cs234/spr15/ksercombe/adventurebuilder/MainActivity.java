package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.provider.CallLog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;


import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.widget.LoginButton;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.melnykov.fab.FloatingActionButton;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.os.StrictMode;


public class MainActivity extends Activity {

    private static AccessToken fbAccessToken;
    private static String fbUserId;
    private class BriteOrderInstance implements Callback<List<BriteOrder>> {

        @Override
        public void failure(RetrofitError arg0) {
        }

        @Override
        public void success(List<BriteOrder> arg0, Response arg1) {
            for(int i=0; i<arg0.size(); i++){
                briteIds.add(arg0.get(i).eventId);
                try {
                    bService.getEvent(arg0.get(i).eventId, new BriteOccasionInstance());
                } catch (RetrofitError e) {
                    System.out.println(e.getResponse().getStatus());
                }
            }
        }

    }

    private class BriteOccasionInstance implements Callback<BriteOccasion>{

        @Override
        public void failure(RetrofitError arg0) {
        }

        @Override
        public void success(BriteOccasion arg0, Response arg1) {
            dayEvent.add(arg0);
        }

    }

    private ListView m_listview;
    DBHelper actionDB;

    Context context;
    private ArrayList<StoryFrag> stories = new ArrayList<StoryFrag>();
    private ArrayList<Integer> briteIds = new ArrayList<Integer>();
    private ArrayList<Occasion> dayEvent = new ArrayList<Occasion>();

    //rest adapter and retrofit data services
    private RestAdapter restAdapter;
    private BriteService bService;

    public static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) //THIS IS WHAT WE NEED
            .registerTypeAdapter(Date.class, new DateTypeAdapter())
            .create();


    public class MyErrorHandler implements ErrorHandler {
        @Override public Throwable handleError(RetrofitError cause) {
//	  	    Response r = cause.getResponse();
            Log.e("AdventureBuilderError", cause.getMessage(), cause.getCause());
            return cause;
        }
    }

    public static void setAccessToken(AccessToken at){
        fbAccessToken = at;
    }
    public static AccessToken getAccessToken(){
        return fbAccessToken;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Main", "Created Main Activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionDB = new DBHelper(this);
        ArrayList adventureList = actionDB.getAllAdventures();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, adventureList) ;

        m_listview = (ListView)findViewById(R.id.listView1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(m_listview);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAdventure(getApplicationContext());
            }
        });

        m_listview.setAdapter(arrayAdapter);

        m_listview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int id_To_Search = arg2 + 1;
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);
                Intent intent = new Intent(getApplicationContext(), edu.uchicago.cs234.spr15.ksercombe.adventurebuilder.DisplayAdventureActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //GENERATOR CODE

    private void addFacebookEvents() {
        Log.i("FB: ", "In addFacebookEvents");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GraphRequest request = new GraphRequest(fbAccessToken, "/me", null, HttpMethod.GET, new GraphRequest.Callback(){
            public void onCompleted(GraphResponse response){
                JSONObject user = response.getJSONObject();
                try {
                    fbUserId = user.getString("id");
                    Log.i("FB: ", "User id: " + fbUserId);
                }
                catch (JSONException m){
                    Log.i("FB: ", "JSON failure");
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id");
        request.setParameters(parameters);
        request.executeAndWait();
        GraphRequest request2 = new GraphRequest(fbAccessToken, "/" + fbUserId +"/events", null, HttpMethod.GET, new GraphRequest.Callback() {
            public void onCompleted(GraphResponse response) {
                JSONObject vals = response.getJSONObject();
                try {
                    JSONArray events =  vals.getJSONArray("data");
                    for (int i = 0; i < events.length(); i++) {
                        JSONObject currEvent = events.getJSONObject(i);
                        String attending = currEvent.getString("rsvp_status");
                        Log.i("FB: ", "Status: "+ attending);
                        if (attending.equals("attending")) {
                            FBOccasion fb = new FBOccasion(currEvent);
                            //if (fb.start.localDatetime.getYear() == DateTime.now().getYear() && fb.start.localDatetime.getMonthOfYear() == DateTime.now().getMonthOfYear() && fb.start.localDatetime.getDayOfMonth() == DateTime.now().getDayOfMonth()) {
                                dayEvent.add(fb);
                                Log.i("FB: ", "Added Event!");
                            //}
                        }
                    }
                }
                catch(JSONException m){
                    //Fails to get
                    Log.i("FB: ", "Fails to get events");
                }

            }
        });
        Bundle parameters2 = new Bundle();
        parameters.putString("fields", "data");
        request2.setParameters(parameters2);
        request2.executeAsync();

    }

    public void addCallEvents(Context context1){
        context = context1;
        /*IDK IF I"M DOING THIs RIGHT...*/
        //DateTime yr = DateTime.now().getYear();
        //DateTime mon = DateTime.now().getMonthOfYear();
        //DateTime day = DateTime.now().getDayOfMonth();
        int yr = 2015;
        int mon = 06;
        int day = 01;

        String date = String.valueOf(yr)+String.valueOf(mon)+String.valueOf(day);

        String selection = CallLog.Calls.DATE + "=" + date;
        Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,null,null,null, null);
        //       Log.i("CALLS:", managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.DATE)));
        while (managedCursor.moveToNext()){
            int dirCode = Integer.parseInt(managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.TYPE)));

            switch (dirCode){
                case CallLog.Calls.MISSED_TYPE:
                    continue;
                default:
                    break;
            }

            CallOccasion occ = new CallOccasion(managedCursor);
            Log.i("CAllS: ", occ.service + occ.title + occ.desc);
            dayEvent.add(occ);
        }
    }

    public static String ckTag(String title) {
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
        else if (title.contains("call"))
            return "phone call";
        else if (title.contains("night out"))
            return "night out";
        else
            return "";
    }

    public static Occasion tagOccasion(Occasion occ) {
        ArrayList<String> tags = new ArrayList<String>();
        String tag;
        String descTag;
        Log.i("Tagging: ", occ.service );
        String service = occ.service.toLowerCase();
        String title = occ.title.toLowerCase();
        String desc = occ.desc.toLowerCase();

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

    public static StoryFrag fragMatch(Occasion occ, ArrayList<StoryFrag> allFrags) {
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
                    if (currFrag.tags.get(j).contains(occTags.get(k))) {
                        fragOptions.add(currFrag);

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
        ArrayList<String> movies = new ArrayList<String>(); //Great choices
        movies.add("Jackass");
        movies.add("Silence of the Lambs");
        movies.add("Hairspray");
        movies.add("Four Christmases");
        movies.add("Zoolander"); //especially this one
        movies.add("The Emperor's New Groove");
        movies.add("Bride and Prejudice");
        ArrayList<String> food = new ArrayList<String>(); //good stuff
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
                        replacer = new SimpleDateFormat("HH:mm").format(currOcc.end.localDatetime);
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
                actualFrag = actualFrag.replaceAll(currReplace.replace("[","").replace("]",""), replacer);
                actualFrag = actualFrag.replace("[","").replace("]","");
            }
            story = story+" "+actualFrag;
        }
        return story;
    }

    public Adventure buildAdventure(Context context1) {
        context = context1;
        ArrayList<StoryFrag> stories = new ArrayList<StoryFrag>();

        // ArrayList<Occasion> dayEvent = new ArrayList<Occasion>();
        /*BUILD OCCASION LIST HERE*/

        //set up eventbrite REST adapter
        /*restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://www.eventbriteapi.com/v3")
                .setErrorHandler(new MyErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.FULL)  // Do this for development too.
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Authorization","Bearer HJBOHLNUZBUFXMGVYTCN");
                    } //Currently hard-coded to my (Evan's) EventBrite OAuth token.
                })
                .setConverter(new GsonConverter(gson)) //ADD THIS TO THE REST ADAPTER
                .build();

        try {
            bService.getOrders(new BriteOrderInstance()); //if import is successful, then runs
    } catch (RetrofitError e) {                           //BriteOrderInstance.success method
            System.out.println(e.getResponse().getStatus());
        }*/

        addFacebookEvents();

        Log.i("Main: ", "After FB events");
        /*MUST RESOLVE CURRENT DATE/TIME*/
        addCallEvents(context);
        Log.i("Main: ", "After Call Events");

        /*read in all frags from a file*/

        ArrayList<StoryFrag> allFrag = StoryFrag.getAllFrags();
        //String filename = "stories.txt";
        //String line = null;
           /*
        try{
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            not sure how to fully initialize frags; what is sent fill and will
            n persons and location be provided in file or must we search

            while((line = bufferedReader.readLine()) != null){

                StoryFrag initFrag = new StoryFrag(line,);
                allFrag.add(initFrag);
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex){
            System.out.println("file not found\n");
        }
        catch (IOException ex){
            System.out.println("IO exception\n");
        }
        */

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date dayDate = new Date();

        Log.i("Main: ", "before tagging");
        /*MUST ORGANIZE DAY EVENT BY START TIME*/
        int eventSize = dayEvent.size();
        for (int i = 0; i < eventSize; i++){
            Occasion occ1 = dayEvent.get(i);
            if (occ1 == null){
                Log.e("Tagging: ", "NULL OCCASION");
            }
            Log.i("Tagging: ", occ1.service);
            occ1 = tagOccasion(occ1);
            StoryFrag frag = fragMatch(occ1,allFrag);
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
