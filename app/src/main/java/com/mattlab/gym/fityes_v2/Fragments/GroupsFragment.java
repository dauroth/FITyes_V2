package com.mattlab.gym.fityes_v2.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.mattlab.gym.fityes_v2.Activitys.GroupAdapter;
import com.mattlab.gym.fityes_v2.Activitys.RVAdapter_Group;
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
import java.util.List;

public class GroupsFragment extends Fragment {

    String JSON_STRING;
    String json_string;

    JSONObject jsonObject;
    JSONArray jsonArray;

    List<GroupAdapter> group_adapter;
    RecyclerView rv_group;
    ArrayList<String> group_list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.groups, container, false);

        group_list = new ArrayList<String>();

        rv_group = (RecyclerView) rootView.findViewById(R.id.rv_group);

        LinearLayoutManager llm_group = new LinearLayoutManager(getActivity());

        rv_group.setLayoutManager(llm_group);
//        initializeData("Teszt", "Teszt2");

        rv_group.setHasFixedSize(true);


        new FetchGroups().execute("var1", "var2");

        SearchButton();
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_group);

        EditText group_search = (EditText) rootView.findViewById(R.id.group_search);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(getActivity())
                        .setContentHolder(new ViewHolder(R.layout.content_searchgroup))
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {

                            }
                        })
                        .setGravity(Gravity.TOP)
                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dialog.show();
            }
        });
        return rootView;
    }


    class FetchGroups extends AsyncTask<String, String, String> {
        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog;

        private static final String json_url = "http://ext.hu/fityes/api/functions.php?action=fetchGroups";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";


        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
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

                    group_adapter = new ArrayList<>();
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject Jasonobject = Jarray.getJSONObject(i);

                        String group_id = Jarray.getJSONObject(i).getString("group_id");
                        String name = Jarray.getJSONObject(i).getString("group_name");
                        String desc = Jarray.getJSONObject(i).getString("group_desc");
                        String img = Jarray.getJSONObject(i).getString("group_img");

                        group_list.add(desc);

                        group_adapter.add(new GroupAdapter(group_id, name, desc, R.drawable.test));
                        initializeAdapter();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    private void initializeAdapter() {
        RVAdapter_Group adapter = new RVAdapter_Group(group_adapter);
        rv_group.setAdapter(adapter);
    }

    public void SearchButton() {


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