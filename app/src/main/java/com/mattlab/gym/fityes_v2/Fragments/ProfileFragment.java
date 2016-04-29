package com.mattlab.gym.fityes_v2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.ProfilePictureView;
import com.mattlab.gym.fityes_v2.Activitys.Initialize;
import com.mattlab.gym.fityes_v2.R;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午1:31
 * Mail: specialcyci@gmail.com
 */
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
            profileName.setText("Név: " + profile.getName());
            String id = profile.getId();

            ProfilePictureView profileImage;

            profileImage = (ProfilePictureView) rootView.findViewById(R.id.profilePicture);
            profileImage.setProfileId(profile.getId());

        }
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

}
