package com.mattlab.gym.fityes_v2.Activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.mattlab.gym.fityes_v2.R;

import java.util.ArrayList;

public class Excersize extends Activity {
    VideoView vidView;
    int VidId = 0;
    String vidAddress;
    ArrayList<String> video_links;
    int video_links_Num;
    int i; //for while

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excersize);
        Log.e("Activity, EXCERSIZE", "Started");

        i = 0;

        vidView = (VideoView) findViewById(R.id.excersize_video);
        video_links = new ArrayList<String>();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.e("Nincs hiba", "Tömb feltöltve");

            //Előző activityből áthozott tömb lekérése
            video_links = extras.getStringArrayList("links");

            //Video link tömbjének számossága
            video_links_Num = video_links.size();

            //A TÖMB számosságának kiiratása
            Log.e("VIDEO LINKS", "TÖMB Számossága" + video_links.size());

            //Videó ciklus inditása
            PlayVideo();
        } else {

            //Link nélküli activity inditáskor visszaléptet a lekérdezéshez
            Log.e("HIBA", "A tömb nincs feltöltve");
            Intent myIntent = new Intent(Excersize.this, ExcersizeList.class);
            myIntent.putExtra("error", 0x1); //loggedin igaz
            Excersize.this.startActivity(myIntent);
        }


        //VIDEO kezelő
        vidView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                i++;
                Log.e("Videó teszt", "Videó vége, Száma: " + VidId);

                //Elfogyó videók ellenőrzése
                if (i == video_links.size()) {
                    //Ha vége az edzésnek

                    //TODO Edzés log beállitása
                    Toast.makeText(Excersize.this, "A videók lejátszása befejeződött", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(Excersize.this, MenuActivity.class);
                    myIntent.putExtra("error", 0x1); //loggedin igaz
                    Excersize.this.startActivity(myIntent);
                } else {

                    Alert();

                }//teszt
            }
        });

    }

    public void PlayVideo() {

        Log.e("PlayVideo", "Függvény elindult");


        //CIKLUS -> Ellenőrzés
        Log.e("PlayVideo", "Ciklus elindult, futásszáma: " + i);

        //LINK -> Ellenőrzés
        Log.e("PlayVideo", "Link: " + video_links.get(i));
        vidAddress = video_links.get(i);

        Uri vidUri = Uri.parse(vidAddress);

        //Media controller
        vidView.setVideoURI(vidUri);
        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);
        vidView.start();

        final ProgressDialog progDailog = ProgressDialog.show(this, "Kérem várjon...", "A videó betöltés alatt...", true);

        vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {

                progDailog.dismiss();

            }
        });


    }

    public void Alert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Indulhat a következő videó?");
        //    alert.setMessage("Message");


        alert.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                PlayVideo();
            }
        });
        alert.show();
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
