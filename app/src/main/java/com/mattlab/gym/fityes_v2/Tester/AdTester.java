package com.mattlab.gym.fityes_v2.Tester;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mattlab.gym.fityes_v2.R;

public class AdTester extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adtester);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Button btn = (Button) findViewById(R.id.full_banner);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_banner(v);
            }
        });
    }

    public void btn_banner(View v) {
        Intent myIntent = new Intent(AdTester.this, FullAd.class);
        AdTester.this.startActivity(myIntent);
    }
}
