package com.thundercandy.epq;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import de.hdodenhof.circleimageview.CircleImageView;

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

        // Changing color of closeDrawer button in the nav_drawer to red
        // TODO: Do this in the .xml instead of programmatically
        ImageView closeDrawer = (ImageView) findViewById(R.id.closeDrawer);
        closeDrawer.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.defaultRed_100)));

        // Updating the UI to highlight the current navigation drawer item
        UI_changeOpenedActivity(this);

        // Changing The shade of the Drawer scrim to a bit of a more transparent grey
        fullLayout.setScrimColor(getResources().getColor(R.color.drawerScrim));

        // Adding OnClickListeners to all the buttons in the DrawerActivity
        addListeners();

        updateDrawerWithUser();

        checkIfShouldShowMenu();
    }

    private void checkIfShouldShowMenu() {
        if (User.LOGIN_TYPE == User.LOGIN_GOOGLE) {
            findViewById(R.id.btnMore).setVisibility(View.VISIBLE);
            findViewById(R.id.buffer).setVisibility(View.GONE);
        } else {
            findViewById(R.id.btnMore).setVisibility(View.GONE);
            findViewById(R.id.buffer).setVisibility(View.VISIBLE);
        }
    }

    private void updateDrawerWithUser() {
        // Setting the display name in the nav drawer if the user logged in through Google

        TextView txtFullName = (TextView) findViewById(R.id.txtFullName);
        if (User.DisplayName != null) {
            txtFullName.setText(User.DisplayName);
        } else {
            txtFullName.setText(User.DEFAULT_DISPLAY_NAME);
        }

        CircleImageView imgProfile = findViewById(R.id.imgProfile);
        if (User.imageUri != null) {
            System.out.println(User.imageUri.toString());
            Glide.with(this)
                    .asBitmap()
                    .load(User.imageUri)
                    .into(imgProfile);
        } else {
            imgProfile.setImageResource(User.DEFAULT_PROFILE_PICTURE);
        }

        Toast.makeText(this, "User type: " + User.LOGIN_TYPE, Toast.LENGTH_SHORT).show();
    }

    private void addListeners() {
        findViewById(R.id.btnMenu).setOnClickListener(this);
        findViewById(R.id.btnMore).setOnClickListener(this);
        findViewById(R.id.closeDrawer).setOnClickListener(this);
        findViewById(R.id.btnHome).setOnClickListener(this);
        findViewById(R.id.btnCollections).setOnClickListener(this);
        findViewById(R.id.btnPomodoro).setOnClickListener(this);
        findViewById(R.id.btnSettings).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMenu:              // The Burger icon in the toolbar
                openDrawer(fullLayout);
                break;
            case R.id.btnMore:              // The three dots in the toolbar
                PopupMenu popupMenu = new PopupMenu(this, v);
                popupMenu.getMenuInflater().inflate(R.menu.action_bar_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.log_out:
                            revokeGoogleAccess();
                            return true;
                        default:
                            return false;
                    }
                });
                popupMenu.show();
                break;
            case R.id.closeDrawer:          // The X Button in the navigation drawer
                closeDrawer(fullLayout);
                break;
            case R.id.btnHome:              // The Home Navigation Item in the navigation view
                redirectActivity(this, HomeActivity.class);
                break;
            case R.id.btnCollections:       // The Collections Navigation Item in the navigation view
                redirectActivity(this, CollectionsActivity.class);
                break;
            case R.id.btnPomodoro:          // The Pomodoro Navigation Item in the navigation view
                redirectActivity(this, PomodoroActivity.class);
                break;
            case R.id.btnSettings:          // The Settings Item in the navigation drawer
                redirectActivity(this, SettingsActivity.class);
                break;
        }
    }

    private void revokeGoogleAccess() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInClient.revokeAccess()
                .addOnCompleteListener(this, task -> {
                    User.resetUser();
                    Intent intent = new Intent(DrawerActivity.this, MainLoginActivity.class);
                    startActivity(intent);
                });
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {       // Check if the drawer is open
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //Commented is the code for making the transition between activities fast
//    public static void redirectActivity(Activity origin, Class<?> destination) {
    public void redirectActivity(Activity origin, Class<?> destination) {
        if (!origin.getClass().equals(destination)) {               // Checks if the user wants to go to where they already are
            Intent intent = new Intent(origin, destination);
//            overridePendingTransition(0, 0);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            origin.startActivity(intent);

            // Makes the transition between DrawerActivities smooth
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    public void setToolbarTitle(String title) {
        TextView textView = (TextView) fullLayout.findViewById(R.id.toolbarTitle);
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
        } else if (context.getClass().equals(PomodoroActivity.class)) {
            selectNavItem(R.id.navView_pomodoro_selected, R.id.navView_pomodoro_Icon, R.id.navView_pomodoro_SelectedIcon);
        }
    }

    private void selectNavItem(@IdRes int background, @IdRes int icon, @IdRes int selectedIcon) {
        LinearLayout Bg = (LinearLayout) findViewById(background);
        ImageView Ic = (ImageView) findViewById(icon);
        ImageView sIc = (ImageView) findViewById(selectedIcon);

        sIc.setImageResource(R.drawable.ic_arrow_left);

        Bg.setBackgroundColor(Color.parseColor("#1af85f6a"));
        Ic.setImageTintList(ColorStateList.valueOf(Color.rgb(248, 95, 106)));
        sIc.setImageTintList(ColorStateList.valueOf(Color.rgb(248, 95, 106)));
    }
//
//    //Code for making a window transition animation apply to only this activity and not globally
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.back_slide_in, R.anim.back_slide_out);
//    }


    @Override
    public void onBackPressed() {
        Class<?> destination;
        if (this.getClass().equals(HomeActivity.class)) {
            destination = MainLoginActivity.class;
        } else {
            destination = HomeActivity.class;
        }

        Intent intent = new Intent(this, destination);

        // Clears the Back stack so the user can't go back to previous activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
