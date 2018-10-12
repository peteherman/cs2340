package com.example.gourn.buzztracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_description);

        String[] locationVariables = {"Location Name", "Location Type", "Latitude", "Longitude", "Address", "Phone Number"};
        String[] test = {"This", "is", "a", "test", "for", "the", "ListView"};
        ListAdapter descriptionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locationVariables);
        ListView descriptionList = findViewById(R.id.location_description_view);
        descriptionList.setAdapter(descriptionAdapter);
    }
}
