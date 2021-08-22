package com.thundercandy.epq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FriendsActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        setToolbarTitle("Friends");
    }
}
