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
        findViewById(R.id.btnSignIn).setOnClickListener(this);
        findViewById(R.id.btnContinueAsGuest).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);      //TODO: Make this nicer, why use intent1 and intent2?
                break;
            case R.id.btnContinueAsGuest:
                Intent intent2 = new Intent(this, HomeActivity.class);
                startActivity(intent2);
                break;
        }

    }
}
