package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.database.Cursor;
import android.util.Log;

public class DisplayAdventureActivity extends Activity {

    private DBHelper adventureDB;
    TextView date;
    TextView story;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("DISPLAY", "In display adventure");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_adventure);
        date = (TextView) findViewById(R.id.editTextDate);
        story = (TextView) findViewById(R.id.editTextStory);

        adventureDB = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            int Value = extras.getInt("id");
            if(Value > 0){
                Cursor rs = adventureDB.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
                String currDate = rs.getString(rs.getColumnIndex(DBHelper.ADVENTURE_COLUMN_DATE));
                String currStory = rs.getString(rs.getColumnIndex(DBHelper.ADVENTURE_COLUMN_STORY));

                if(!rs.isClosed())
                {
                    rs.close();
                }
                date.setText((CharSequence)currDate);
                date.setFocusable(false);
                date.setClickable(false);

                story.setText((CharSequence)currStory);
                story.setFocusable(false);
                story.setClickable(false);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_adventure, menu);
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
}
