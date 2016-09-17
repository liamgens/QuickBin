package com.dev.liamgens.quickbin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.liamgens.quickbin.R;
import com.dev.liamgens.quickbin.objects.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProfilePage extends AppCompatActivity {
    private ImageView _profilePicture, _levelIcon;
    private TextView _displayName;
    private TextView _levelTitle;
    private TextView _level;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        _profilePicture = (ImageView) findViewById(R.id.profile_picture);
        _displayName = (TextView) findViewById(R.id.display_name);
        _levelTitle = (TextView) findViewById(R.id.level_title);
        _level = (TextView) findViewById(R.id.level);
        _levelIcon = (ImageView) findViewById(R.id.level_icon);




        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            FirebaseUser user = auth.getCurrentUser();
            ref = FirebaseDatabase.getInstance().getReference("users/" + user.getUid());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Profile profile = dataSnapshot.getValue(Profile.class);
                    _level.setText("Level " + profile.get_level());
                    _levelTitle.setText(profile.get_levelTitle());
                   if(_levelTitle.getText().equals("Sapling")){
                        Picasso.with(getApplicationContext()).load(R.drawable.sapling_icon).transform(new CropCircleTransformation()).into(_levelIcon);
                    }else if (_levelTitle.getText().equals("Tree")){
                        Picasso.with(getApplicationContext()).load(R.drawable.tree_icon).transform(new CropCircleTransformation()).into(_levelIcon);
                    }else{
                       Picasso.with(getApplicationContext()).load(R.drawable.seed_icon).transform(new CropCircleTransformation()).into(_levelIcon);
                   }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            Picasso.with(this).load(user.getPhotoUrl()).
                    transform(new CropCircleTransformation()).into(_profilePicture);

            _displayName.setText(user.getDisplayName());

        } else {
            // not signed in
        }




    }
}
