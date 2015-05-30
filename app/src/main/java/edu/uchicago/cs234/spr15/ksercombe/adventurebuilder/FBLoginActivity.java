package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.facebook.CallbackManager;
import com.facebook.login.LoginResult;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import android.content.Context;

public class FBLoginActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_fblogin);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fblogin, menu);
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



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        CallbackManager callbackManager;
        Context context;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            FacebookSdk.sdkInitialize(context.getApplicationContext());
            View view = inflater.inflate(R.layout.fragment_fblogin, container, false);

            callbackManager = CallbackManager.Factory.create();
            LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
            loginButton.setReadPermissions("user_events");
            loginButton.setFragment(this);
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess (LoginResult loginResult){
                    Generator.setAccessToken(loginResult.getAccessToken());
                    Intent intent = new Intent(context.getApplicationContext(), edu.uchicago.cs234.spr15.ksercombe.adventurebuilder.MainActivity.class);
                    startActivity(intent);
                }
                @Override
                public void onCancel() {
                    Generator.setAccessToken(null);  //User does not allow access
                    Intent intent = new Intent(context.getApplicationContext(), edu.uchicago.cs234.spr15.ksercombe.adventurebuilder.MainActivity.class);
                    startActivity(intent);
                }
                @Override
                public void onError (FacebookException exception){
                    //App Code
                }
            });
            return view;
        }
    }
}
