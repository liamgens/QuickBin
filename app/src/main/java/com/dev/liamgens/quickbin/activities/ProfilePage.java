package com.dev.liamgens.quickbin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.liamgens.quickbin.R;
import com.dev.liamgens.quickbin.objects.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProfilePage extends AppCompatActivity {
    private ImageView _profilePicture;
    private TextView _displayName;
    private TextView _levelTitle;
    private TextView _level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        _profilePicture = (ImageView) findViewById(R.id.profile_picture);
        _displayName = (TextView) findViewById(R.id.display_name);
        _levelTitle = (TextView) findViewById(R.id.level_title);
        _level = (TextView) findViewById(R.id.level);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            FirebaseUser user = auth.getCurrentUser();

            Picasso.with(this).load(user.getPhotoUrl()).
                    transform(new CropCircleTransformation()).into(_profilePicture);

            _displayName.setText(user.getDisplayName());

        } else {
            // not signed in
        }




    }
}
