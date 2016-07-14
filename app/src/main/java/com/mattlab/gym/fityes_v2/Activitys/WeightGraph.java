package com.mattlab.gym.fityes_v2.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mattlab.gym.fityes_v2.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class WeightGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_graph);

        BarChart mBarChart = (BarChart) findViewById(R.id.barchart);

        mBarChart.addBar(new BarModel(95, 0xFF123456));
        mBarChart.addBar(new BarModel(92, 0xFF343456));
        mBarChart.addBar(new BarModel(90, 0xFF563456));
        mBarChart.addBar(new BarModel(89, 0xFF873F56));
        mBarChart.addBar(new BarModel(89, 0xFF56B7F1));
        mBarChart.addBar(new BarModel(85, 0xFF343456));
        mBarChart.addBar(new BarModel(87, 0xFF1FF4AC));
        mBarChart.addBar(new BarModel(87, 0xFF1BA4E6));
    }
}
