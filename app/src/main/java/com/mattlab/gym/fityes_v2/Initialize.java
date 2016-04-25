package com.mattlab.gym.fityes_v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by EXT on 2016. 04. 25..
 * Author: Máté Németh
 */
public class Initialize extends AppCompatActivity {

    public boolean isLoggedin;
    public boolean firstStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_initialize);
        AppEventsLogger.activateApp(this);

        isLoggedin = true;
        firstStep = true;

        if (isLoggedin) {
            init();
        }

        Button btn = (Button) findViewById(R.id.btn_reg);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reg(v);
            }
        });
    }

    public void Reg(View v){
        Intent myIntent = new Intent(Initialize.this, Registration.class);
        myIntent.putExtra("key", "2"); //Optional parameters
        Initialize.this.startActivity(myIntent);
    }

    public void init() {
        if (isLoggedin == true && firstStep == true) {
            Intent myIntent = new Intent(Initialize.this, MenuActivity.class);
            myIntent.putExtra("loggedin", true); //loggedin igaz
            Initialize.this.startActivity(myIntent);
        } else if (firstStep == false) {
            Intent myIntent = new Intent(Initialize.this, FirstStep.class);
            myIntent.putExtra("loggedin", true); //loggedin igaz
            Initialize.this.startActivity(myIntent);
        }
    }
}
