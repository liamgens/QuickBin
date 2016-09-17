package com.dev.liamgens.quickbin.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class BinList extends AppCompatActivity implements View.OnClickListener{


    RecyclerView binList;
    FloatingActionButton addBin;
    BinListAdapter binListAdapter;
    ArrayList<Bin> bins = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_list);

        binList = (RecyclerView) findViewById(R.id.bin_list_recycler_view);
        addBin = (FloatingActionButton) findViewById(R.id.bin_list_add_bin);
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
