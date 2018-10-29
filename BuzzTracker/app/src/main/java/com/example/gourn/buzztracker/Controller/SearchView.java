package com.example.gourn.buzztracker.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gourn.buzztracker.Model.DefaultDonationCategories;
import com.example.gourn.buzztracker.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchView extends AppCompatActivity {
    private EditText searchEditText;
    private Button submitButton;
    private Button backButton;
    private Spinner locationNameSpinner;
    private Spinner categorySpinner;
    private Spinner searchTypeSpinner;
    private final String[] searchTypes = {"Item", "Category"};
    private final int ITEM_SEARCH = 0;
    private final int CATEGORY_SEARCH = 1;
    private final int DEFAULT_CATEGORY_POS = 0;
    private final int DEFAULT_LOCATION_POS = 0;
    private int searchType;
    private int categorySelected;
    private int locationSelected;
    private FirebaseDatabase firebaseDatabase;
    private HashMap<Integer, String> categoryMap = new HashMap<>();
    private HashMap<Integer, String> locationMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        //Set searchType = item search
        searchType = ITEM_SEARCH;

        //Set category selected to default category positions
        categorySelected = DEFAULT_CATEGORY_POS;

        //Set location selected to default location position
        locationSelected = DEFAULT_LOCATION_POS;

        //Get Elements from view
        searchEditText = findViewById(R.id.search_edit_text);
        locationNameSpinner = findViewById(R.id.location_name_spinner);
        submitButton = findViewById(R.id.search_button);
        categorySpinner = findViewById(R.id.category_spinner);
        searchTypeSpinner = findViewById(R.id.search_type_spinner);
        locationNameSpinner = findViewById(R.id.location_name_spinner);
        backButton = findViewById(R.id.back_button);


        //Get list of location names from firebase & populate location spinner
        final DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference();
        Query locationQuery = databaseReference.child("Locations");
        locationQuery.addValueEventListener(new ValueEventListener() {
            private ArrayList<String> locationNames = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;
                locationNames.add("All");
                locationMap.put(count++, "All");
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    locationNames.add(d.child("name").getValue().toString());
                    locationMap.put(count++, d.child("name").getValue().toString());
                }

                //Create ArrayAdapter for spinner
                ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(SearchView.this,
                        android.R.layout.simple_spinner_item, android.R.id.text1, locationNames);
                locationNameSpinner.setAdapter(locationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        //Set on itemSelectedListener for location spinner
        locationNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                locationSelected = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Make adapter for search type spinner
        ArrayAdapter<String> searchTypeAdapter = new ArrayAdapter<>(this,
        android.R.layout.simple_spinner_item, android.R.id.text1, searchTypes);
        searchTypeSpinner.setAdapter(searchTypeAdapter);

        //set on itemSelectedListener for searchType spinner
        searchTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int pos, long arg3) {
                searchType = pos;
                if (pos == ITEM_SEARCH) {
                    categorySpinner.setVisibility(View.GONE);
                    searchEditText.setVisibility(View.VISIBLE);
                } else if (pos == CATEGORY_SEARCH) {
                    searchEditText.setVisibility(View.GONE);
                    categorySpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Set category spinner adapter
        //Build String array of default donation categories
        String[] defaultDonationCategories = new String[DefaultDonationCategories.values().length];
        for (int i = 0; i < DefaultDonationCategories.values().length; i++) {
            defaultDonationCategories[i] = DefaultDonationCategories.values()[i].toString();
            categoryMap.put(i, defaultDonationCategories[i]);
        }

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                android.R.id.text1, defaultDonationCategories);
        categorySpinner.setAdapter(categoryAdapter);

        //Set category onItemSelectedListener
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                categorySelected = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Set category spinner to invisible at first
        categorySpinner.setVisibility(View.GONE);



        //Set onClickListener for submitButton
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubmit(v);
            }
        });


        //Set onClickListener for backButton
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBack(v);
            }
        });


    }

    private void onClickSubmit(View v) {

        Intent intent = new Intent(this, SearchResultsView.class);
        Bundle bundle = new Bundle();
        bundle.putInt("SEARCH_TYPE", searchType);

        //add to bundle certain characteristics if searchType is and item search
        if (searchType == ITEM_SEARCH) {
            //Check search type, if item search, search field can't be empty
            if (searchEditText.getText() != null &&
                    searchEditText.getText().toString().isEmpty()) {
                /*
                 *
                 * CREATE TOAST WITH ERROR HERE
                 *
                 */
                return;
            }

            //Add search field to intent
            bundle.putString("SEARCH_FIELD", searchEditText.getText().toString());
        } else if (searchType == CATEGORY_SEARCH) {
            //Add category to intent
            bundle.putString("CATEGORY_SELECTED", categoryMap.get(categorySelected));
        }

        //Add location selected field to intent
        bundle.putString("LOCATION_SELECTED", categoryMap.get(locationSelected));
        bundle.putInt("USER_TYPE", getIntent().getExtras().getInt("USER_TYPE"));

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void onClickBack(View v) {
        Intent intent = new Intent(this, AppScreen.class);
        Bundle bundle = new Bundle();

        bundle.putInt("USER_TYPE", getIntent().getExtras().getInt("USER_TYPE"));

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
