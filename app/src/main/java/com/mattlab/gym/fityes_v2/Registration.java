package com.mattlab.gym.fityes_v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button btn = (Button) findViewById(R.id.btn_reg_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_reg_next(v);
            }
        });
    }

    public void btn_reg_next(View v) {
        Intent myIntent = new Intent(Registration.this, Reg_Second.class);
        myIntent.putExtra("key", "2"); //Optional parameters
        Registration.this.startActivity(myIntent);
    }
}
