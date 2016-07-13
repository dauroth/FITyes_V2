package com.mattlab.gym.fityes_v2.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.facebook.Profile;
import com.mattlab.gym.fityes_v2.R;
import com.mattlab.gym.fityes_v2.Utilities.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegWithFaceNext extends AppCompatActivity {

    String user_id;
    CheckBox dumbbell;
    CheckBox barbell;
    CheckBox kettlebell;
    CheckBox clubbell;
    CheckBox sandbag;
    CheckBox trx;

    String dumbint, barint, kettleint, clubint, sandint, trxint = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regwithfacenext);


        //Bundle
        Bundle extras = getIntent().getExtras();
        //ExtraString
        user_id = extras.getString("user_id");

        Button btn = (Button) findViewById(R.id.eq_reg_fb);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eq_reg_fb(v);
            }
        });
    }

    public void eq_reg_fb(View v) {


        new RegFBPostAsync().execute("valtozo1", "valtozo2");
    }

    class RegFBPostAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;


        private static final String LOGIN_URL = "http://www.ext.hu/fityes/api/functions.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {


            pDialog = new ProgressDialog(RegWithFaceNext.this);
            pDialog.setMessage("Adatok mentése...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            InitCheckBox();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {
                Profile profile;
                profile = Profile.getCurrentProfile();

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "eq_first");
                params.put("client_id", user_id);
                params.put("dumbbell", dumbint);
                params.put("barbell", barint);
                params.put("kettlebell", kettleint);
                params.put("clubbell", clubint);
                params.put("sandbag", sandint);
                params.put("trx", trxint);

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

                try {
                    success = json.getInt(TAG_SUCCESS);
                    message = json.getString(TAG_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (success == 1) {
                pDialog = new ProgressDialog(RegWithFaceNext.this);
                pDialog.setMessage("Sikeres mentés! Felkészülés az első inditásra...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();

                user_id = message;


                Intent myIntent = new Intent(RegWithFaceNext.this, MenuActivity.class);
                myIntent.putExtra("user_id", user_id); //Optional parameters
                RegWithFaceNext.this.startActivity(myIntent);
                finish();

            } else {

                Toast.makeText(RegWithFaceNext.this, "Hiba a betöltéskor, kérlek próbáld újra!",
                        Toast.LENGTH_LONG).show();

            }
        }

    }

    public void InitCheckBox() {
        dumbbell = (CheckBox) findViewById(R.id.dumbbell);
        barbell = (CheckBox) findViewById(R.id.barbell);
        kettlebell = (CheckBox) findViewById(R.id.kettlebell);
        clubbell = (CheckBox) findViewById(R.id.clubbell);
        sandbag = (CheckBox) findViewById(R.id.sandbag);
        trx = (CheckBox) findViewById(R.id.trx);

        if (dumbbell.isChecked()) {
            dumbint = "1";
        } else {
            dumbint = "0";
        }

        if (barbell.isChecked()) {
            barint = "1";
        } else {
            barint = "0";
        }

        if (kettlebell.isChecked()) {
            kettleint = "1";
        } else {
            kettleint = "0";
        }

        if (clubbell.isChecked()) {
            clubint = "1";
        } else {
            clubint = "0";
        }

        if (sandbag.isChecked()) {
            sandint = "1";
        } else {
            sandint = "0";
        }

        if (trx.isChecked()) {
            trxint = "1";
        } else {
            trxint = "0";
        }

    }
}
