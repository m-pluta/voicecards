package com.thundercandy.epq;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.thundercandy.epq.database.DbUtils;
import com.thundercandy.epq.events.CardAddedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class CollectionsActivity extends DrawerActivity {

    TextInputLayout SearchField;
    EditText txtSearch;
    Button btnAddNewCategory;
    RecyclerView collectionsRecView;
    CategoryRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        setToolbarTitle("Collections");

        SearchField = findViewById(R.id.SearchField);
        txtSearch = (EditText) findViewById(R.id.txtSearch);
        btnAddNewCategory = (Button) findViewById(R.id.btnAddNewCategory);
        collectionsRecView = findViewById(R.id.collectionsRecView);

        EventBus.getDefault().register(this);

        // DbUtils.addDebugData(this); //TODO: Make sure this only executes once

        adapter = new CategoryRecViewAdapter(this);
        collectionsRecView.setAdapter(adapter);
        collectionsRecView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCategories(DbUtils.getCategories(this));

        // Adding functionality to the clear search button
        SearchField.setEndIconVisible(false);               // Makes clear text end icon visibility to gone
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
                setSearchQuery(s.toString());
            }
        });

        btnAddNewCategory.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Name of new category");

            final EditText txtInput = new EditText(this);

            txtInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            builder.setView(txtInput);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String input = txtInput.getText().toString();
                if (input.equals("")) {
                    Toast.makeText(this, "Category name cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.addCategory(input);
                    setSearchQuery("");
                }
                //TODO: test if category already exists.
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.setCancelable(true);
            builder.show();
        });
    }

    @Subscribe
    public void onEvent(CardAddedEvent event) {
        Log.d("CardAddedEvent", "Event received");
        Log.d("CardAddedEvent", "Card '" + event.getAddedCard().getTerm() + "' to category with id: " + event.getCategory());

        int targetPosition = event.getTargetPosition();
        adapter.updateCategory(targetPosition);
    }

    public void setSearchQuery(String query) {
        SearchField.setEndIconVisible(query.length() != 0); // End icon only visible when user has entered something
        updateResults(query);
    }

    private void updateResults(String search) {
        // TODO: Make this update the the RecyclerView Adapter
    }


}
