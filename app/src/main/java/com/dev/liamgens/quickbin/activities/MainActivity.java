package com.dev.liamgens.quickbin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dev.liamgens.quickbin.R;
import com.dev.liamgens.quickbin.objects.Profile;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    final int RC_SIGN_IN = 120;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in

            startActivity(new Intent(this, BinList.class));
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(
                                    AuthUI.GOOGLE_PROVIDER).setLogo(R.drawable.quick_bin_logo)
                            .setTheme(R.style.GreenTheme)
                            .build(),
                    RC_SIGN_IN);
        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // user is signed in!

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("users/" + user.getUid());

                Profile profile = new Profile(user.getDisplayName(), user.getPhotoUrl().toString(), 1);
                myRef.setValue(profile);


                startActivity(new Intent(this, BinList.class));
                finish();
            } else {
                // user is not signed in. Maybe just wait for the user to press
                // "sign in" again, or show a message
            }
        }
    }
}
