package com.mattlab.gym.fityes_v2.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.mattlab.gym.fityes_v2.R;
import com.mattlab.gym.fityes_v2.Utilities.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegWithFace extends AppCompatActivity {

    ProfileTracker profTrack;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;
    String name;
    String mail_var;
    EditText emails;
    String fb_id;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookInit();
        Profile profile;
        profile = Profile.getCurrentProfile();
        setContentView(R.layout.activity_regwithface);


        EditText fb_name = (EditText) findViewById(R.id.fb_name);
        emails = (EditText) findViewById(R.id.textMailFBREG);
        fb_name.setText(profile.getName(), TextView.BufferType.EDITABLE);

        name = profile.getName();
        mail_var = emails.getText().toString();
        fb_id = profile.getId();

        Button btn = (Button) findViewById(R.id.btn_reg_next_fb);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_reg_next_fb(v);
            }
        });


    }

    public void FacebookInit() {
        FacebookSdk.sdkInitialize(RegWithFace.this.getApplicationContext());

        accessToken = AccessToken.getCurrentAccessToken();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // App code
                if (currentAccessToken == null) {
                    Toast.makeText(RegWithFace.this, "A Facebook kijelentkeztette, kérem jelentkezzen be újra!",
                            Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(RegWithFace.this, Initialize.class);
                    myIntent.putExtra("key", "2"); //Optional parameters
                    RegWithFace.this.startActivity(myIntent);
                }
                //}
            }
        };
        profTrack = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code

            }
        };
    }

    public void btn_reg_next_fb(View v) {
        mail_var = emails.getText().toString();
        new RegFBPostAsync().execute(name, emails.getText().toString());
    }

    class RegFBPostAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;


        private static final String LOGIN_URL = "http://www.ext.hu/fityes/api/functions.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {


            pDialog = new ProgressDialog(RegWithFace.this);
            pDialog.setMessage("Adatok mentése...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {
                Profile profile;
                profile = Profile.getCurrentProfile();

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "regwfb");
                params.put("name", args[0]);
                params.put("email", mail_var);
                params.put("fb_id", profile.getId());

                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);

                if (json != null) {
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
                pDialog = new ProgressDialog(RegWithFace.this);
                pDialog.setMessage("Sikeres mentés...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();

                user_id = message;


                Intent myIntent = new Intent(RegWithFace.this, RegWithFaceNext.class);
                myIntent.putExtra("user_id", user_id); //Optional parameters
                RegWithFace.this.startActivity(myIntent);
                finish();

            } else {

                Toast.makeText(RegWithFace.this, "Hibás e-mail vagy jelszó páros",
                        Toast.LENGTH_LONG).show();

            }
        }

    }
}
