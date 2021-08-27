package com.thundercandy.epq;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private Button btnGoogleSignUp;
    private Button btnSignUp;
    private Button btnContinueAsGuest;

    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        btnGoogleSignUp = findViewById(R.id.btnGoogleSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnContinueAsGuest = findViewById(R.id.btnContinueAsGuest);

        btnGoogleSignUp.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnContinueAsGuest.setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Makes the log in text clickable
        setLogInTextClickable();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        proceed(account);
    }

    private void proceed(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            User.DisplayName = account.getDisplayName();
            User.imageUri = account.getPhotoUrl();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    private void setLogInTextClickable() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString ss = new SpannableString(getString(R.string.already_a_user_log_in));

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(MainLoginActivity.this, LoginActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.defaultRed_100));

        ss.setSpan(clickableSpan, 16, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(boldSpan, 16, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcs, 16, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.append(ss);

        TextView textView = (TextView) findViewById(R.id.btnHaveAccount_LogIn);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnGoogleSignUp:
                GoogleSignIn();
                Toast.makeText(MainLoginActivity.this, "Google sign up clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnSignUp:
                Toast.makeText(MainLoginActivity.this, "Sign up button clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnContinueAsGuest:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void GoogleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // [START onActivityResult]
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
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            proceed(account);
        } catch (ApiException e) {
            proceed(null);
        }
    }
    // [END handleSignInResult]
}
