package com.thundercandy.epq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StatisticsActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        setToolbarTitle("Statistics");
    }
}
