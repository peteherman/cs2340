package com.example.gourn.buzztracker.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.gourn.buzztracker.Model.Donation;
import com.example.gourn.buzztracker.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DonationListView extends AppCompatActivity {
    private Button backButton;
    private ListView listView;
    private HashMap<String, String> donations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_list_view);

        //get view elements
        backButton = findViewById(R.id.back_button);
        listView = findViewById(R.id.list_view);


        //Set back button click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClick(v);
            }
        });

        //Populate list view
        getDonations();


    }

    private HashMap<String, String> getDonations() {
        final HashMap<String, String> donations = new HashMap<>();

        String locationName = getIntent().getExtras().getString("LOCATION_NAME");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        //Query database
        Query query = databaseReference.child("donations").child(locationName);
        query.addValueEventListener(new ValueEventListener() {
            private HashMap<String, String> donations = new HashMap<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    donations.put(d.getKey().toString(), d.child("shortDescription").getValue().toString());
                }
                List<String> donationShortDescriptions = new ArrayList<>();
                for (String sd : donations.values()) {
                    donationShortDescriptions.add(sd);
                }

                //Setup listview adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DonationListView.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, donationShortDescriptions);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener (
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                clickDonation(view, (String)donations.keySet().toArray()[position]);
                            }
                        }
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            public HashMap<String, String> getDonations() {
                return donations;
            }
        });

        return donations;
    }

    private void clickDonation(View view, String donationId) {
        Intent intent = new Intent(this, DetailedDonationView.class);

        Bundle bundle = new Bundle();
        bundle.putInt("USER_TYPE", getIntent().getExtras().getInt("USER_TYPE"));
        bundle.putString("DONATION_ID", donationId);
        bundle.putString("LOCATION_NAME", getIntent().getExtras().getString("LOCATION_NAME"));

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void onBackClick(View view) {
        Intent intent = new Intent(this, LocationsList.class);

        Bundle bundle = new Bundle();
        bundle.putInt("USER_TYPE", getIntent().getExtras().getInt("USER_TYPE"));

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}
