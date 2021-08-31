package com.thundercandy.epq;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thundercandy.epq.database.DbUtils;

import java.util.ArrayList;

public class HomeActivity extends DrawerActivity {

    RecyclerView recView;
    TextView txtNoCards, txtSelectText;
    Button btnStartSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setToolbarTitle("Home");
        recView = findViewById(R.id.selectableCategoryRecView);
        txtNoCards = findViewById(R.id.txtNoCards);
        txtSelectText = findViewById(R.id.txtSelectText);
        btnStartSession = findViewById(R.id.btnStartSession);

        // Requests an arraylist of type SelectableCategory to show the user in the home screen
        ArrayList<SelectableCategory> scs = DbUtils.getSelectableCategories(this);
        if (scs.size() != 0) {
            SelectableCategoryAdapter adapter = new SelectableCategoryAdapter(this);
            recView.setAdapter(adapter);
            recView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setSelectableCategories(scs);

            // Enables the recycler view and disables the UI components meant for showing the user a 'no categories found' message
            txtSelectText.setVisibility(View.VISIBLE);
            recView.setVisibility(View.VISIBLE);
            txtNoCards.setVisibility(View.GONE);
        } else {
            // Shows a message to the user that no categories were found.
            txtSelectText.setVisibility(View.GONE);
            recView.setVisibility(View.GONE);
            txtNoCards.setVisibility(View.VISIBLE);
        }
    }
}
