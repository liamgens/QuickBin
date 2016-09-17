package com.dev.liamgens.quickbin.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.liamgens.quickbin.R;
import com.dev.liamgens.quickbin.objects.Bin;
import com.dev.liamgens.quickbin.objects.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Date;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropTransformation;
import si.virag.fuzzydateformatter.FuzzyDateTimeFormatter;

public class DetailedBin extends AppCompatActivity implements View.OnClickListener {

    String id;
    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference usersReference;

    TextView title, description, user, date;
    ImageView garbagePicture;
    Toolbar toolbar;

    Button beenHere;

    LinearLayout showOnMapButton;

    private Bin bin;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_bin);

        id = getIntent().getExtras().getString("binID");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("bins/" + id);

        title = (TextView) findViewById(R.id.bin_title_detailed);
        description = (TextView) findViewById(R.id.description_detailed);
        user = (TextView) findViewById(R.id.user_detailed);
        date = (TextView) findViewById(R.id.date_detailed);
        toolbar = (Toolbar) findViewById(R.id.detailed_bin_toolbar);

        beenHere = (Button) findViewById(R.id.been_here);
        beenHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetailedBin.this)
                        .setPositiveButton("Yes, I have used it.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editBin(1);
                                //dialog.dismiss();
                            }

                        })
                        .setNegativeButton("No, it doesn't exist.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(bin.get_verifyCounter() > 0){
                                    editBin(-1);
                                }
                                //dialog.dismiss();
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");

        showOnMapButton = (LinearLayout) findViewById(R.id.detailed_bin_show_on_map);
        showOnMapButton.setOnClickListener(this);

        garbagePicture = (ImageView) findViewById(R.id.garbage_img);



        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bin = dataSnapshot.getValue(Bin.class);

                usersReference = database.getReference("users/" + bin.get_user());
                Date datePosted = new Date(bin.get_date());
                String text = FuzzyDateTimeFormatter.getTimeAgo(getApplicationContext(), datePosted);

                title.setText(bin.get_title());
                description.setText(bin.get_description());

                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Profile profile = dataSnapshot.getValue(Profile.class);
                        user.setText("Submitted by " + profile.get_displayName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                date.setText(text);
                Picasso.with(getApplicationContext()).load(bin.get_binPicture()).into(garbagePicture);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detailed_bin_show_on_map:

                if (bin != null) {
                    Uri gmmIntentUri = Uri.parse("geo:" + bin.get_latitude() + "," + bin.get_longitude() + "?z=20");
                      Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }

                    String label = bin.get_title();
                    String uriBegin = "geo:" + bin.get_latitude() + "," + bin.get_longitude();
                    String query = bin.get_latitude() + "," + bin.get_longitude() + "(" + label + ")";
                    String encodedQuery = Uri.encode( query  );
                    String uriString = uriBegin + "?q=" + encodedQuery;
                    Uri uri = Uri.parse( uriString );
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri );
                    startActivity( intent );
                }

                break;
        }
    }

    public void editBin(final int i){

        reference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                Bin bin = mutableData.getValue(Bin.class);
                if (bin == null) {
                    return Transaction.success(mutableData);
                }

                bin.set_verifyCounter(bin.get_verifyCounter() + i);

                mutableData.setValue(bin);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });


        usersReference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                Profile profile = mutableData.getValue(Profile.class);
                if (profile == null) {
                    return Transaction.success(mutableData);
                }

                profile.set_level(profile.get_level() + i);

                mutableData.setValue(profile);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });

    }
}