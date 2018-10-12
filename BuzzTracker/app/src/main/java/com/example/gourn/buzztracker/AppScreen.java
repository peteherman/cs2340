package com.example.gourn.buzztracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppScreen extends AppCompatActivity {
    private Button logoutButton;
    private Button locationsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_screen);

        logoutButton = findViewById(R.id.log_out_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLogOutButton(v);
            }
        });

        locationsButton = findViewById(R.id.locationsButton);
        locationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLocationsButton(v);
            }
        });

    }

    private void clickLogOutButton(View view) {
        Intent intent = new Intent(this, Welcome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void clickLocationsButton(View view) {
        Intent intent = new Intent(this, LocationsList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
