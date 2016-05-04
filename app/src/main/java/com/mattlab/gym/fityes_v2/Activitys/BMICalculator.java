package com.mattlab.gym.fityes_v2.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mattlab.gym.fityes_v2.R;

public class BMICalculator extends AppCompatActivity {

    public float height;
    public float weight;

    public float bmi_height;
    public float bmi;

    TextView bmi_Text;
    TextView bmi_Cat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        height = 150;
        weight = 60;

        bmi_Text = (TextView) findViewById(R.id.bmi_TextView);
        bmi_height = height / 100;
        bmi = weight / (bmi_height * bmi_height);

        bmi_Text.setText("Az Ön BMI értéke: " + bmi);


        //Kategória kiszámitása
        bmi_Cat = (TextView) findViewById(R.id.bmi_cat);

        if (bmi > 20.5 && bmi < 26.5) {
            //Normál
            bmi_Cat.setText("Normál");
        } else if (bmi > 26.6 && bmi < 31.9) {
            //Túlsúly
            bmi_Cat.setText("Túlsúly");
        } else if (bmi > 32.0 && bmi < 36.9) {
            //I. fokú elhízás
            bmi_Cat.setText("I. fokú elhízás");
        } else if (bmi > 37.0 && bmi < 41.9) {
            //II. fokú elhízás
            bmi_Cat.setText("II. fokú elhízás");
        } else if (bmi > 42.0) {
            //III. fokú elhízás
            bmi_Cat.setText("III. fokú elhízás");
        }



    }
}
