package com.mattlab.gym.fityes_v2.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.mattlab.gym.fityes_v2.R;
import com.mattlab.gym.fityes_v2.Utilities.JSONParser;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

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
import java.util.HashMap;
import java.util.List;

public class inGroup extends AppCompatActivity {

    String group_id;
    String group_name;
    String comment;
    String JSON_STRING;
    String json_string;

    JSONObject jsonObject;
    JSONArray jsonArray;

    List<inGroupAdapter> ingroup_adapter;
    RecyclerView rv_ingroup;
    ArrayList<String> ingroup_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingroup);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final CollapsingToolbarLayout toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.test);
        Palette.generateAsync(bitmap,
                new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrant =
                                palette.getVibrantSwatch();
                        int mutedColor = palette.getVibrantSwatch().getRgb();
                        if (vibrant != null) {
                            // If we have a vibrant color
                            // update the title TextView
                            // toolbar_layout.setBackgroundColor(mutedColor);
                            // mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                            toolbar_layout.setStatusBarScrimColor(palette.getDarkMutedColor(mutedColor));
                            toolbar_layout.setContentScrimColor(palette.getMutedColor(mutedColor));

                        }
                    }
                });

        initPrefs();

        ingroup_list = new ArrayList<String>();
        ingroup_adapter = new ArrayList<>();
        rv_ingroup = (RecyclerView) findViewById(R.id.rv_ingroup);

        LinearLayoutManager llm_group = new LinearLayoutManager(inGroup.this);

        rv_ingroup.setLayoutManager(llm_group);

        rv_ingroup.setHasFixedSize(true);

    }

    class fetchComments extends AsyncTask<String, String, String> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;


        private final String json_url = "http://ext.hu/fityes/api/functions.php?action=fetchGroupComments&group_id=" + group_id;

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(inGroup.this);
            pDialog.setMessage("Csoport betöltése...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

            Log.e("URL", json_url);
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

                    ingroup_adapter = new ArrayList<>();
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject Jasonobject = Jarray.getJSONObject(i);

                        String group_id = Jarray.getJSONObject(i).getString("group_id");
                        String client_id = Jarray.getJSONObject(i).getString("client_id");
                        String text = Jarray.getJSONObject(i).getString("text");
                        String date = Jarray.getJSONObject(i).getString("date");

                        Log.e("Adatok", client_id + text + date);
                        ingroup_list.add(text);

                        ingroup_adapter.add(new inGroupAdapter(client_id, text, date, R.drawable.test));
                        initializeAdapter();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    private void initializeAdapter() {
        RVAdapter_inGroup adapter = new RVAdapter_inGroup(ingroup_adapter);
        rv_ingroup.setAdapter(adapter);
    }

    private void initPrefs() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.e("Nincs hiba", "Tömb feltöltve");

            //Előző activityből áthozott id lekérése
            group_id = extras.getString("group_id");
            group_name = extras.getString("group_name");

            getSupportActionBar().setTitle(group_name);

            //A csoport ID-jának kiirása
            Log.e("Group ID", group_id);
            new fetchComments().execute(group_id, "var2");

        } else {

            //Link nélküli activity inditáskor visszaléptet a lekérdezéshez
            Log.e("HIBA", "A tömb nincs feltöltve");
            Intent myIntent = new Intent(inGroup.this, MenuActivity.class);
            myIntent.putExtra("error", 0x1);
            inGroup.this.startActivity(myIntent);


        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialog = DialogPlus.newDialog(inGroup.this)
                        .setContentHolder(new ViewHolder(R.layout.content_newpost))
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {

                            }
                        })
                        .setGravity(Gravity.TOP)
                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dialog.show();

                EditText ingroup_comment = (EditText) findViewById(R.id.ingroup_comment);
                ingroup_comment.requestFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(ingroup_comment, InputMethodManager.SHOW_IMPLICIT);

                comment = ingroup_comment.getText().toString();

                final Button SaveComment = (Button) findViewById(R.id.save_comment);
                SaveComment.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //TESZT
                        EditText ingroup_comment = (EditText) findViewById(R.id.ingroup_comment);

                        dialog.dismiss();

                        comment = ingroup_comment.getText().toString();
                        Log.e("Szöveg", comment);
                        //TESZTEND
                        Log.e("Save Comment", "Szöveg" + comment);

                        Snackbar snackbar = Snackbar
                                .make(v, "Bejegyzés mentve", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        new addComments().execute(group_id, comment);

                    }
                });

            }
        });


    }

    class addComments extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String client_id = preferences.getString("client_id", "");

        private final String json_url = "http://ext.hu/fityes/api/functions.php";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        @Override
        protected void onPreExecute() {
            InputMethodManager imm = (InputMethodManager) getSystemService(
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            pDialog = new ProgressDialog(inGroup.this);
            pDialog.setMessage("Bejegyzés mentése...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

            Log.e("URL", json_url);
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                Log.e("Szóköz próba", comment);

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "addComment");
                params.put("group_id", group_id);
                params.put("client_id", client_id);
                params.put("comment", comment);

                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        json_url, "POST", params);

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
                new fetchComments().execute(group_id, "var2");
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
        }
    }

}
