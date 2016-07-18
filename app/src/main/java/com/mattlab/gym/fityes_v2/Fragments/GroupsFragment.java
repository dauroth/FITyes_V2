package com.mattlab.gym.fityes_v2.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.mattlab.gym.fityes_v2.Activitys.DisplayListView;
import com.mattlab.gym.fityes_v2.R;
import com.mattlab.gym.fityes_v2.Utilities.Adapters.GroupAdapter;
import com.mattlab.gym.fityes_v2.Utilities.GroupSetter;

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

public class GroupsFragment extends Fragment {

    private View parentView;
    String JSON_STRING;
    String json_string;

    JSONObject jsonObject;
    JSONArray jsonArray;

    GroupAdapter groupAdapter;

    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        new BackgroundTask().execute();


        return inflater.inflate(R.layout.groups, container, false);
    }

    public void getJSON(View view) {


    }

    private class BackgroundTask extends AsyncTask<String, Void, String> {

        String json_url;


        @Override
        protected void onPreExecute() {
            json_url = "http://ext.hu/fityes/api/functions.php?action=fetchgroups";
        }

        @Override
        protected String doInBackground(String... params) {

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

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            //TextView textview = (TextView) getActivity().findViewById(R.id.textView_test);
            //textview.setText(result);
            json_string = result;
            if (json_string == null) {
                Toast.makeText(getActivity(), "Hiba a lekérdezésben", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(getActivity(), DisplayListView.class);
                intent.putExtra("json_data", json_string); //Optional parameters
                getActivity().startActivity(intent);
                Log.e("TESZTADAT", json_string);

            }

            json_string = result;

        }


    }

    public void parseJSON(View view) {

    }

    public void FillUpList() {
        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String name, desc, number;

            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                name = JO.getString("name");
                desc = JO.getString("desc");
                number = JO.getString("id");

                GroupSetter groupSetter = new GroupSetter(name, desc, number);
                groupAdapter.add(groupSetter);
                count++;
            }
            listView = (ListView) getActivity().findViewById(R.id.listView);

            groupAdapter = new GroupAdapter(getActivity(), R.layout.row_layout);

            listView.setAdapter(groupAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
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