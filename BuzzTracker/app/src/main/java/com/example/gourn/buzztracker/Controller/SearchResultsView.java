package com.example.gourn.buzztracker.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gourn.buzztracker.R;

public class SearchResultsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_view);
        Bundle extras = getIntent().getExtras();

    }
}
