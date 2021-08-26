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
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout passwordField = (TextInputLayout) findViewById(R.id.PasswordField);
        passwordField.setEndIconOnClickListener(v -> {
            EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
            if (txtPassword.getTransformationMethod() == null) {
                txtPassword.setTransformationMethod(new PasswordTransformationMethod());
            } else {
                txtPassword.setTransformationMethod(null);
            }
        });

        setupRegisterButtonUI();


    }

    private void setupRegisterButtonUI() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString ss = new SpannableString(getString(R.string.no_account_register));

        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.defaultRed_100));

        ss.setSpan(boldSpan, 23, 31, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcs, 23, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.append(ss);

        TextView textView = (TextView) findViewById(R.id.NoAccount_Register);
        textView.setText(ss);
    }
}
