package com.mattlab.gym.fityes_v2.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mattlab.gym.fityes_v2.R;

public class SecondStep extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondstep); //test

        Button btn = (Button) findViewById(R.id.next_step_second);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextStep_Second(v);
            }
        });
    }


    public void NextStep_Second(View v) {
        Intent myIntent = new Intent(SecondStep.this, ThirdStep.class);
        myIntent.putExtra("key", "2"); //Optional parameters
        SecondStep.this.startActivity(myIntent);
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
