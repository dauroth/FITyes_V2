package com.mattlab.gym.fityes_v2.Activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;

import com.mattlab.gym.fityes_v2.Fragments.CalculatorFragment;
import com.mattlab.gym.fityes_v2.Fragments.CalendarFragment;
import com.mattlab.gym.fityes_v2.Fragments.GroupsFragment;
import com.mattlab.gym.fityes_v2.Fragments.HomeFragment;
import com.mattlab.gym.fityes_v2.Fragments.ProfileFragment;
import com.mattlab.gym.fityes_v2.Fragments.SettingsFragment;
import com.mattlab.gym.fityes_v2.R;
import com.mattlab.gym.fityes_v2.Utilities.Menu.ResideMenu;
import com.mattlab.gym.fityes_v2.Utilities.Menu.ResideMenuItem;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{

    private ResideMenu resideMenu;
    private MenuActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;
    private ResideMenuItem itemCalculator;
    private ResideMenuItem itemGroups;

    String user_id;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new HomeFragment());

        //Bundle
        Bundle extras = getIntent().getExtras();
        //ExtraString
        user_id = extras.getString("user_id");


    }


    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.9f);

        // create menu items;
        itemHome = new ResideMenuItem(this, R.drawable.icon_home, "Főoldal");
        itemProfile = new ResideMenuItem(this, R.drawable.icon_profile, "Profil");
        itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar, "Naptár");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "Beállitások");
        itemCalculator = new ResideMenuItem(this, R.drawable.icon_calendar, "Kalkulátorok");
        itemGroups = new ResideMenuItem(this, R.drawable.icon_groups, "Csoportok");

        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);
        itemCalculator.setOnClickListener(this);
        itemGroups.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalculator, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemGroups, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {

        changeFragment(new HomeFragment());
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeFragment(new HomeFragment());
        }else if (view == itemProfile){
            changeFragment(new ProfileFragment());
        }else if (view == itemCalendar){
            changeFragment(new CalendarFragment());
        }else if (view == itemSettings){
            changeFragment(new SettingsFragment());
        }else if (view == itemCalculator){
            changeFragment(new CalculatorFragment());
        } else if (view == itemGroups) {
            changeFragment(new GroupsFragment());
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
        //    Toast.makeText(mContext, "Menü megnyitva!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
        //    Toast.makeText(mContext, "Menü bezárva!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
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
