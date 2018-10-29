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

public class SearchResultsView extends AppCompatActivity {
    private ListView resultlist;
    private Button backButton;
    private String category;
    private String toSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_view);
        Bundle extras = getIntent().getExtras();
        category = extras.getString("CATEGORY_SELECTED");
        toSearch = extras.getString("SEARCH_FIELD");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBack(v);
            }
        });
        String searchParam = extras.getString("LOCATION_SELECTED");
        if (searchParam.equals("ALL")) {
            findInAll();
        } else {
            findIn(searchParam);
        }
    }


    private void findInAll() {
        final List<String> donationsList = new ArrayList<>();
        final Map<String, String> donationsMap = new HashMap<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final Query query1 = ref.child("donations");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    final String s = d.getKey().toString();
                    final Query query2 = ((DatabaseReference) query1).child(d.getKey().toString());
                    query2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot c : dataSnapshot.getChildren()) {
                                final String key = c.getKey().toString();
                                if (category != null) {
                                    if (c.child("category").getValue().toString().equals(category)) {
                                        String locAndItem = s + ":" + c.child("short_description").getValue().toString();
                                        donationsMap.put(key, locAndItem);
                                    }
                                } else if (toSearch != null) {
                                    Query query3 = ((DatabaseReference)query2).startAt(toSearch).endAt(toSearch + "\uf8ff");
                                    query3.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot e : dataSnapshot.getChildren()) {
                                                String locAndItem = s + ":" + e.getValue().toString();
                                                donationsMap.put(key, locAndItem);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }
                            for (String sd : donationsMap.values()) {
                                donationsList.add(sd);
                            }

//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchResultsView.class,
//                                    android.R.layout.simple_list_item_1, donationsList);
//                            resultlist.setAdapter(adapter);
//                            resultlist.setOnItemClickListener (
//                                    new AdapterView.OnItemClickListener() {
//                                        @Override
//                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                            String[] locItems = donationsMap.get((String)donationsMap.keySet().toArray()[position]).split(":");
//                                            String locName = locItems[0];
//                                            clickDonation(view, (String)donationsMap.keySet().toArray()[position], locName);
//                                        }
//                                    }
//                            );
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void findIn(String searchParam) {

    }

    private void clickDonation(View view, String donationId, String name) {
        Intent intent = new Intent(this, DetailedDonationView.class);

        Bundle bundle = new Bundle();
        bundle.putInt("USER_TYPE", getIntent().getExtras().getInt("USER_TYPE"));
        bundle.putString("DONATION_ID", donationId);
        bundle.putString("LOCATION_NAME", name);

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void onClickBack(View v) {
        Intent intent = new Intent(this, SearchView.class);
        Bundle bundle = new Bundle();

        bundle.putInt("USER_TYPE", getIntent().getExtras().getInt("USER_TYPE"));

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
