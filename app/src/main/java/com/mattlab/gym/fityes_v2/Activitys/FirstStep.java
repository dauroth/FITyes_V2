package com.mattlab.gym.fityes_v2.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mattlab.gym.fityes_v2.R;

public class FirstStep extends AppCompatActivity {

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private EditText editAge;

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

        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);
        editAge = (EditText) findViewById(R.id.editAge);

        if (radioSexButton != null) {

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            Toast.makeText(FirstStep.this,
                    radioSexButton.getText(), Toast.LENGTH_SHORT).show();

            Intent myIntent = new Intent(FirstStep.this, SecondStep.class);
            myIntent.putExtra("key", "2"); //Optional parameters
            FirstStep.this.startActivity(myIntent);

        } else {
            Toast.makeText(FirstStep.this,
                    "Nincs kijelölve a neme", Toast.LENGTH_SHORT).show();
        }
    }
}
