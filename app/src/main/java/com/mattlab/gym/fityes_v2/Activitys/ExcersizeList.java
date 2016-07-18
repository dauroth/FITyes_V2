package com.mattlab.gym.fityes_v2.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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
import java.util.ArrayList;
import java.util.List;

public class ExcersizeList extends AppCompatActivity {

    String username;
    String password;

    String JSON_STRING;
    String json_string;

    JSONObject jsonObject;
    JSONArray jsonArray;

    private List<Excersizes> excersizes;
    private RecyclerView rv;
    ArrayList<String> video_links;

    FloatingActionButton FloatPlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excersizelist);

        video_links = new ArrayList<String>();

        username = "Tesztnév";
        password = "TesztPass";

        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        //testtv = (TextView)findViewById(R.id.testtext);

        new PostAsync().execute(username, password);
        rv.setHasFixedSize(true);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.playButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PlayExercize(video_links);
            }
        });
    }


    class PostAsync extends AsyncTask<String, String, String> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String json_url = "http://ext.hu/fityes/api/functions.php?action=fetchExcersize&Level=2";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ExcersizeList.this);
            pDialog.setMessage("Adatok beöltése...");
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

                    //Log.e("First build","A teszt kimenetele:" + Jarray);
                    excersizes = new ArrayList<>();
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject Jasonobject = Jarray.getJSONObject(i);

                        String name = Jarray.getJSONObject(i).getString("name");
                        String link = Jarray.getJSONObject(i).getString("link");

                        video_links.add(link);

                        Log.e("Video", "LINK:" + video_links.get(i));

                        //    Log.e("First build", "A teszt kimenetele:" + Jasonobject);
                        excersizes.add(new Excersizes(name, link, R.drawable.test));
                        initializeAdapter();
                        //    Log.e("Second Build", "Output" + name);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    private void initializeData(String id, String name) {

        excersizes.add(new Excersizes(id, name, R.drawable.test));
    }

    public void PlayExercize(ArrayList<String> video_links) {
        Intent myIntent = new Intent(ExcersizeList.this, Excersize.class);
        myIntent.putExtra("links", video_links); //loggedin igaz
        ExcersizeList.this.startActivity(myIntent);
    }


    private void initializeAdapter() {
        RVAdapter adapter = new RVAdapter(excersizes);
        rv.setAdapter(adapter);
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