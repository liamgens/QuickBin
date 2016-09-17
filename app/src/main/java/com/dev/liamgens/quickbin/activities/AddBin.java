package com.dev.liamgens.quickbin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dev.liamgens.quickbin.R;
import com.dev.liamgens.quickbin.objects.Bin;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class AddBin extends AppCompatActivity implements View.OnClickListener{

    EditText title, description;
    Button addBin;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bin);

        title = (EditText) findViewById(R.id.add_bin_title);
        description = (EditText) findViewById(R.id.add_bin_description);
        addBin = (Button) findViewById(R.id.add_bin_button);
        addBin.setOnClickListener(this);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("bins");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add_bin_button :
                saveBin();
                break;
        }

    }

    private void saveBin() {
        String titleString = title.getText().toString();
        String descriptionString = description.getText().toString();

        Date date = new Date();
        Bin bin = new Bin(0, "ianleshan71", titleString, descriptionString, 43.244, -42.342, 2, "imgur.com", date.toString());

        DatabaseReference binReference = myRef.push();
        String id = binReference.getKey();
        binReference.setValue(bin);
    }
}
