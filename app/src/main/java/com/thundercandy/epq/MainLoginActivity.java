package com.thundercandy.epq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainLoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
//        findViewById(R.id.btnSignIn).setOnClickListener(this);
        findViewById(R.id.btnContinueAsGuest).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnSignIn:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btnContinueAsGuest:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
        }

    }
}
