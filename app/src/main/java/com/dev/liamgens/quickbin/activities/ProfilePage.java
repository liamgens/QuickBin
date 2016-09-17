package com.dev.liamgens.quickbin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.liamgens.quickbin.R;
import com.dev.liamgens.quickbin.objects.Profile;
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
        Picasso.with(this).load("https://lh3.googleusercontent.com/Xb_HzSiWAsYDy-9uB03fVlFrAnUx1SRmD2DwB2JAvBBANBvypC03HR8Mnf7RNSg_iu8C8vl3SA=w5760-h3600-rw-no").
                transform(new CropCircleTransformation()).into(_profilePicture);

        Profile p = new Profile("Liam Gensel", 6);

        _displayName = (TextView) findViewById(R.id.display_name);
        _displayName.setText(p.get_displayName());

        _levelTitle = (TextView) findViewById(R.id.level_title);
        _levelTitle.setText(p.get_levelTitle());

        _level = (TextView) findViewById(R.id.level);
        _level.setText("Level " + p.get_level());




    }
}
