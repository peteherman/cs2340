package com.example.gourn.buzztracker.Controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gourn.buzztracker.Model.Location;
import com.example.gourn.buzztracker.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        back_button = (Button) findViewById(R.id.back_button);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBack(v);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng defaultCamera = new LatLng(33.753746, -84.386330);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultCamera, 10f));
        map.setInfoWindowAdapter(new CustomWindow(this));
        Bundle bundle = getIntent().getExtras();
        Iterable<Location> locations = bundle.getParcelableArrayList("LOCATIONS_ARRAY");
        String snippet;
        for (Location l : locations) {
            snippet = "Address: " + l.getAddress() + "\n" +
                      "Phone Number: " + l.getPhoneNum() + "\n" +
                      "Website: " + l.getWebsite();
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(l.getLatitude()), Double.parseDouble(l.getLongitude())))
                    .title(l.getName())
                    .snippet(snippet));
        }
    }
    private void onClickBack(View v) {
        Intent intent = new Intent(this, LocationsList.class);
        Bundle bundle = new Bundle();

        bundle.putInt("USER_TYPE", getIntent().getExtras().getInt("USER_TYPE"));

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
