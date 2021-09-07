package com.thundercandy.epq;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout btnGoogleSignUp;
    private Button btnContinueAsGuest;

    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        Utils.removeBottomNavigation(this);

        btnGoogleSignUp = findViewById(R.id.btnGoogleSignUp);
        btnContinueAsGuest = findViewById(R.id.btnContinueAsGuest);

        btnGoogleSignUp.setOnClickListener(this);
        btnContinueAsGuest.setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        if (User.autoGoogleSignIn) {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            proceedAsGoogleUser(account);
            User.autoGoogleSignIn = false;
        }
    }

    private void proceedAsGoogleUser(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            User.setGoogleUser();
            User.DisplayName = account.getDisplayName();
            User.imageUri = account.getPhotoUrl();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnGoogleSignUp:
                GoogleSignIn();
                Toast.makeText(MainLoginActivity.this, "Google sign up clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnContinueAsGuest:
                User.setGuestUser();
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void GoogleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            proceedAsGoogleUser(account);
        } catch (ApiException e) {
            proceedAsGoogleUser(null);
        }
    }

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        Utils.removeBottomNavigation(this);
        super.onResume();
    }
}
