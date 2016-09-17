package com.dev.liamgens.quickbin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.dev.liamgens.quickbin.R;
import com.squareup.picasso.Picasso;

public class ProfilePage extends AppCompatActivity {
    private ImageView _profilePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        _profilePicture = (ImageView) findViewById(R.id.profile_picture);
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(_profilePicture);

    }
}
