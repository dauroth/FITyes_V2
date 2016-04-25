package com.mattlab.gym.fityes_v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by EXT on 2016. 04. 25..
 * Author: Máté Németh
 */
public class Initialize extends AppCompatActivity {

    public boolean login;
    public boolean firstep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_initialize);
        AppEventsLogger.activateApp(this);

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
}
