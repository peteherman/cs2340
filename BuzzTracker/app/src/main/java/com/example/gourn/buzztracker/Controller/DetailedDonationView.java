package com.example.gourn.buzztracker.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class DetailedDonationView extends AppCompatActivity {
    private Button backButton;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_donation_view);


        //Get view elements
        backButton = findViewById(R.id.back_button);
        listView = findViewById(R.id.list_view);

        //Populate listview
        populateListView();

        //Set back button on click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClicked(v);
            }
        });

    }

    private void populateListView() {
        final String donationId = getIntent().getExtras().getString("DONATION_ID");
        String locationName = getIntent().getExtras().getString("LOCATION_NAME");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("donations").child(locationName).child(donationId);
        query.addValueEventListener(new ValueEventListener() {
            private HashMap<String, String> donationInfo = new HashMap<>();
            private final int YEAR_OFFSET = 1;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> donationValues = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    donationInfo.put(d.getKey().toString(), d.getValue().toString());
                    donationValues.add(formatDonation(d));
                }


                ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailedDonationView.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, donationValues);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            public String formatDonation(DataSnapshot d) {
                String result = "";

                String key = d.getKey();
                String value = d.getValue().toString();

                result += key + ": ";

                if (key != null && key.equalsIgnoreCase("timestamp")) {
                    String timestampString = "";
                    timestampString += d.child("month").getValue().toString();
                    timestampString += "-";
                    timestampString += d.child("day").getValue().toString();
                    timestampString += "-";
                    timestampString += d.child("year").
                            getValue().toString().substring(YEAR_OFFSET);
                    timestampString += "  ";
                    timestampString += d.child("hours").getValue().toString();
                    timestampString += ":";
                    timestampString += d.child("minutes").getValue().toString();
                    timestampString += ":";
                    timestampString += d.child("seconds").getValue().toString();

                    result += timestampString;
                } else {
                    result += value;
                }
                return result;
            }
        });
    }

    private void onBackClicked(View view) {
        Intent intent = new Intent(this, DonationListView.class);

        Bundle bundle = new Bundle();
        bundle.putInt("USER_TYPE", getIntent().getExtras().getInt("USER_TYPE"));
        bundle.putString("LOCATION_NAME", getIntent().getExtras().getString("LOCATION_NAME"));

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
