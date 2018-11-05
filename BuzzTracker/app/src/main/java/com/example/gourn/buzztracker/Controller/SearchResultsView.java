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
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView emptyView;
    private List<String> toCheck = new ArrayList<>();
    private boolean found = false;
    List<String> donationsList = new ArrayList<>();
    HashMap<String, String> donationsMap = new HashMap<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_view);
        Bundle extras = getIntent().getExtras();
        resultlist = (ListView) findViewById(R.id.list_view);
        backButton = (Button) findViewById(R.id.back_button);
        emptyView = (TextView) findViewById(R.id.empty_view);
        category = extras.getString("CATEGORY_SELECTED");
        toSearch = extras.getString("SEARCH_FIELD");
        adapter = new ArrayAdapter<String>(SearchResultsView.this,
                android.R.layout.simple_list_item_1, donationsList);
        resultlist.setAdapter(adapter);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBack(v);
            }
        });
        String searchParam = extras.getString("LOCATION_SELECTED");
        if (searchParam.equals("All")) {
            findInAll();
        } else {
            findIn(searchParam);
        }
        resultlist.setEmptyView(emptyView);
    }

    private void setFound() {
        this.found = true;
    }

    private HashMap<String, String> findInAll() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        List<String> locNames = new ArrayList<>();
        final Query query1 = ref.child("donations");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> donationsList = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    final String s = d.getKey().toString();
                    final Query query2 = ((DatabaseReference) query1).child(d.getKey().toString());
                    query2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot c : dataSnapshot.getChildren()) {
                                final String key = c.getKey().toString();
                                if (category != null) {
                                    if (c.child("category").getValue().toString().toLowerCase().equals(category.toLowerCase())) {
                                        String locAndItem = s + ":" + c.child("shortDescription").getValue().toString();
                                        addItem(key, locAndItem);
                                        setFound();
                                    }
                                } else if (toSearch != null) {
                                    if (c.child("shortDescription").getValue().toString().toLowerCase().contains(toSearch.toLowerCase())) {
                                        String locAndItem = s + ":" + c.child("shortDescription").getValue().toString();
                                        addItem(key, locAndItem);
                                        setFound();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                resultlist.setOnItemClickListener (new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String[] locItems = donationsMap.get((String)donationsMap.keySet().toArray()[position]).split(":");
                        String locName = locItems[0];
                        clickDonation(view, (String)donationsMap.keySet().toArray()[position], locName);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return donationsMap;
    }

    private void addItem(String key, String items) {
        donationsList.add(items);
        toCheck.addAll(donationsList);
        donationsMap.put(key, items);
        adapter.notifyDataSetChanged();
    }

    private HashMap<String, String> findIn(final String searchParam) {
        final HashMap<String, String> donationsMap = new HashMap<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query1 = ref.child("donations").child(searchParam);
        query1.addValueEventListener(new ValueEventListener() {
            private HashMap<String, String> donationsMap = new HashMap<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean found = false;
                List<String> donationsList = new ArrayList<>();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    if (category != null) {
                        if (ds.child("category").getValue().toString().toLowerCase().equals(category.toLowerCase())) {
                            donationsMap.put(ds.getKey().toString(), ds.child("shortDescription").getValue().toString());
                            found = true;
                        }
                    } else if (toSearch != null) {
                        if (ds.child("shortDescription").getValue().toString().toLowerCase().contains(toSearch.toLowerCase())) {
                            donationsMap.put(ds.getKey().toString(), ds.child("shortDescription").getValue().toString());
                            found = true;
                        }
                    }
                }
//                if (!found) {
////                    Toast.makeText(getApplicationContext(), "No item found. Go back to try again", Toast.LENGTH_LONG).show();
//                    addItem("", "No such item found. Go back to search again.");
//                }
                for (String sd : donationsMap.values()) {
                    donationsList.add(sd);
                }

                //Setup listview adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchResultsView.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, donationsList);
                resultlist.setAdapter(adapter);

                resultlist.setOnItemClickListener (
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                clickDonation(view, (String)donationsMap.keySet().toArray()[position], searchParam);
                            }
                        }
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
            public HashMap<String, String> findIn() {
                return donationsMap;
            }
        });
        return donationsMap;
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
