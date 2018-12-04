package com.example.gourn.buzztracker.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gourn.buzztracker.R;
import com.google.firebase.auth.FirebaseAuth;

public class AppScreen extends AppCompatActivity {
//    private Button logoutButton;
//    private Button locationsButton;
//    private Button searchButton;
    private int userType;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onBackPressed() {
        mAuth.signOut();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button logoutButton;
        Button locationsButton;
        Button searchButton;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_screen);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(AppScreen.this, Welcome.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        };

        logoutButton = findViewById(R.id.log_out_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLogOutButton();
            }
        });

        locationsButton = findViewById(R.id.locationsButton);
        locationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLocationsButton();
            }
        });
        if (getIntent().getExtras() != null) {
            userType = getIntent().getExtras().getInt("USER_TYPE");
        } else {
            userType = -1;
        }


        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSearch();
            }
        });
    }

    private void clickLogOutButton() {
        mAuth.signOut();
    }

    private void clickLocationsButton() {
        Intent intent = new Intent(this, LocationsList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putInt("USER_TYPE", userType);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void onClickSearch() {
        Intent intent = new Intent(this, SearchView.class);
        Bundle bundle = new Bundle();
        bundle.putInt("USER_TYPE", userType);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }
}
