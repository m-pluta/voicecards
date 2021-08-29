package com.thundercandy.epq;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.thundercandy.epq.database.DbUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class CollectionsActivity extends DrawerActivity {

    TextInputLayout SearchField;
    EditText txtSearch;
    Button btnAddNewCategory;
    RecyclerView collectionsRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        setToolbarTitle("Collections");

        SearchField = findViewById(R.id.SearchField);
        txtSearch = (EditText) findViewById(R.id.txtSearch);
        btnAddNewCategory = (Button) findViewById(R.id.btnAddNewCategory);
        collectionsRecView = findViewById(R.id.collectionsRecView);

        // Adding functionality to the clear search button
        SearchField.setEndIconOnClickListener(v -> {
            txtSearch.setText("");
        });
        SearchField.setEndIconVisible(false);       // Makes clear text end icon visibility to gone

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SearchField.setEndIconVisible(s.length() != 0); // End icon only visible when user has entered something

                String search = s.toString();
                updateResults(search);
            }
        });

        btnAddNewCategory.setOnClickListener(v -> {
            Toast.makeText(CollectionsActivity.this, "Add New Category button clicked", Toast.LENGTH_SHORT).show();
        });

        // DbUtils.addDebugData(this); //TODO: Make sure this only executes once

        CategoryRecViewAdapter adapter = new CategoryRecViewAdapter(this);
        collectionsRecView.setAdapter(adapter);
        collectionsRecView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCategories(DbUtils.getCategories(this));

    }

    private void updateResults(String search) {
        // TODO: Make this update the the RecyclerView Adapter
    }
}
