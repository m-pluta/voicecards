package com.thundercandy.epq;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // Init variables
    TextInputLayout passwordField;
    TextView signupTextButton;
    Button btnSignIn;
    SwitchCompat switchRememberMe;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Assign variables
        passwordField = (TextInputLayout) findViewById(R.id.PasswordField);
        signupTextButton = findViewById(R.id.NoAccount_Register);
        btnSignIn = findViewById(R.id.btnSignIn);
        switchRememberMe = findViewById(R.id.switchRememberMe);

        // Set the dimensions of the sign-in button.
        SignInButton btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn);
        btnGoogleSignIn.setSize(SignInButton.SIZE_WIDE);
        btnGoogleSignIn.setColorScheme(SignInButton.COLOR_AUTO);

        // Toggle for showing/hiding the entered password
        passwordField.setEndIconOnClickListener(v -> {
            EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
            if (txtPassword.getTransformationMethod() == null) {
                txtPassword.setTransformationMethod(new PasswordTransformationMethod());
            } else {
                txtPassword.setTransformationMethod(null);
            }
        });

        // Makes the 'Register' bold and red
        setupRegisterButtonUI();

        btnSignIn.setOnClickListener(this);
        signupTextButton.setOnClickListener(this);

        // Debug for Switch
        switchRememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(LoginActivity.this, "Remember me: true", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "Remember me: false", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRegisterButtonUI() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString ss = new SpannableString(getString(R.string.no_account_signup));

        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.defaultRed_100));

        ss.setSpan(boldSpan, 23, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcs, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.append(ss);
        signupTextButton.setText(ss);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                Toast.makeText(LoginActivity.this, "Sign In button clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.NoAccount_Register:
                Toast.makeText(LoginActivity.this, "Sign up button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
