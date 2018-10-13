package com.example.gourn.buzztracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_description);

        String locationLatitude = getIntent().getStringExtra("EXTRA_LOCATION_LATITUDE");
        String locationLongitude = getIntent().getStringExtra("EXTRA_LOCATION_LONGITUDE");
        String locationAddress = getIntent().getStringExtra("EXTRA_LOCATION_ADDRESS");
        String locationType = getIntent().getStringExtra("EXTRA_LOCATION_TYPE");
        String locationPhoneNum = getIntent().getStringExtra("EXTRA_LOCATION_PHONE_NUM");
        String locationWebsite = getIntent().getStringExtra("EXTRA_LOCATION_WEBSITE");

        final String[] locationVariables = {"Latitude", "Longitude", "Address", "Type", "Phone Number", "Address"};
        final String[] locationValues = {locationLatitude, locationLongitude, locationAddress, locationType, locationPhoneNum, locationWebsite};
        ArrayAdapter descriptionAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, locationVariables) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(locationVariables[position]);
                text2.setText(locationValues[position]);
                return view;
            }
        };

        ListView descriptionList = findViewById(R.id.location_description_view);
        descriptionList.setAdapter(descriptionAdapter);

        final Button locationsButton = findViewById(R.id.locations_button);
        locationsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // Code here executes on main thread after user presses button
                returnToLocations(v);

            }

        });
    }

    private void returnToLocations(View v) {
        Intent intent = new Intent(this, LocationsList.class);
        startActivity(intent);
        finish();
    }
}
