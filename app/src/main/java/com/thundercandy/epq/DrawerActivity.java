package com.thundercandy.epq;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
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

        UI_changeOpenedActivity(this);

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
            case R.id.btnHome:              // The X Button in the navigation drawer
                redirectActivity(this, HomeActivity.class);
                break;
            case R.id.btnCollections:       // The X Button in the navigation drawer
                redirectActivity(this, CollectionsActivity.class);
                break;
            case R.id.btnStatistics:        // The X Button in the navigation drawer
                redirectActivity(this, StatisticsActivity.class);
                break;
            case R.id.btnPomodoro:          // The X Button in the navigation drawer
                redirectActivity(this, PomodoroActivity.class);
                break;
            case R.id.btnFriends:           // The X Button in the navigation drawer
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

    public static void redirectActivity(Activity origin, Class<?> destination) {
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

    private void UI_changeOpenedActivity(Activity context) {
        if (context.getClass().equals(HomeActivity.class)) {
            selectNavItem(R.id.navView_home_selected, R.id.navView_home_Icon, R.id.navView_home_SelectedIcon);
        } else if (context.getClass().equals(CollectionsActivity.class)) {
            selectNavItem(R.id.navView_collections_selected, R.id.navView_collections_Icon, R.id.navView_collections_SelectedIcon);
        } else if (context.getClass().equals(StatisticsActivity.class)) {
            selectNavItem(R.id.navView_statistics_selected, R.id.navView_statistics_Icon, R.id.navView_statistics_SelectedIcon);
        } else if (context.getClass().equals(PomodoroActivity.class)) {
            selectNavItem(R.id.navView_pomodoro_selected, R.id.navView_pomodoro_Icon, R.id.navView_pomodoro_SelectedIcon);
        } else if (context.getClass().equals(FriendsActivity.class)) {
            selectNavItem(R.id.navView_friends_selected, R.id.navView_friends_Icon, R.id.navView_friends_SelectedIcon);
        }
    }

    private void selectNavItem(@IdRes int background, @IdRes int icon, @IdRes int selectedIcon) {
        LinearLayout Bg = (LinearLayout) findViewById(background);
        ImageView Ic = (ImageView) findViewById(icon);
        ImageView sIc = (ImageView) findViewById(selectedIcon);

        Bg.setBackgroundColor(Color.parseColor("#2af85f6a"));
        Ic.setImageTintList(ColorStateList.valueOf(Color.rgb(248,95, 106)));
        sIc.setImageResource(R.drawable.ic_arrow_left);
        sIc.setImageTintList(ColorStateList.valueOf(Color.rgb(248,95, 106)));
    }
//
//    //Code for making a window transition animation apply to only this activity and not globally
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.back_slide_in, R.anim.back_slide_out);
//    }


}
