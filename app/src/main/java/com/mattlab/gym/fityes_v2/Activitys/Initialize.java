package com.mattlab.gym.fityes_v2.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mattlab.gym.fityes_v2.R;
import com.mattlab.gym.fityes_v2.Utilities.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by EXT on 2016. 04. 25..
 * Author: Máté Németh
 */
public class Initialize extends AppCompatActivity {

    public boolean isLoggedin;
    public boolean firstStep;


    private LoginButton loginButton;
    CallbackManager callbackManager;
    ProfileTracker profTrack;


    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            ProgressDialog pDialog = new ProgressDialog(Initialize.this);
            pDialog.setMessage("Bejelentkezés...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

            String user_fb_id = profile.getId();

            Log.e("FB_ID", user_fb_id);

            new FB_Login().execute(user_fb_id, "login_fb");

            /*Intent myIntent = new Intent(Initialize.this, MenuActivity.class);
            myIntent.putExtra("key", "2"); //Optional parameters
            Initialize.this.startActivity(myIntent);
            finish();*/
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }

        class FB_Login extends AsyncTask<String, String, JSONObject> {
            JSONParser jsonParser = new JSONParser();

            private ProgressDialog pDialog;

            private static final String LOGIN_URL = "http://www.ext.hu/fityes/api/functions.php";

            private static final String TAG_SUCCESS = "success";
            private static final String TAG_MESSAGE = "message";


            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Initialize.this);
                pDialog.setMessage("Bejelentkezés...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected JSONObject doInBackground(String... args) {

                try {

                    HashMap<String, String> params = new HashMap<>();
                    params.put("action", "login_fb");
                    params.put("fb_id", args[0]);

                    Log.e("FB_ID", args[0]);

                    JSONObject json = jsonParser.makeHttpRequest(
                            LOGIN_URL, "GET", params);

                    Log.e("JSON_RESULT", "JSON" + json);

                    if (json != null) {
                        Log.e("JSON result", json.toString());
                        return json;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            protected void onPostExecute(JSONObject json) {


                int success = 0;
                String message = "";

                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                if (json != null) {
                /*Toast.makeText(Initialize.this, json.toString(),
                        Toast.LENGTH_LONG).show();*/

                    try {
                        success = json.getInt(TAG_SUCCESS);
                        message = json.getString(TAG_MESSAGE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (success == 1) {
                    pDialog = new ProgressDialog(Initialize.this);
                    pDialog.setMessage("Sikeres bejelentkezés...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(true);
                    pDialog.show();

                    Toast.makeText(Initialize.this, "Szerepel az ID a táblában",
                            Toast.LENGTH_LONG).show();

                    Intent myIntent = new Intent(Initialize.this, MenuActivity.class);
                    myIntent.putExtra("key", "2"); //Optional parameters
                    Initialize.this.startActivity(myIntent);
                    finish();

                } else {


                    Toast.makeText(Initialize.this, "Nem szerepelt az ID a táblában",
                            Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(Initialize.this, RegWithFace.class);
                    myIntent.putExtra("key", "2"); //Optional parameters
                    Initialize.this.startActivity(myIntent);
                    finish();
                }
            }
        }
    };



    // ----------------------------------------------- INIT START ----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_initialize);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.mattlab.gym.fityes_v2",  // replace with your unique package name
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        Profile profile;
        profile = Profile.getCurrentProfile();
        isNetworkOnline();

        if (profile != null) {
            Intent myIntent = new Intent(Initialize.this, MenuActivity.class);
            myIntent.putExtra("key", "2"); //Optional parameters
            Initialize.this.startActivity(myIntent);
            finish();
        }


        profile = Profile.getCurrentProfile();

        //textView17.setText("Üdv: " + profile.getName());


        profTrack = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code


                Log.d("current profile", "" + currentProfile);
            }
        };


        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, callback);






        isLoggedin = false;
        firstStep = true;
// ----------------------------------------------- INIT END ------------------------------------------------------------------
// ----------------------------------------------- Bejelentkezés start - Async Start------------------------------------------
        Button button = (Button) findViewById(R.id.buttonSignIn);

        button.setOnClickListener(new View.OnClickListener() {

            EditText email = (EditText) findViewById(R.id.mail);
            EditText password_text = (EditText) findViewById(R.id.password);


            @Override
            public void onClick(View view) {
                String username = email.getText().toString(); //get this from user input
                String password = password_text.getText().toString(); //get this from user input
                new PostAsync().execute(username, password);
            }

        });



        if (isLoggedin) {
            init();
        }

        Button btn = (Button) findViewById(R.id.btn_reg);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reg(v);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    class PostAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String LOGIN_URL = "http://www.ext.hu/fityes/api/functions.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Initialize.this);
            pDialog.setMessage("Bejelentkezés...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "login");
                params.put("name", args[0]);
                params.put("password", args[1]);

                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONObject json) {


            int success = 0;
            String message = "";

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (json != null) {
                /*Toast.makeText(Initialize.this, json.toString(),
                        Toast.LENGTH_LONG).show();*/

                try {
                    success = json.getInt(TAG_SUCCESS);
                    message = json.getString(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (success == 1) {
                pDialog = new ProgressDialog(Initialize.this);
                pDialog.setMessage("Sikeres bejelentkezés...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                Log.d("Sikeres!", message);

                Intent myIntent = new Intent(Initialize.this, MenuActivity.class);
                myIntent.putExtra("key", "2"); //Optional parameters
                Initialize.this.startActivity(myIntent);
                finish();

            } else {

                Toast.makeText(Initialize.this, "Hibás e-mail vagy jelszó páros",
                        Toast.LENGTH_LONG).show();
                Log.d("Sikertelen", message);

            }
        }

    }

    public void Reg(View v){
        Intent myIntent = new Intent(Initialize.this, Registration.class);
        myIntent.putExtra("key", "2"); //Optional parameters
        Initialize.this.startActivity(myIntent);
    }

    public void init() {
        if (isLoggedin == true && firstStep == true) {
            Intent myIntent = new Intent(Initialize.this, MenuActivity.class);
            myIntent.putExtra("loggedin", true); //loggedin igaz
            Initialize.this.startActivity(myIntent);
        } else if (firstStep == false) {
            Intent myIntent = new Intent(Initialize.this, FirstStep.class);
            myIntent.putExtra("loggedin", true); //loggedin igaz
            Initialize.this.startActivity(myIntent);
        }
    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;

    }


}