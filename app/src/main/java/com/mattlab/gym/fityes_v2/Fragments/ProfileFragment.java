package com.mattlab.gym.fityes_v2.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.ProfilePictureView;
import com.mattlab.gym.fityes_v2.R;

import org.w3c.dom.Text;

import java.net.URL;

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
    CallbackManager callbackManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        CallbackManager callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // App code
                Log.d("current token", "" + currentAccessToken);

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

        View rootView = inflater.inflate(R.layout.profile, container, false);
        profileName = (TextView) rootView.findViewById(R.id.profileName);

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

}
