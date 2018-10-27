package com.example.gourn.buzztracker.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gourn.buzztracker.Model.DefaultDonationCategories;
import com.example.gourn.buzztracker.Model.Donation;
import com.example.gourn.buzztracker.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;

public class DonationItemActivity extends AppCompatActivity {
    private TextView locationTextView;
    private Spinner categorySpinner;
    private Button backButton;
    private Button addButton;
    private EditText shortDescriptionField;
    private EditText longDescriptionField;
    private EditText valueField;
    private EditText quantityField;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_item);

        //set view elements
        locationTextView = findViewById(R.id.location_textview);
        categorySpinner = findViewById(R.id.category_spinner);
        backButton = findViewById(R.id.back_button);
        addButton = findViewById(R.id.add_button);
        shortDescriptionField = findViewById(R.id.short_description_field);
        longDescriptionField = findViewById(R.id.long_description_field);
        valueField = findViewById(R.id.value_field);
        quantityField = findViewById(R.id.qty);

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

        //Set up onClickListener for Add Button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit(v);
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

    private void onSubmit(View v) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String locationName = getIntent().getExtras().getString("LOCATION_NAME");
        String shortDescription = shortDescriptionField.getText().toString();
        String longDescription = longDescriptionField.getText().toString();
        String valueString = valueField.getText().toString();
        int quantity = Integer.parseInt(quantityField.getText().toString());
        Log.d("Value field", valueString);
        double value = Double.parseDouble(valueString);
        DefaultDonationCategories category = (DefaultDonationCategories)categorySpinner.getSelectedItem();

        //Check to make sure all fields were entered
        if (shortDescription.isEmpty()) {
            shortDescriptionField.setError("Short Description not entered");
            shortDescriptionField.requestFocus();
            return;
        }
        if (longDescription.isEmpty()) {
            longDescriptionField.setError("Long Description was not entered");
            longDescriptionField.requestFocus();
            return;
        }

        Donation donation = new Donation(timestamp, locationName, shortDescription,
                longDescription, value, category, quantity);

        /*
        *
        *
        *
        * Put donation into database here
        *
        *
        *
        *
         */
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("donations").child(donation.getLocation()).push().setValue(donation);

        Intent intent = new Intent(this, LocationsList.class);
        Bundle bundle = new Bundle();
        bundle.putInt("USER_TYPE", getIntent().getExtras().getInt("USER_TYPE"));
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
