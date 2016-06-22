package com.mattlab.gym.fityes_v2.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.mattlab.gym.fityes_v2.R;
import com.mattlab.gym.fityes_v2.Utilities.Adapters.GroupAdapter;
import com.mattlab.gym.fityes_v2.Utilities.GroupSetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayListView extends AppCompatActivity {

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    GroupAdapter groupAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_listview);

        listView = (ListView) findViewById(R.id.listview);


        groupAdapter = new GroupAdapter(this, R.layout.row_layout);
        listView.setAdapter(groupAdapter);

        json_string = getIntent().getExtras().getString("json_data");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
