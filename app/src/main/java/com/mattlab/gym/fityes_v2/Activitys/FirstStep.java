package com.mattlab.gym.fityes_v2.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mattlab.gym.fityes_v2.R;

public class FirstStep extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firststep);

        Button btn = (Button) findViewById(R.id.btn_save_next_first);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextStep(v);
            }
        });
    }

    public void NextStep(View v) {
        Intent myIntent = new Intent(FirstStep.this, SecondStep.class);
        myIntent.putExtra("key", "2"); //Optional parameters
        FirstStep.this.startActivity(myIntent);
    }
}
