package com.thundercandy.epq;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class CollectionsActivity extends DrawerActivity {

    TextInputLayout SearchField;
    EditText txtSearch;
    Button btnAddNewCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        setToolbarTitle("Collections");

        SearchField = findViewById(R.id.SearchField);
        txtSearch = (EditText) findViewById(R.id.txtSearch);
        btnAddNewCategory = (Button) findViewById(R.id.btnAddNewCategory);

        // Adding functionality to the clear search button
        SearchField.setEndIconOnClickListener(v -> {
            txtSearch.setText("");
        });

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                updateResults(search);
            }
        });

        btnAddNewCategory.setOnClickListener(v -> {
            Toast.makeText(CollectionsActivity.this, "Add New Category button clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateResults(String search) {
        Toast.makeText(CollectionsActivity.this, "Search: " + search, Toast.LENGTH_SHORT).show();
        // TODO: Make this update the the RecyclerView Adapter
    }
}
