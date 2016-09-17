package com.dev.liamgens.quickbin.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dev.liamgens.quickbin.R;

public class BinList extends AppCompatActivity implements View.OnClickListener{


    FloatingActionButton addBin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_list);

        addBin = (FloatingActionButton) findViewById(R.id.bin_list_add_bin);
        addBin.setOnClickListener(this);

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
