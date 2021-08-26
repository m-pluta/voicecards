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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // Init variables
    TextInputLayout passwordField;
    TextView registerTextButton;
    Button btnSignIn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Assign variables
        passwordField = (TextInputLayout) findViewById(R.id.PasswordField);
        registerTextButton = findViewById(R.id.NoAccount_Register);
        btnSignIn = findViewById(R.id.btnSignIn);

        passwordField.setEndIconOnClickListener(v -> {
            EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
            if (txtPassword.getTransformationMethod() == null) {
                txtPassword.setTransformationMethod(new PasswordTransformationMethod());
            } else {
                txtPassword.setTransformationMethod(null);
            }
        });

        setupRegisterButtonUI();

        btnSignIn.setOnClickListener(this);
        registerTextButton.setOnClickListener(this);
    }

    private void setupRegisterButtonUI() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString ss = new SpannableString(getString(R.string.no_account_register));

        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.defaultRed_100));

        ss.setSpan(boldSpan, 23, 31, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcs, 23, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.append(ss);
        registerTextButton.setText(ss);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                Toast.makeText(LoginActivity.this, "Sign In button clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.NoAccount_Register:
                Toast.makeText(LoginActivity.this, "Register button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
