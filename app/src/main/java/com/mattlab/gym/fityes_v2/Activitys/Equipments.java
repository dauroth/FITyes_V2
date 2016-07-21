package com.mattlab.gym.fityes_v2.Activitys;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.mattlab.gym.fityes_v2.R;
import com.mattlab.gym.fityes_v2.Utilities.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Equipments extends AppCompatActivity {

    String user_id;
    private CheckBox dumbbell;
    private CheckBox barbell;
    private CheckBox kettlebell;
    private CheckBox clubbell;
    private CheckBox sandbag;
    private CheckBox trx;

    private String JSON_STRING;
    String json_string;

    JSONObject jsonObject;
    JSONArray jsonArray;

    private String bodyweight_string, dumbbell_string, barbell_string, kettlebell_string, clubbell_string, sandbag_string, trx_string = "0";
    private String dumbint, barint, kettleint, clubint, sandint, trxint = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eqipments);

        Button btn = (Button) findViewById(R.id.eq_reg_fb);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eq_reg_fb(v);
            }
        });

        new GetEquip().execute("valtozo1", "valtozo2");
    }

    public void eq_reg_fb(View v) {
        new AddEquip().execute("valtozo1", "valtozo2");
    }

    class AddEquip extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;


        private static final String LOGIN_URL = "http://ext.hu/fityes/api/functions.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {


            pDialog = new ProgressDialog(Equipments.this);
            pDialog.setMessage("Adatok mentése...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                InitCheckBox_ForAdd();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String client_id = preferences.getString("client_id", "");

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "add_equipments");
                params.put("client_id", client_id);
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
                pDialog = new ProgressDialog(Equipments.this);
                pDialog.setMessage("Sikeres mentés!");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
                pDialog.dismiss();
                new GetEquip().execute("valtozo1", "valtozo2");


            } else {

                Toast.makeText(Equipments.this, "Hiba a betöltéskor, kérlek próbáld újra!",
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

        if (dumbbell_string.equals("1")) {
            dumbbell.setChecked(true);
        }

        if (barbell_string.equals("1")) {
            barbell.setChecked(true);
        }

        if (kettlebell_string.equals("1")) {
            kettlebell.setChecked(true);
        }

        if (clubbell_string.equals("1")) {
            clubbell.setChecked(true);
        }

        if (sandbag_string.equals("1")) {
            sandbag.setChecked(true);
        }

        if (trx_string.equals("1")) {
            trx.setChecked(true);
        }

    }

    class GetEquip extends AsyncTask<String, String, String> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String client_id = preferences.getString("client_id", "");

        private final String json_url = "http://ext.hu/fityes/api/functions.php?action=get_equipments&client_id=" + client_id;


        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Equipments.this);
            pDialog.setMessage("Adatok betöltése...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            try {

                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(String json) {

            int success = 0;
            String message = "";

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (json != null) {

                try {

                    JSONObject object = new JSONObject(json);
                    JSONArray Jarray = object.getJSONArray("result");

                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject Jasonobject = Jarray.getJSONObject(i);

                        bodyweight_string = Jarray.getJSONObject(i).getString("bodyweight");
                        dumbbell_string = Jarray.getJSONObject(i).getString("dumbbell");
                        barbell_string = Jarray.getJSONObject(i).getString("barbell");
                        kettlebell_string = Jarray.getJSONObject(i).getString("kettlebell");
                        clubbell_string = Jarray.getJSONObject(i).getString("clubbell");
                        sandbag_string = Jarray.getJSONObject(i).getString("sandbag");
                        trx_string = Jarray.getJSONObject(i).getString("trx");


                        InitCheckBox();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    public void InitCheckBox_ForAdd() {
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


    public void onDestroy() {
        super.onDestroy();
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
