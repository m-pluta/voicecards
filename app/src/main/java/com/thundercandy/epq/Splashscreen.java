package com.thundercandy.epq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(2000);

                    Intent intent = new Intent(Splashscreen.this, MainLoginActivity.class);
                    startActivity(intent);

                    finish();

                } catch (Exception e) {
                    System.exit(0);
                }
            }
        };
        background.start();

    }
}
