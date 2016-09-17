package com.dev.liamgens.quickbin.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dev.liamgens.quickbin.R;

public class BinList extends AppCompatActivity implements View.OnClickListener{


    RecyclerView binList;
    FloatingActionButton addBin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_list);

        binList = (RecyclerView) findViewById(R.id.bin_list_recycler_view);
        addBin = (FloatingActionButton) findViewById(R.id.bin_list_add_bin);
        addBin.setOnClickListener(this);

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
