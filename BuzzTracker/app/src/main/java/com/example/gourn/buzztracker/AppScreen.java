package com.example.gourn.buzztracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppScreen extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_screen);

        button = findViewById(R.id.log_out_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLogOutButton(v);
            }
        });
    }

    private void clickLogOutButton(View view) {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }
}
