package com.example.gourn.buzztracker.Controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng defaultCamera = new LatLng(33.753746, -84.386330);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultCamera, 10f));
        map.setInfoWindowAdapter(new CustomWindow(this));
        Bundle bundle = getIntent().getExtras();
        ArrayList<Location> locations = bundle.getParcelableArrayList("LOCATIONS_ARRAY");
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
}
