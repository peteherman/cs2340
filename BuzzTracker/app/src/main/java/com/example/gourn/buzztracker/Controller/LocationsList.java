package com.example.gourn.buzztracker.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gourn.buzztracker.Controller.AppScreen;
import com.example.gourn.buzztracker.Controller.LocationDescriptionActivity;
import com.example.gourn.buzztracker.Model.Location;
import com.example.gourn.buzztracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LocationsList extends AppCompatActivity {
    private Button backButton;
    private Button mapButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_list);
        try {
            final Location[] locations = csvParse();
            String[] locationNames = new String[locations.length];
            for (int i = 0; i < locations.length; i++) {
                locationNames[i] = locations[i].getName();
                System.out.println(locationNames[i]);
            }
            ListView locationlist = (ListView) findViewById(R.id.LocationList);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, locationNames);
            locationlist.setAdapter(adapter);

            locationlist.setOnItemClickListener (
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            clickLocation(view, locations[position]);
                        }
                    }
            );

            mapButton = findViewById(R.id.map_button);
            mapButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clickMap(v, locations);
                }
            });
            backButton = (Button) findViewById(R.id.BackButton);
            backButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clickBackButton(v);
                }
            });} catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location[] csvParse() throws IOException {

        List<String> lines = new ArrayList<>();

        InputStream is = getResources().openRawResource(R.raw.location_data);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line;
        buffer.readLine();
        while ((line = buffer.readLine()) != null) {
            lines.add(line);
        }
        String[][] data = new String[lines.size()][11];
        Location[] locationsArr = new Location[lines.size()];
        for (int i = 0; i < data.length; i++) {
            String[] currLine = lines.get(i).split(",");
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = currLine[j]; // putting csv data into a 2d array
            }
        }
        for (int i = 0; i < data.length; i++) {
            final String locName = data[i][1];
            String locLatitude = data[i][2];
            String locLongitude = data[i][3];
            String locAddress = data[i][4] + ", " + data[i][5] + ", " + data[i][6] + ", "+ data[i][7];
            String locType = data[i][8];
            String locPhoneNum = data[i][9];
            String locWebsite = data[i][10];
            final Location location = new Location(locName,locLatitude,locLongitude,locAddress,locType,locPhoneNum,locWebsite);
            locationsArr[i] = location;
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Locations");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    boolean noChildren = true;
                    boolean exists = false;
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        //If email exists then toast shows else store the data on new key
                        noChildren = false;
                        Location loc = data.getValue(Location.class);
                        if (loc.getName().equals(locName)) {
                            exists = true;
                            break;
                        }
                    }
                    if(noChildren || !exists) {
                        databaseReference.child(databaseReference.push().getKey()).setValue(location);                    }
                }

                @Override
                public void onCancelled(final DatabaseError databaseError) {
                }
            });
        }
        return locationsArr;
    }

    private void clickLocation(View v, Location locationId) {
        Intent intent = new Intent(this, LocationDescriptionActivity.class);
        intent.putExtra("EXTRA_LOCATION_NAME", locationId.getName());
        intent.putExtra("EXTRA_LOCATION_LATITUDE", locationId.getLatitude());
        intent.putExtra("EXTRA_LOCATION_LONGITUDE", locationId.getLongitude());
        intent.putExtra("EXTRA_LOCATION_ADDRESS", locationId.getAddress());
        intent.putExtra("EXTRA_LOCATION_TYPE", locationId.getType());
        intent.putExtra("EXTRA_LOCATION_PHONE_NUM", locationId.getPhoneNum());
        intent.putExtra("EXTRA_LOCATION_WEBSITE", locationId.getWebsite());
        Bundle bundle = new Bundle();
        int userType = getIntent().getExtras().getInt("USER_TYPE");
        bundle.putInt("USER_TYPE", userType);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void clickMap(View v, Location[] locationsArray) {
        ArrayList<Location> locations = new ArrayList<>(Arrays.asList(locationsArray));
        Intent intent = new Intent(this, MapActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("LOCATIONS_ARRAY", locations);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void clickBackButton(View view) {
        Intent intent = new Intent(this, AppScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        int userType = getIntent().getExtras().getInt("USER_TYPE");
        bundle.putInt("USER_TYPE", userType);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
