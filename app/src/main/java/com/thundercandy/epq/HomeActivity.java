package com.thundercandy.epq;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thundercandy.epq.database.DbUtils;

import java.util.ArrayList;

public class HomeActivity extends DrawerActivity {

    RecyclerView recView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setToolbarTitle("Home");
        recView = findViewById(R.id.selectableCategoryRecView);

        ArrayList<SelectableCategory> scs = DbUtils.getSelectableCategories(this);
        if (scs.size() != 0) {
            SelectableCategoryAdapter adapter = new SelectableCategoryAdapter(this);
            recView.setAdapter(adapter);
            recView.setLayoutManager(new LinearLayoutManager(this));
            adapter.setSelectableCategories(scs);
        } else {
            //TODO: Show message that no categories with cards were found
        }
    }
}
