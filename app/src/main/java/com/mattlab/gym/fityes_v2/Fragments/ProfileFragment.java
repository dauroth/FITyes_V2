package com.mattlab.gym.fityes_v2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.ProfilePictureView;
import com.mattlab.gym.fityes_v2.Activitys.Initialize;
import com.mattlab.gym.fityes_v2.Activitys.WeightGraph;
import com.mattlab.gym.fityes_v2.R;


public class ProfileFragment extends Fragment {
    private View rootView;
    TextView profileName;
    ProfileTracker profTrack;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, container, false);
        profileName = (TextView) rootView.findViewById(R.id.profileName);


        FacebookInit();
        Profile profile;
        profile = Profile.getCurrentProfile();

        if (profile != null) {
            profileName.setText(profile.getName());
            Log.e("Profil ID", profile.getId());

            String id = profile.getId();

            ProfilePictureView profileImage;

            profileImage = (ProfilePictureView) rootView.findViewById(R.id.profilePicture);
            profileImage.setProfileId(profile.getId());

        }

        Button add_weight = (Button) getActivity().findViewById(R.id.add_weight);

        rootView.findViewById(R.id.add_weight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), WeightGraph.class);
                getActivity().startActivity(myIntent);
            }
        });

        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        profTrack.stopTracking();
    }


    public void FacebookInit() {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

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
                    Intent myIntent = new Intent(getActivity(), Initialize.class);
                    myIntent.putExtra("key", "2"); //Optional parameters
                    getActivity().startActivity(myIntent);
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

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

}
