package edu.uchicago.cs234.spr15.ksercombe.adventurebuilder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.IntentSender.SendIntentException;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
//import com.google.android.gms.common.api.Api;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.services.calendar.Calendar;

import java.io.IOException;


public class GoogleLoginActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
View.OnClickListener {

    //private String clientId = String.valueOf(R.string.google_client_id);
    //private String clientSecret = String.valueOf(R.string.google_client_secret);
    //private String redirectUri = "http://localhost";
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private boolean mSignInClicked;
    private boolean mIntentInProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);



        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope("https://www.googleapis.com/auth/calendar.readonly"))
                .addScope(new Scope("https://www.googleapis.com/auth/plus.login"))
                .addScope(new Scope("https://www.googleapis.com/auth/calendar"))
                .build();

        findViewById(R.id.sign_in_button).setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button && !mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress && result.hasResolution()) {
            try {
                mIntentInProgress = true;
                result.startResolutionForResult(this, RC_SIGN_IN);
            } catch (SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
        String mAccountName = Plus.AccountApi.getAccountName(mGoogleApiClient);
        MainActivity.setGAccountName(mAccountName);
        new RetrieveTokenTask().execute(mAccountName);
    }


    public void onConnectionSuspended(int i){
        Log.i("G sign in: ", "Connection suspended");

    }
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;


            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.reconnect();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_google_login, menu);
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

    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "RetrieveAccessToken";


        @Override
        protected String doInBackground(String... params) {
            Log.i("Token: ", "In retrieve token");
            String accountName = params[0];
            /*Bundle scopes = new Bundle();
            scopes.putString("profile", "oauth2:profile email");
            scopes.putString("calendar", "https://www.googleapis.com/auth/calendar.readonly");
            scopes.putString("play","https://www.googleapis.com/auth/plus.login" );*/


            String scopes = "oauth2:https://www.googleapis.com/auth/calendar https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/plus.login";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(getApplicationContext(), accountName, scopes);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } catch (UserRecoverableAuthException e) {
                startActivityForResult(e.getIntent(), RC_SIGN_IN);
            } catch (GoogleAuthException e) {
                Log.e(TAG, e.getMessage());
            }
            if(token == null){
                Log.i("Token: ", "TOKEN IS NULL");
            }
            Log.i("Token: ", token);
            return token;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity.setGAccessToken(s);
            //Log.i("AccessToken: ", s);
            Intent i = new Intent(getApplicationContext(), edu.uchicago.cs234.spr15.ksercombe.adventurebuilder.FBLoginActivity.class);
            startActivity(i);

            //((TextView) findViewById(R.id.token_value)).setText("Token Value: " + s);
        }
    }
}
