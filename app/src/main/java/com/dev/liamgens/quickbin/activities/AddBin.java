package com.dev.liamgens.quickbin.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dev.liamgens.quickbin.R;
import com.dev.liamgens.quickbin.objects.Bin;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class AddBin extends AppCompatActivity implements View.OnClickListener {

    EditText title, description;
    TextInputLayout titleLayout, descriptionLayout;
    Button addBin;
    RadioGroup binsRadioGroup;
    ImageButton addImage, addLocation;
    ImageView picture;

    boolean takenPicture = false;
    Bitmap imageBitmap;
    StorageReference storageRef;

    FirebaseDatabase database;
    DatabaseReference myRef;

    FirebaseStorage storage;

    int type = 0;

    double longitude = 0;
    double latitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bin);

        title = (EditText) findViewById(R.id.add_bin_title);
        description = (EditText) findViewById(R.id.add_bin_description);
        titleLayout = (TextInputLayout) findViewById(R.id.add_bin_title_layout);
        descriptionLayout = (TextInputLayout) findViewById(R.id.add_bin_description_layout);
        addBin = (Button) findViewById(R.id.add_bin_button);
        binsRadioGroup = (RadioGroup) findViewById(R.id.add_bin_radio_group);
        addImage = (ImageButton) findViewById(R.id.add_bin_image);
        addLocation = (ImageButton) findViewById(R.id.add_bin_location);
        picture = (ImageView) findViewById(R.id.add_bin_picture);


        addBin.setOnClickListener(this);
        addLocation.setOnClickListener(this);
        addImage.setOnClickListener(this);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("bins");

        storage = FirebaseStorage.getInstance();

        binsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getId()) {
                    case R.id.add_bin_radio_button_garbage:
                        type = 0;
                        break;

                    case R.id.add_bin_radio_button_recycling:
                        type = 1;
                        break;

                    case R.id.add_bin_radio_button_recycling_station:
                        type = 2;
                        break;
                }
            }
        });


        storageRef = storage.getReferenceFromUrl("gs://quickbin-e9141.appspot.com");

        storageRef.child("bins/ocean.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getApplicationContext()).load(uri).into(picture);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add_bin_button:
                saveBin();
                break;
            case R.id.add_bin_image:

                takePicture();

                break;

            case R.id.add_bin_location:


                break;
        }

    }

    private void saveBin() {
        final String titleString = title.getText().toString().trim();
        final String descriptionString = description.getText().toString().trim();

        if (titleString.isEmpty()) {
            titleLayout.setError("You must have a title!");
            return;
        } else {
            titleLayout.setErrorEnabled(false);
        }

        if (descriptionString.isEmpty()) {
            descriptionLayout.setError("You must have a description!");
            return;
        } else {
            descriptionLayout.setErrorEnabled(false);
        }

        if(!takenPicture){
            Toast.makeText(AddBin.this, "You must take a picture!", Toast.LENGTH_SHORT).show();
            return;
        }else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();


            final DatabaseReference binReference = myRef.push();
            final String id = binReference.getKey();

            final Date date = new Date();
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null){
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }




            StorageReference ref = storageRef.child("bins/" + id);
            UploadTask uploadTask = ref.putBytes(data);
            Toast.makeText(AddBin.this, "Uploading...", Toast.LENGTH_SHORT).show();
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(AddBin.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Bin bin = new Bin(type, "ianleshan71", titleString, descriptionString, longitude, latitude, 0, downloadUrl.toString(), date.toString(), id);

                    binReference.setValue(bin);
                    finish();
                    Toast.makeText(AddBin.this, "Done", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            picture.setImageBitmap(imageBitmap);
            takenPicture = true;
        }
    }
}
