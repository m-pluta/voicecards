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

import java.util.ArrayList;
import java.util.Arrays;

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

        ArrayList<Category> categories = new ArrayList<>();

        Card a = new Card(1, "Work done", "The amount of force needed to move an object a certain distance.");
        Card b = new Card(2, "Momentum", "The product of the mass and velocity of an object.");
        Card c = new Card(3, "Centripetal Force", "A force, orthogonal to the direction of motion of an object which causes the object to move in a circular path");

        Card d = new Card(4, "FDE Cycle", "The FDE cycle is followed by a processor to process an instruction.");
        Card e = new Card(5, "Hardware", "The physical components of a computer system");
        Card f = new Card(6, "Software", "Instructions that tell a computer what to do");
        Card g = new Card(7, "Vector", "A quantity with both a magnitude and direction");

        categories.add(new Category(1, "Physics", new ArrayList<>(Arrays.asList(a, b, c))));
        categories.add(new Category(2, "Computer Science", new ArrayList<>(Arrays.asList(d, e, f))));
        categories.add(new Category(2, "Maths", new ArrayList<>(Arrays.asList(g))));

        RecyclerView collectionsRecView = findViewById(R.id.collectionsRecView);

        CategoryRecViewAdapter adapter = new CategoryRecViewAdapter(this);
        collectionsRecView.setAdapter(adapter);
        collectionsRecView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCategories(categories);

    }

    private void updateResults(String search) {
        // TODO: Make this update the the RecyclerView Adapter
    }
}
