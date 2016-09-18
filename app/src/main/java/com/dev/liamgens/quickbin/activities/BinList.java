package com.dev.liamgens.quickbin.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dev.liamgens.quickbin.R;
import com.dev.liamgens.quickbin.adapters.BinListAdapter;
import com.dev.liamgens.quickbin.objects.Bin;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class BinList extends AppCompatActivity implements View.OnClickListener{


    RecyclerView binList;
    FloatingActionButton addBin;
    Toolbar toolbar;
    BinListAdapter binListAdapter;
    ArrayList<Bin> bins = new ArrayList<>();
    final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 231;

    double longitude = 0;
    double latitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_list);



        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();

        binList = (RecyclerView) findViewById(R.id.bin_list_recycler_view);
        addBin = (FloatingActionButton) findViewById(R.id.bin_list_add_bin);
        toolbar = (Toolbar) findViewById(R.id.bin_list_toolbar);
        toolbar.setTitle("QuickBin");
        setSupportActionBar(toolbar);
        addBin.setOnClickListener(this);

        binListAdapter = new BinListAdapter(bins);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        binList.setLayoutManager(mLayoutManager);
        binList.setItemAnimator(new DefaultItemAnimator());
        binList.setAdapter(binListAdapter);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("bins");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Bin bin = dataSnapshot.getValue(Bin.class);
                bins.add(bin);
                binListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                Bin bin = dataSnapshot.getValue(Bin.class);
                bins.remove(bin);
                binListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        binListAdapter.setItemClickListener(new BinListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Bin bin) {
                Intent intent = new Intent(BinList.this, DetailedBin.class);
                intent.putExtra("binID", bin.get_id());
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bin_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_profile :

                startActivity(new Intent(BinList.this, ProfilePage.class));

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bin_list_add_bin :

                startActivity(new Intent(BinList.this, AddBin.class));

                break;
        }
    }


}