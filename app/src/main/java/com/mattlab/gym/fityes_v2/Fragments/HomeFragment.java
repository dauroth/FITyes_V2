package com.mattlab.gym.fityes_v2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattlab.gym.fityes_v2.Activitys.Excersize;
import com.mattlab.gym.fityes_v2.Activitys.ExcersizeList;
import com.mattlab.gym.fityes_v2.Activitys.MenuActivity;
import com.mattlab.gym.fityes_v2.R;
import com.mattlab.gym.fityes_v2.Utilities.Menu.ResideMenu;


public class HomeFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        setUpViews();
        return parentView;
    }

    private void setUpViews() {
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        parentView.findViewById(R.id.startlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Breakpoint", "Start Activity");
                startEXER_FromCard(v);
            }
        });


        parentView.findViewById(R.id.start_excersize).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Breakpoint", "Start Activity");
                startPULSE_FromCard(v);
            }
        });

        /*parentView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

        // add gesture operation's ignored views
        FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
        resideMenu.addIgnoredView(ignored_view);*/
    }

    public void startEXER_FromCard(View v) {
        Intent myIntent = new Intent(getActivity(), ExcersizeList.class);
        Log.e("Breakpoint", "Starter CLASS");
        //myIntent.putExtra("key", "2"); //Optional parameters
        getActivity().startActivity(myIntent);
    }


    public void startPULSE_FromCard(View v) {
        Intent myIntent = new Intent(getActivity(), Excersize.class);
        Log.e("Breakpoint", "Starter CLASS");
        //myIntent.putExtra("key", "2"); //Optional parameters
        getActivity().startActivity(myIntent);
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
