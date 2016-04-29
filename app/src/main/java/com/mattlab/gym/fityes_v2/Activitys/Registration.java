package com.mattlab.gym.fityes_v2.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mattlab.gym.fityes_v2.R;

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
        String textName;
        String textMail;
        String textDate;
        EditText editName;
        EditText editMail;
        EditText editDate;

        editName = (EditText) findViewById(R.id.password);
        editMail = (EditText) findViewById(R.id.textMail);
        editDate = (EditText) findViewById(R.id.secondPassword);

        textName = editName.getText().toString();
        textMail = editMail.getText().toString();
        textDate = editDate.getText().toString();

        Log.e("Név:", textName);
        Log.e("E-mail cim:", textMail);
        Log.e("Születési dátum:", textDate);



        Intent myIntent = new Intent(Registration.this, Reg_Second.class);
        myIntent.putExtra("name", textName); //Optional parameters
        myIntent.putExtra("mail", textMail); //Optional parameters
        myIntent.putExtra("date", textDate); //Optional parameters
        Registration.this.startActivity(myIntent);
    }

}