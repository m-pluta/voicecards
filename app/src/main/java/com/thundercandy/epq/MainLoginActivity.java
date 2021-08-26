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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainLoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        findViewById(R.id.btnSignUp).setOnClickListener(this);
        findViewById(R.id.btnContinueAsGuest).setOnClickListener(this);

        setLogInTextClickable();

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
            case R.id.btnSignUp:
                Toast.makeText(MainLoginActivity.this, "Sign up button clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnContinueAsGuest:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
        }

    }
}
