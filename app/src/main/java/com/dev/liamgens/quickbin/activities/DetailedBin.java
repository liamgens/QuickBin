package com.dev.liamgens.quickbin.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.liamgens.quickbin.R;
import com.dev.liamgens.quickbin.objects.Bin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropTransformation;

public class DetailedBin extends AppCompatActivity implements View.OnClickListener{

    String id;
    FirebaseDatabase database;
    DatabaseReference reference;

    TextView title, description, user, date;
    ImageView garbagePicture;

    ImageButton showOnMapButton;

    private Bin bin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_bin);

        id = getIntent().getExtras().getString("binID");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("bins/" + id);

        title = (TextView) findViewById(R.id.bin_title_detailed);
        description = (TextView) findViewById(R.id.description_detailed);
        user = (TextView) findViewById(R.id.user_detailed);
        date = (TextView) findViewById(R.id.date_detailed);
        showOnMapButton = (ImageButton) findViewById(R.id.detailed_bin_show_on_map);
        showOnMapButton.setOnClickListener(this);

        garbagePicture = (ImageView) findViewById(R.id.garbage_img);

        Picasso.with(this).load("http://www.wcnorthwest.com/Resources_Local/Images/garbage%20can.png").into(garbagePicture);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bin = dataSnapshot.getValue(Bin.class);
                title.setText(bin.get_title());
                description.setText(bin.get_description());
                user.setText("Submitted by " + bin.get_user());
                date.setText(" on " + bin.get_date());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detailed_bin_show_on_map:

                if(bin != null) {
                    Uri gmmIntentUri = Uri.parse("geo:"+ bin.get_latitude() +","+ bin.get_longitude() + "?z=20" );
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                }

            break;
        }
    }
}
