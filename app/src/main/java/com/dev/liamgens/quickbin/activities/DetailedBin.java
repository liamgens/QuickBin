package com.dev.liamgens.quickbin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dev.liamgens.quickbin.R;

public class DetailedBin extends AppCompatActivity {

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_bin);

        id = getIntent().getExtras().getString("binID");
    }
}
