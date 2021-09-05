package com.thundercandy.epq;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Utils.removeBottomNavigation(this);

        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(1000);

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

    @Override
    public void onBackPressed() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            finishAndRemoveTask();
//        } else {
//            finish();
//        }
        finishAndRemoveTask();

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        Utils.removeBottomNavigation(this);
        super.onResume();
    }
}
