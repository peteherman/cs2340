package com.example.gourn.buzztracker.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gourn.buzztracker.Model.DefaultDonationCategories;
import com.example.gourn.buzztracker.R;

public class DonationItemActivity extends AppCompatActivity {
    private TextView locationTextView;
    private Spinner categorySpinner;
    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_item);

        //set view elements
        locationTextView = findViewById(R.id.location_textview);
        categorySpinner = findViewById(R.id.category_spinner);
        backButton = findViewById(R.id.back_button);

        //Set up category spinner
        ArrayAdapter<String> csAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, DefaultDonationCategories.values());
        categorySpinner.setAdapter(csAdapter);

        //Set page title to location selected
        String locationSelected;
        if (getIntent().getExtras() != null &&
                getIntent().getExtras().get("LOCATION_NAME") != null)  {
            locationSelected = getIntent().getExtras().get("LOCATION_NAME").toString();
        } else {
            locationSelected = "Location";
        }
        locationTextView.setText(locationSelected);

        //Set up onClickListener for Back Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBackButton(v);
            }
        });

    }

    private void onClickBackButton(View v) {
        Intent intent = new Intent(this, LocationsList.class);
        Bundle bundle = new Bundle();
        bundle.putInt("USER_TYPE", getIntent().getExtras().getInt("USER_TYPE"));
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
