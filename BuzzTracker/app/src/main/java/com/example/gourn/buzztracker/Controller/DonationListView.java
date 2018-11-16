package com.example.gourn.buzztracker.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
import java.util.Map;
import java.util.Objects;

public class DonationListView extends AppCompatActivity {
//    private Button backButton;
    private ListView listView;
    private HashMap<String, String> donations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button backButton;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_list_view);

        //get view elements
        backButton = findViewById(R.id.back_button);
        listView = findViewById(R.id.list_view);


        //Set back button click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClick();
            }
        });

        //Populate list view
        getDonations();


    }

    private void getDonations() {
        final Map<String, String> donations = new HashMap<>();

        String locationName = Objects.requireNonNull(getIntent().getExtras()).getString("LOCATION_NAME");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        //Query database
        Query query = databaseReference.child("donations").child(Objects.requireNonNull(locationName));
        query.addValueEventListener(new ValueEventListener() {
            private final HashMap<String, String> donations = new HashMap<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    donations.put(d.getKey(), Objects.requireNonNull(d.child("shortDescription")
                            .getValue()).toString());
                }
                List<String> donationShortDescriptions = new ArrayList<>(donations.values());

                //Setup listview adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DonationListView.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1,
                        donationShortDescriptions);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener (
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                clickDonation((String)donations.keySet().toArray()[position]);
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

    }

    private void clickDonation(String donationId) {
        Intent intent = new Intent(this, DetailedDonationView.class);

        Bundle bundle = new Bundle();
        bundle.putInt("USER_TYPE", Objects.requireNonNull(getIntent().getExtras()).getInt("USER_TYPE"));
        bundle.putString("DONATION_ID", donationId);
        bundle.putString("LOCATION_NAME", getIntent().getExtras().getString("LOCATION_NAME"));
        bundle.putString("Donations", "Yes");

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void onBackClick() {
        Intent intent = new Intent(this, LocationsList.class);

        Bundle bundle = new Bundle();
        bundle.putInt("USER_TYPE", Objects.requireNonNull(getIntent().getExtras()).getInt("USER_TYPE"));

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}
