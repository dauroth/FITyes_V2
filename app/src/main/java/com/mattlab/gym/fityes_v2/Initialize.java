package com.mattlab.gym.fityes_v2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by EXT on 2016. 04. 25..
 * Author: Máté Németh
 */
public class Initialize extends AppCompatActivity {

    public boolean isLoggedin;
    public boolean firstStep;

    // ----------------------------------------------- INIT START ----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_initialize);
        AppEventsLogger.activateApp(this);

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

    class PostAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String LOGIN_URL = "http://www.ext.hu/api/index.php";

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


}
