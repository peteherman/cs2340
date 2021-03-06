package com.example.gourn.buzztracker.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gourn.buzztracker.Model.UserType;
import com.example.gourn.buzztracker.R;

import java.util.Objects;

public class LocationDescriptionActivity extends AppCompatActivity {
//    private Button addDonationButton;
//    private Button donationListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button addDonationButton;
        Button donationListButton;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_description);

        String locationName = getIntent().getStringExtra("EXTRA_LOCATION_NAME");
        String locationLatitude = getIntent().getStringExtra("EXTRA_LOCATION_LATITUDE");
        String locationLongitude = getIntent().getStringExtra("EXTRA_LOCATION_LONGITUDE");
        String locationAddress = getIntent().getStringExtra("EXTRA_LOCATION_ADDRESS");
        String locationType = getIntent().getStringExtra("EXTRA_LOCATION_TYPE");
        String locationPhoneNum = getIntent().getStringExtra("EXTRA_LOCATION_PHONE_NUM");
        String locationWebsite = getIntent().getStringExtra("EXTRA_LOCATION_WEBSITE");

        final String[] locationVariables = {"Name", "Latitude", "Longitude",
                "Address", "Type", "Phone Number", "Website"};
        final String[] locationValues = {locationName,
                locationLatitude, locationLongitude, locationAddress,
                locationType, locationPhoneNum, locationWebsite};
        ListAdapter descriptionAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, locationVariables) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                text1.setText(locationVariables[position]);
                text2.setText(locationValues[position]);
                return view;
            }
        };

        ListView descriptionList = findViewById(R.id.location_description_view);
        descriptionList.setAdapter(descriptionAdapter);

        final Button locationsButton = findViewById(R.id.locations_button);
        locationsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Code here executes on main thread after user presses button
                returnToLocations();

            }

        });

        //Set onclick listener for donation button
        addDonationButton = findViewById(R.id.add_donation);
        addDonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddDonationClick();
            }
        });

        //Check user type, if not location employee, set addDonation button visible to false
        if ((getIntent().getExtras() != null) &&
                (getIntent().getExtras().getInt("USER_TYPE")
                        != (UserType.LOCATION_EMPLOYEE.ordinal()))) {

            addDonationButton.setVisibility(View.GONE);
        }

        //Set onclick listener for donationlist button
        donationListButton = findViewById(R.id.donation_list_button);
        donationListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDonationListClick();
            }
        });


    }

    private void returnToLocations() {
        Intent intent = new Intent(this, LocationsList.class);
        Bundle bundle = buildIntentBundle();
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void onAddDonationClick() {
        String locationName = getIntent().getStringExtra("EXTRA_LOCATION_NAME");

        Intent intent = new Intent(this, DonationItemActivity.class);
        Bundle bundle = buildIntentBundle();
        bundle.putString("LOCATION_NAME", locationName);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private Bundle buildIntentBundle() {
        Bundle bundle = new Bundle();
        int userType = Objects.requireNonNull(getIntent().getExtras()).getInt("USER_TYPE");
        bundle.putInt("USER_TYPE", userType);
        return  bundle;
    }

    private void onDonationListClick() {
        String locationName = getIntent().getStringExtra("EXTRA_LOCATION_NAME");

        Intent intent = new Intent(this, DonationListView.class);
        Bundle bundle = new Bundle();
        int userType = Objects.requireNonNull(getIntent().getExtras()).getInt("USER_TYPE");
        bundle.putInt("USER_TYPE", userType);
        bundle.putString("LOCATION_NAME", locationName);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }
}
