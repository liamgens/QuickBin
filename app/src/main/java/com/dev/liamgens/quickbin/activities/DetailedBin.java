package com.dev.liamgens.quickbin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dev.liamgens.quickbin.R;
import com.dev.liamgens.quickbin.objects.Bin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailedBin extends AppCompatActivity {

    String id;
    FirebaseDatabase database;
    DatabaseReference reference;

    TextView title, description, user, date;



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


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Bin bin = dataSnapshot.getValue(Bin.class);
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
}
