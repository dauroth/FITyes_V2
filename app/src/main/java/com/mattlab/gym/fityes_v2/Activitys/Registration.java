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

 /*   ProfileTracker profTrack;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //FacebookInit();
        //Profile profile;
        //profile = Profile.getCurrentProfile();

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
/*
    public void FacebookInit() {
        FacebookSdk.sdkInitialize(getApplicationContext());

        accessToken = AccessToken.getCurrentAccessToken();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // App code
                Log.d("current token", "" + currentAccessToken);
                if (currentAccessToken == null) {
                    Log.e("Facebook", "Sign OUT");
                    Intent myIntent = new Intent(Registration.this, Initialize.class);
                    myIntent.putExtra("key", "2"); //Optional parameters
                    startActivity(myIntent);
                }
                //}
            }
        };
        profTrack = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code


                Log.d("current profile", "" + currentProfile);
            }
        };
    }
*/

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(Registration.this, Initialize.class);
        myIntent.putExtra("key", "2"); //Optional parameters
        Registration.this.startActivity(myIntent);
        finish();
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
