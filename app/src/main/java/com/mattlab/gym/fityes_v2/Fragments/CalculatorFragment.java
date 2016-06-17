package com.mattlab.gym.fityes_v2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattlab.gym.fityes_v2.Activitys.BMICalculator;
import com.mattlab.gym.fityes_v2.Activitys.MenuActivity;
import com.mattlab.gym.fityes_v2.Activitys.PulseCalculator;
import com.mattlab.gym.fityes_v2.R;
import com.mattlab.gym.fityes_v2.Utilities.Menu.ResideMenu;


public class CalculatorFragment extends Fragment {

    private View parentView;
    private ResideMenu resideMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.calculator, container, false);
        setUpViews();
        return parentView;
    }

    private void setUpViews() {
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        parentView.findViewById(R.id.bmi_start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBMI(v);
            }
        });
        parentView.findViewById(R.id.bmi_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBMI_FromCard(v);
            }
        });
        parentView.findViewById(R.id.pulse_start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPULSE(v);
            }
        });
        parentView.findViewById(R.id.pulse_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPULSE_FromCard(v);
            }
        });

//        // add gesture operation's ignored views
//        FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
//        resideMenu.addIgnoredView(ignored_view);
    }

    public void startBMI(View v) {
        Intent myIntent = new Intent(getActivity(), BMICalculator.class);
        myIntent.putExtra("key", "2"); //Optional parameters
        getActivity().startActivity(myIntent);
    }

    public void startBMI_FromCard(View v) {
        Intent myIntent = new Intent(getActivity(), BMICalculator.class);
        myIntent.putExtra("key", "2"); //Optional parameters
        getActivity().startActivity(myIntent);
    }

    public void startPULSE(View v) {
        Intent myIntent = new Intent(getActivity(), PulseCalculator.class);
        myIntent.putExtra("key", "2"); //Optional parameters
        getActivity().startActivity(myIntent);
    }

    public void startPULSE_FromCard(View v) {
        Intent myIntent = new Intent(getActivity(), PulseCalculator.class);
        myIntent.putExtra("key", "2"); //Optional parameters
        getActivity().startActivity(myIntent);
    }


}