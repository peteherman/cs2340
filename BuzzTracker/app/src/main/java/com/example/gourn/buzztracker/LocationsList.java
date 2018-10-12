package com.example.gourn.buzztracker;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class LocationsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_list);
        DB_Handler db = new DB_Handler(this.getApplicationContext(), null, null, 1);
        try {
            csvParse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Location[] locations = db.getAllLocations();
        String[] locationNames = new String[locations.length];
        for (int i = 0; i < locations.length; i++) {
            locationNames[i] = locations[i].getName();
            System.out.println(locationNames[i]);
        }
        ListView locationlist = (ListView) findViewById(R.id.LocationList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, locationNames);
        locationlist.setAdapter(adapter);
    }

    public void csvParse() throws IOException {

        ArrayList<String> lines = new ArrayList<>();
        DB_Handler db = new DB_Handler(this.getApplicationContext(),null,null,1);
        db.clearLocations();

        InputStream is = getResources().openRawResource(R.raw.locationdata);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line;
        buffer.readLine();
        while ((line = buffer.readLine()) != null) {
            lines.add(line);
        }
        String[][] data = new String[lines.size()][11];

        for (int i = 0; i < data.length; i++) {
            String[] currLine = lines.get(i).split(",");
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = currLine[j]; // putting csv data into a 2d array
            }
        }
        String columns = "Name, Latitude, Longitude, Address, Type, PhoneNum, Website";
        String str1 = "INSERT INTO Location" + " (" + columns + ") values(";
        String str2 = ");";
        for (int i = 0; i < data.length; i++) {
            StringBuilder sb = new StringBuilder(str1);
            sb.append("'" + data[i][1] + "', '");
            sb.append(data[i][2] + "', '");
            sb.append(data[i][3] + "', '");
            sb.append(data[i][4] + ", " + data[i][5] + ", " + data[i][6] + ", "+ data[i][7] + "', '");
            sb.append(data[i][8] + "', '");
            sb.append(data[i][9] + "', '");
            sb.append(data[i][10] + "'");
            sb.append(str2);
            System.out.println(sb);
            db.db.execSQL(sb.toString());
        }
    }
}
