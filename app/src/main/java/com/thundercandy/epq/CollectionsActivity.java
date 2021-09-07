package com.thundercandy.epq;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thundercandy.epq.database.DbUtils;
import com.thundercandy.epq.events.CardAddedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class CollectionsActivity extends DrawerActivity {

    Button btnAddNewCategory;
    RecyclerView collectionsRecView;
    CategoryRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        setToolbarTitle("Collections");
        btnAddNewCategory = (Button) findViewById(R.id.btnAddNewCategory);
        collectionsRecView = findViewById(R.id.collectionsRecView);

        EventBus.getDefault().register(this);

        // DbUtils.addDebugData(this); //TODO: Make sure this only executes once

        adapter = new CategoryRecViewAdapter(this);
        collectionsRecView.setAdapter(adapter);
        collectionsRecView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCategories(DbUtils.getCategories(this));

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

}
