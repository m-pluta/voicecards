package com.thundercandy.epq;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public abstract class DrawerActivity extends AppCompatActivity implements View.OnClickListener {
    protected DrawerLayout fullLayout;
    protected FrameLayout actContent;

    @Override
    public void setContentView(final int layoutResID) {
        // Your base layout here
        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer, null);
        actContent = (FrameLayout) fullLayout.findViewById(R.id.act_content);

        // Setting the content of layout your provided to the act_content frame
        getLayoutInflater().inflate(layoutResID, actContent, true);
        super.setContentView(fullLayout);

        // Adding OnClickListeners to all the buttons in the DrawerActivity
        addListeners();
    }

    private void addListeners() {
        findViewById(R.id.btnMenu).setOnClickListener(this);
        findViewById(R.id.btnMore).setOnClickListener(this);
        findViewById(R.id.closeDrawer).setOnClickListener(this);
        findViewById(R.id.btnHome).setOnClickListener(this);
        findViewById(R.id.btnCollections).setOnClickListener(this);
        findViewById(R.id.btnStatistics).setOnClickListener(this);
        findViewById(R.id.btnPomodoro).setOnClickListener(this);
        findViewById(R.id.btnFriends).setOnClickListener(this);
        findViewById(R.id.btnSettings).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMenu:              // The Burger icon in the toolbar
                // Open drawer
                openDrawer(fullLayout);
                break;
            case R.id.btnMore:              // The three dots in the toolbar
                Toast.makeText(DrawerActivity.this, "Three dots clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.closeDrawer:          // The X Button in the navigation drawer
                closeDrawer(fullLayout);
                break;
            case R.id.btnHome:          // The X Button in the navigation drawer
                redirectActivity(this, HomeActivity.class);
                break;
            case R.id.btnCollections:          // The X Button in the navigation drawer
                redirectActivity(this, CollectionsActivity.class);
                break;
            case R.id.btnStatistics:          // The X Button in the navigation drawer
                redirectActivity(this, StatisticsActivity.class);
                break;
            case R.id.btnPomodoro:          // The X Button in the navigation drawer
                redirectActivity(this, PomodoroActivity.class);
                break;
            case R.id.btnFriends:          // The X Button in the navigation drawer
                redirectActivity(this, FriendsActivity.class);
                break;
            case R.id.btnSettings:          // The X Button in the navigation drawer
                redirectActivity(this, SettingsActivity.class);
                break;
        }
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        // Open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        // Close drawer layout
        // Check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // When the drawer is open
            // Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity origin, Class destination) {
        if (!origin.getClass().equals(destination)) {
            Intent intent = new Intent(origin, destination);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            origin.startActivity(intent);
        }
    }

    public void setToolbarTitle(String title) {
        TextView textView = fullLayout.findViewById(R.id.toolbarTitle);
        textView.setText(title);
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.forward_slide_in, R.anim.forward_slide_out);
//    }
//
//    //Code for making a window transition animation apply to only this activity and not globally
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.back_slide_in, R.anim.back_slide_out);
//    }


}
