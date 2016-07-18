package com.mattlab.gym.fityes_v2.Activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mattlab.gym.fityes_v2.R;
import com.mattlab.gym.fityes_v2.Utilities.JSONParser;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightGraph extends AppCompatActivity {

    String JSON_STRING;
    String json_string;

    JSONObject jsonObject;
    JSONArray jsonArray;

    private List<Excersizes> weights;

    ArrayList<String> video_links;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_graph);

        Button btn = (Button) findViewById(R.id.add_actual_weight);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert();
            }
        });

        new GetWeight().execute("", "");
    }

    class GetWeight extends AsyncTask<String, String, String> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String client_id = preferences.getString("client_id", "");

        private final String json_url = "http://ext.hu/fityes/api/functions.php?action=get_weight&client_id=" + client_id;


        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(WeightGraph.this);
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

                        String weight = Jarray.getJSONObject(i).getString("weight");
                        String date = Jarray.getJSONObject(i).getString("date");

                        addChart(weight);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    public void addChart(String weight) {
        BarChart mBarChart = (BarChart) findViewById(R.id.barchart);

        int int_weight = Integer.parseInt(weight);

        Random rnd = new Random();
        int r = rnd.nextInt(255);
        int g = rnd.nextInt(255);
        int b = rnd.nextInt(255);

        int randomColor = Color.rgb(r, g, b);

        mBarChart.addBar(new BarModel(int_weight, randomColor));
    }

    public void Alert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Aktuális súly hozzáadása");
        //    alert.setMessage("Message");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);

        alert.setView(input);

        alert.setPositiveButton("Mentés", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String add_Weight = String.valueOf(input.getText());

                new AddWeight().execute(add_Weight, "");
            }
        });

        alert.setNegativeButton("Vissza", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    class AddWeight extends AsyncTask<String, String, String> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String client_id = preferences.getString("client_id", "");

        private String json_url = "http://ext.hu/fityes/api/functions.php?action=add_weight&client_id=" + client_id + "&weight=";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(WeightGraph.this);
            pDialog.setMessage("Adatok mentése...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            try {
                json_url = json_url + args[0];

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
                //   new GetWeight().execute("", "");
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }

        }
    }


}
