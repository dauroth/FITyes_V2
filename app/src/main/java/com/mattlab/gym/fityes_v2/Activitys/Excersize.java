package com.mattlab.gym.fityes_v2.Activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.mattlab.gym.fityes_v2.R;

public class Excersize extends Activity {
    VideoView vidView;
    int VidId = 0;
    String vidAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excersize);
        Log.e("Activity, EXCERSIZE", "Started");

        vidView = (VideoView) findViewById(R.id.excersize_video);

        PlayVideo();


        vidView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                VidId++;
                Log.e("Videó teszt", "Videó vége, Száma: " + VidId);
                PlayVideo();
            }
        });

    }

    public void PlayVideo() {

        if (VidId == 0) {

            vidAddress = "http://ext.hu/videos/armfat.mp4";
        } else if (VidId == 1) {
            vidAddress = "http://ext.hu/videos/sixpack.mp4";
        } else if (VidId == 2) {
            vidAddress = "http://ext.hu/videos/butt.mp4";
        } else if (VidId == 3) {
            vidAddress = "http://ext.hu/videos/fatlose.mp4";
        }

        Uri vidUri = Uri.parse(vidAddress);


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
}
