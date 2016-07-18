package com.mattlab.gym.fityes_v2.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessTokenTracker;
import com.mattlab.gym.fityes_v2.R;

import java.util.ArrayList;

public class CalendarFragment extends Fragment {

    private View parentView;
    private ListView listView;

    private AccessTokenTracker tokenTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.calendar, container, false);
        listView   = (ListView) parentView.findViewById(R.id.listView);
        initView();
        return parentView;
    }


    private void initView(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                getCalendarData());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Listaelem lenyomva", Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayList<String> getCalendarData(){
        ArrayList<String> calendarList = new ArrayList<String>();
        calendarList.add("Karizom edzés");
        return calendarList;
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
